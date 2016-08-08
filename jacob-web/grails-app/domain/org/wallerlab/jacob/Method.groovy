package org.wallerlab.jacob

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/*
 * In principle, the method name can be obtained from hamiltonian, basis and counterpoise
 * but to make it easier to search, we combine it together and give it a name attribute
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = ["result"])
class Method {
	
	String name
	
	@XmlAttribute(name = "Hamiltonian")
	Hamiltonian hamiltonian
	
	@XmlAttribute(name = "Basis")
	Basis basis
	
	@XmlAttribute(name = "CounterpoiseCorrection")
	String counterpoise

	static belongsTo = [System]
    static hasMany = [results:Result]   
		
	static searchable = true

	String toString(){
		return """$name and $basis"""
	}

	static constraints = {
		name(unique:true,nullable:false)
		hamiltonian(nullable:false)
		basis(nullable:true)
		counterpoise(inList:['CP','noCP'])
	}
	
	static mapping = {
		id generator:'sequence', params:[sequence:'method_id_seq']
	}

}
