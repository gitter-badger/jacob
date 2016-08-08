package org.wallerlab.jacob

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = ["value"])
class Result {
	
	@XmlAttribute(name = "CalculatedValue")
    float calculatedValue 
	
	float referenceValue
	
	//Generated
	float differenceWithReferenceValue   
    float percentageError
	
	Benchmark benchmark
	Dataset dataset
	System system
	Method method
	
	static searchable = true
	
    static belongsTo = [Benchmark,Dataset,System,Method]
	
	static constraints = {
		benchmark(nullable:false)
		dataset(nullable:false)
		system(nullable:false)
		method(nullable:false)
		calculatedValue(nullable:true,blank:true)
		referenceValue(nullable:true,blank:true)
		differenceWithReferenceValue(nullable:true,blank:true)
		percentageError(nullable:true,blank:true)
	  }

	static mapping = {
		id generator:'sequence', params:[sequence:'result_id_seq']
	}
 
	@Override
	public String toString() {
		return "Result [calculatedValue=" + calculatedValue \
				+ ", differenceWithReferenceValue=" \
				+ differenceWithReferenceValue + ", percentageError=" \
				+ percentageError + ", referenceValue=" + referenceValue + "]";
	}

}
