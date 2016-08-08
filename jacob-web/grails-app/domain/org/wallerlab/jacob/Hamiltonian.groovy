package org.wallerlab.jacob

class Hamiltonian {
	
	String nameOfHamiltonian //name
	String category
	
	static belongsTo = [Method]	
	
	static searchable = true

	static constraints = {
		nameOfHamiltonian(nullable:false,unique:true)
		category(inList:['LDA','GGA','meta-GGA','hybrid-GGA','double-hybrid-GGA','wavefunction','semiempirical','undefined','forcefield'])
    }
	
	static mapping = {
		id generator:'sequence', params:[sequence:'hamiltonian_id_seq']
	}
	
	@Override
	public String toString() {
		return "Hamiltonian [nameOfHamiltonian=" + nameOfHamiltonian \
				+ ", category=" + category + "]";
	}

}
