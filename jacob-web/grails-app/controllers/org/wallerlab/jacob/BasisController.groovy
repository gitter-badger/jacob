package org.wallerlab.jacob

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class BasisController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Basis.list(params), model:[basisCount: Basis.count()]
    }

    def show(Basis basis) {
        respond basis
    }

    def create() {
        respond new Basis(params)
    }

    @Transactional
    def save(Basis basis) {
        if (basis == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (basis.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond basis.errors, view:'create'
            return
        }

        basis.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'basis.label', default: 'Basis'), basis.id])
                redirect basis
            }
            '*' { respond basis, [status: CREATED] }
        }
    }

    def edit(Basis basis) {
        respond basis
    }

    @Transactional
    def update(Basis basis) {
        if (basis == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (basis.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond basis.errors, view:'edit'
            return
        }

        basis.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'basis.label', default: 'Basis'), basis.id])
                redirect basis
            }
            '*'{ respond basis, [status: OK] }
        }
    }

    @Transactional
    def delete(Basis basis) {

        if (basis == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        basis.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'basis.label', default: 'Basis'), basis.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'basis.label', default: 'Basis'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
