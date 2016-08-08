package org.wallerlab.jacob.domain;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;

@NodeEntity
public class Method {

	@GraphId
	private Long id;
	
	private String name;

	private String hamiltonian;

	private String basis;

	private Result result;

	private Double error;

	public Method(String hamiltonian, String basis, Result result) {
		this.result = result;
		this.hamiltonian = hamiltonian;
		this.basis = basis;
	}

	public Result getResult() {
		return result;
	}

	public Double getError() {
		return error;
	}

	public void setError(Double error) {
		this.error = error;
	}
}
