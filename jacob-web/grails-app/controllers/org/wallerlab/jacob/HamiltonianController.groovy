package org.wallerlab.jacob

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class HamiltonianController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Hamiltonian.list(params), model:[hamiltonianCount: Hamiltonian.count()]
    }

    def show(Hamiltonian hamiltonian) {
        respond hamiltonian
    }

    def create() {
        respond new Hamiltonian(params)
    }

    @Transactional
    def save(Hamiltonian hamiltonian) {
        if (hamiltonian == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (hamiltonian.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond hamiltonian.errors, view:'create'
            return
        }

        hamiltonian.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'hamiltonian.label', default: 'Hamiltonian'), hamiltonian.id])
                redirect hamiltonian
            }
            '*' { respond hamiltonian, [status: CREATED] }
        }
    }

    def edit(Hamiltonian hamiltonian) {
        respond hamiltonian
    }

    @Transactional
    def update(Hamiltonian hamiltonian) {
        if (hamiltonian == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (hamiltonian.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond hamiltonian.errors, view:'edit'
            return
        }

        hamiltonian.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'hamiltonian.label', default: 'Hamiltonian'), hamiltonian.id])
                redirect hamiltonian
            }
            '*'{ respond hamiltonian, [status: OK] }
        }
    }

    @Transactional
    def delete(Hamiltonian hamiltonian) {

        if (hamiltonian == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        hamiltonian.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'hamiltonian.label', default: 'Hamiltonian'), hamiltonian.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'hamiltonian.label', default: 'Hamiltonian'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
