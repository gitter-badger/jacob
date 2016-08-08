package org.wallerlab.jacob

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = ["system"])
class Dataset {
	
	@XmlAttribute(name = "Name")
    String datasetName //name
	
	@XmlAttribute(name = "ReferenceArticle")
    String referenceArticle //reference
	
	@XmlElement(name = "System")
	System system
	
	Benchmark benchmark
	
    static belongsTo = Benchmark 
    static hasMany = [systems:System]   
	
	static embedded = ['systems'] //Look this up.
	
	static searchable = true
	
	static constraints = {
		datasetName(nullable:false)
		referenceArticle(nullable:true)
		benchmark(nullable:false)
		system(nullable:true)
	  }
  
	  static mapping = {
		  id generator:'sequence', params:[sequence:'dataset_id_seq']
	  }
	
    @Override
	public String toString() {
		return "Dataset [datasetName=" + datasetName + ", referenceArticle=" \
				+ referenceArticle + ", benchmark=" + benchmark + "]";
	}
	
}
