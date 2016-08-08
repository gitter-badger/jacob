package org.wallerlab.jacob.domain;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.Set;

@NodeEntity
public class Dataset {

	@GraphId
	private Long id;
	
	private String name;

	private String citation;

	private Set<System> systems;

	
	public Dataset(String name, String citation,  Set<System> systems) {
		super();
		this.name = name;
		this.citation =citation;
		this.systems = systems;
	}

	public Dataset(String name) {
		super();
		this.name = name;
	}

	public Dataset() {
		super();
	}
	
	
}
