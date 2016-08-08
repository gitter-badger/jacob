package org.wallerlab.jacob

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = ["benchmark","dataset"])
@XmlRootElement(name = "Jacob")
class Jacob {

	@XmlElement(name = "Benchmark", required = true)
	Benchmark benchmark;
	
	@XmlElement(name = "Dataset", required = true)
	Dataset dataset;
	
	@XmlAttribute(name = "Version")
	Float version;

    static constraints = {
    }
	
	
}
