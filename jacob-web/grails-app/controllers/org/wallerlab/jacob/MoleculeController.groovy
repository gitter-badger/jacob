package org.wallerlab.jacob

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class MoleculeController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Molecule.list(params), model:[moleculeCount: Molecule.count()]
    }

    def show(Molecule molecule) {
        respond molecule
    }

    def create() {
        respond new Molecule(params)
    }

    @Transactional
    def save(Molecule molecule) {
        if (molecule == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (molecule.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond molecule.errors, view:'create'
            return
        }

        molecule.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'molecule.label', default: 'Molecule'), molecule.id])
                redirect molecule
            }
            '*' { respond molecule, [status: CREATED] }
        }
    }

    def edit(Molecule molecule) {
        respond molecule
    }

    @Transactional
    def update(Molecule molecule) {
        if (molecule == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (molecule.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond molecule.errors, view:'edit'
            return
        }

        molecule.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'molecule.label', default: 'Molecule'), molecule.id])
                redirect molecule
            }
            '*'{ respond molecule, [status: OK] }
        }
    }

    @Transactional
    def delete(Molecule molecule) {

        if (molecule == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        molecule.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'molecule.label', default: 'Molecule'), molecule.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'molecule.label', default: 'Molecule'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
