package org.wallerlab.jacob.domain;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

@NodeEntity
public class Benchmark {
	
	@GraphId
	private Long id;

	private String name;
	
	private String reference;
	
	@Relationship(type = "OWNS")
	private org.wallerlab.jacob.domain.Dataset dataset;

	public Benchmark() {}

	public Benchmark(String name, String reference, org.wallerlab.jacob.domain.Dataset dataset) {
		super();
		this.name = name;
		this.dataset = dataset;
		this.reference=reference;
	}

}
