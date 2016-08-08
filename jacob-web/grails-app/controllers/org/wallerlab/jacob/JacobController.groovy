package org.wallerlab.jacob

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class JacobController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Jacob.list(params), model:[jacobCount: Jacob.count()]
    }

    def show(Jacob jacob) {
        respond jacob
    }

    def create() {
        respond new Jacob(params)
    }

    @Transactional
    def save(Jacob jacob) {
        if (jacob == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (jacob.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond jacob.errors, view:'create'
            return
        }

        jacob.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'jacob.label', default: 'Jacob'), jacob.id])
                redirect jacob
            }
            '*' { respond jacob, [status: CREATED] }
        }
    }

    def edit(Jacob jacob) {
        respond jacob
    }

    @Transactional
    def update(Jacob jacob) {
        if (jacob == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (jacob.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond jacob.errors, view:'edit'
            return
        }

        jacob.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'jacob.label', default: 'Jacob'), jacob.id])
                redirect jacob
            }
            '*'{ respond jacob, [status: OK] }
        }
    }

    @Transactional
    def delete(Jacob jacob) {

        if (jacob == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        jacob.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'jacob.label', default: 'Jacob'), jacob.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'jacob.label', default: 'Jacob'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
