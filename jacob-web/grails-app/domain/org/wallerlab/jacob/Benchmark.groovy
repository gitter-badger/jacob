package org.wallerlab.jacob

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = ["value"])
class Benchmark {

	@XmlValue
    String value;
	
	@XmlAttribute(name = "Name")
	String name
	
	@XmlAttribute(name = "ReferenceArticle")
	String referenceArticle

	static hasMany = [dataset:Dataset]

	static searchable = true


	static constraints = {
		name(unique:true,nullable:false)
		referenceArticle(nullable:true)
	}

	@Override
	public String toString() {
		return "Benchmark [name=" + name + ", referenceArticle=" + referenceArticle  + " datasets= " + dataset + "]";
	}



	static mapping = {
		dataset sort: "datasetName"
		id generator:'sequence', params:[sequence:'benchmark_id_seq']
	}
}
