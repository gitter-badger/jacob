package org.wallerlab.jacob

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class MethodController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Method.list(params), model:[methodCount: Method.count()]
    }

    def show(Method method) {
        respond method
    }

    def create() {
        respond new Method(params)
    }

    @Transactional
    def save(Method method) {
        if (method == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (method.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond method.errors, view:'create'
            return
        }

        method.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'method.label', default: 'Method'), method.id])
                redirect method
            }
            '*' { respond method, [status: CREATED] }
        }
    }

    def edit(Method method) {
        respond method
    }

    @Transactional
    def update(Method method) {
        if (method == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (method.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond method.errors, view:'edit'
            return
        }

        method.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'method.label', default: 'Method'), method.id])
                redirect method
            }
            '*'{ respond method, [status: OK] }
        }
    }

    @Transactional
    def delete(Method method) {

        if (method == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        method.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'method.label', default: 'Method'), method.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'method.label', default: 'Method'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
