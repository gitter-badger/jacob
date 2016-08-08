package org.wallerlab.jacob

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

class System {

	String systemName //name
	
	Float referenceValue
	
	Float meanAbsoluteDeviation

	Dataset dataset
	
    Method method
	
	Molecule molecule
	
	static belongsTo = [Dataset, Benchmark]
	
	static hasMany = [methods:Method, molecules:Molecule]

	//Throws error
	//static embedded = ['methods', 'molecules']

	static searchable = true
	
	static constraints = {
		systemName(nullable:false)
		molecule(nullable:true)
		dataset(nullable:false)
		referenceValue(nullable:true,blank:false)
		meanAbsoluteDeviation(nullable:true,blank:true)
	}

	static mapping = {
		id generator:'sequence', params:[sequence:'system_id_seq']
	}
	
	@Override
	String toString(){
		return systemName
	}

}
