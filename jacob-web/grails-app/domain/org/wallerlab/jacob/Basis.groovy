package org.wallerlab.jacob

class Basis {

	String nameOfBasis //name
	String description 
	String basisType //type
	
	static belongsTo = [Method]
	
	static searchable = true
	
    static constraints = {
		nameOfBasis(nullable:false,unique:true)
		description(nullable:true)
		basisType(nullable:true)
	}
	
	@Override
	public String toString() {
		return "Basis [nameOfBasis=" + nameOfBasis + ", description=" \
				+ description + ", basisType=" + basisType + "]";
	}

	static mapping = {
		id generator:'sequence', params:[sequence:'basis_id_seq']
	}

}
