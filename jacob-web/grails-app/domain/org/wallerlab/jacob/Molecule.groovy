package org.wallerlab.jacob

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = ["atomArray","bondArray"])
class Molecule {
	
	@XmlAttribute(name = "Name")
	String moleculeName //name
	
	@XmlAttribute(name = "SmileString")
	String smileString  //smiles
	
	System system
	Dataset dataset
	Benchmark benchmark
	
	static belongsTo = [Benchmark,Dataset,System]
	
	static searchable = true
	
    static constraints = {
		moleculeName(nullable:false)
		smileString(nullable:true)
		system(nullable:false)
		dataset(nullable:false)
		benchmark(nullable:false)
    }
	
	static mapping = {
		id generator:'sequence', params:[sequence:'jacobMolecule_id_seq']
	}
	
	@Override
	String toString(){
		return moleculeName
	}
	
}
