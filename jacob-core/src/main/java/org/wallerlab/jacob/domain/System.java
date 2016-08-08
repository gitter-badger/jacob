package org.wallerlab.jacob.domain;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.Set;

@NodeEntity
public class System {

	@GraphId
	private Long id;
		
	private String name;

	@Relationship(type = "HAS")
	private Set<Molecule> molecules;

	@Relationship(type = "HAS")
	private Set<Method> methods;

	@Relationship(type = "HAS")
	private Reference reference;

	public System(String name, Set<Molecule> molecules, Set<Method> methods, Reference reference) {
		this.name = name;
		this.molecules = molecules;
		this.methods = methods;
		this.reference = reference;
	}


	public String getName() {
		return name;
	}

	public Set<Molecule> getMolecules() {
		return molecules;
	}

	public Set<Method> getMethods() {
		return methods;
	}

	public Reference getReference() {
		return reference;
	}
}
