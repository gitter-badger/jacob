package org.wallerlab.jacob

import org.wallerlab.jacob.domain.Molecule
import spock.lang.Specification


class SecondSpec extends Specification{

    def "Molecule domain model"(){

        when:
        def molecule = new Molecule("Benzene","cccccc")

        then:
        molecule.getName() == "Benzene"
        molecule.getSmiles() == "cccccc"
        molecule.toString()
    }

}