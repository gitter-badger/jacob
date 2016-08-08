package org.wallerlab.jacob

class Molecule {
	
	String moleculeName //name
	
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
