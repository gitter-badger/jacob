package org.wallerlab.jacob

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class ResultController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Result.list(params), model:[resultCount: Result.count()]
    }

    def show(Result result) {
        respond result
    }

    def create() {
        respond new Result(params)
    }

    @Transactional
    def save(Result result) {
        if (result == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (result.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond result.errors, view:'create'
            return
        }

        result.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'result.label', default: 'Result'), result.id])
                redirect result
            }
            '*' { respond result, [status: CREATED] }
        }
    }

    def edit(Result result) {
        respond result
    }

    @Transactional
    def update(Result result) {
        if (result == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (result.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond result.errors, view:'edit'
            return
        }

        result.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'result.label', default: 'Result'), result.id])
                redirect result
            }
            '*'{ respond result, [status: OK] }
        }
    }

    @Transactional
    def delete(Result result) {

        if (result == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        result.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'result.label', default: 'Result'), result.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'result.label', default: 'Result'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
