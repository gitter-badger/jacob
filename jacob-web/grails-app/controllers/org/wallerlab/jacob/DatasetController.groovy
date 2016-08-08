package org.wallerlab.jacob

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class DatasetController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Dataset.list(params), model:[datasetCount: Dataset.count()]
    }

    def show(Dataset dataset) {
        respond dataset
    }

    def create() {
        respond new Dataset(params)
    }

    @Transactional
    def save(Dataset dataset) {
        if (dataset == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (dataset.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond dataset.errors, view:'create'
            return
        }

        dataset.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'dataset.label', default: 'Dataset'), dataset.id])
                redirect dataset
            }
            '*' { respond dataset, [status: CREATED] }
        }
    }

    def edit(Dataset dataset) {
        respond dataset
    }

    @Transactional
    def update(Dataset dataset) {
        if (dataset == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (dataset.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond dataset.errors, view:'edit'
            return
        }

        dataset.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'dataset.label', default: 'Dataset'), dataset.id])
                redirect dataset
            }
            '*'{ respond dataset, [status: OK] }
        }
    }

    @Transactional
    def delete(Dataset dataset) {

        if (dataset == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        dataset.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'dataset.label', default: 'Dataset'), dataset.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'dataset.label', default: 'Dataset'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
