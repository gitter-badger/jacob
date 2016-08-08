package org.wallerlab.jacob.domain;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;

@NodeEntity
public class Molecule {

	@GraphId
	private Long id;
	
	private String name;
	
	private String smiles;

	public Molecule(String name, String smiles) {
		this.name = name;
		this.smiles = smiles;
	}
}
