package org.wallerlab.jacob.domain;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;

import java.math.BigDecimal;

@NodeEntity
public class Result {
	
	@GraphId
	private Long id;
	
	private Double value;

	public Result(Double value) {
		this.value = value;
	}

	public Double getValue() {
		return value;
	}
}
