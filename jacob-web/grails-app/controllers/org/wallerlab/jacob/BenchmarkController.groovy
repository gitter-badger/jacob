package org.wallerlab.jacob

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class BenchmarkController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Benchmark.list(params), model:[benchmarkCount: Benchmark.count()]
    }

    def show(Benchmark benchmark) {
        respond benchmark
    }

    def create() {
        respond new Benchmark(params)
    }

    @Transactional
    def save(Benchmark benchmark) {
        if (benchmark == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (benchmark.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond benchmark.errors, view:'create'
            return
        }

        benchmark.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'benchmark.label', default: 'Benchmark'), benchmark.id])
                redirect benchmark
            }
            '*' { respond benchmark, [status: CREATED] }
        }
    }

    def edit(Benchmark benchmark) {
        respond benchmark
    }

    @Transactional
    def update(Benchmark benchmark) {
        if (benchmark == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (benchmark.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond benchmark.errors, view:'edit'
            return
        }

        benchmark.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'benchmark.label', default: 'Benchmark'), benchmark.id])
                redirect benchmark
            }
            '*'{ respond benchmark, [status: OK] }
        }
    }

    @Transactional
    def delete(Benchmark benchmark) {

        if (benchmark == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        benchmark.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'benchmark.label', default: 'Benchmark'), benchmark.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'benchmark.label', default: 'Benchmark'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
