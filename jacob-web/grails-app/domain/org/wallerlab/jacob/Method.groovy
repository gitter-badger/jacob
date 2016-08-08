package org.wallerlab.jacob

/*
 * In principle, the method name can be obtained from hamiltonian, basis and counterpoise
 * but to make it easier to search, we combine it together and give it a name attribute
 */
class Method {
	
	String name
	
	Hamiltonian hamiltonian
	
	Basis basis
	
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
