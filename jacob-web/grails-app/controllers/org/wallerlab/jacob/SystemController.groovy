package org.wallerlab.jacob

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class SystemController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond System.list(params), model:[systemCount: System.count()]
    }

    def show(System system) {
        respond system
    }

    def create() {
        respond new System(params)
    }

    @Transactional
    def save(System system) {
        if (system == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (system.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond system.errors, view:'create'
            return
        }

        system.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'system.label', default: 'System'), system.id])
                redirect system
            }
            '*' { respond system, [status: CREATED] }
        }
    }

    def edit(System system) {
        respond system
    }

    @Transactional
    def update(System system) {
        if (system == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (system.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond system.errors, view:'edit'
            return
        }

        system.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'system.label', default: 'System'), system.id])
                redirect system
            }
            '*'{ respond system, [status: OK] }
        }
    }

    @Transactional
    def delete(System system) {

        if (system == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        system.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'system.label', default: 'System'), system.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'system.label', default: 'System'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
