package org.wallerlab.jacob

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = ["molecules","methods","referenceResult"])
class System {

	@XmlAttribute(name = "Name")
	String systemName //name
	
	@XmlElement(name = "ReferenceResult", required = true)
	Float referenceValue
	
	Float meanAbsoluteDeviation

	Dataset dataset
	
	@XmlElement(name = "Methods", required = true)
    Method method
	
	@XmlElement(name = "Molecules", required = true)
	Molecule molecule
	
	static belongsTo = [Dataset, Benchmark]
	
	static hasMany = [methods:Method, molecules:Molecule]
	
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
