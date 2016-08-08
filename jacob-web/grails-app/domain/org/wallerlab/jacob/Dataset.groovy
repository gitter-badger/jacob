package org.wallerlab.jacob

class Dataset {
	
    String datasetName //name
	
    String referenceArticle //reference
	
	System system
	
	Benchmark benchmark
	
    static belongsTo = Benchmark 
    static hasMany = [systems:System]   

	//Throws error
	//static embedded = ['systems']
	
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
