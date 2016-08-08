package org.wallerlab.jacob.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;
import org.wallerlab.jacob.config.DatabaseConfig;
import org.wallerlab.jacob.domain.*;
import org.wallerlab.jacob.generated.Jacob;
import org.wallerlab.jacob.generated.Method;

import java.lang.System;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by jacob on 28/07/16.
 */
@Service
public class Translator {

    private final Logger log = LoggerFactory.getLogger(Translator.class);


    public Benchmark translate(Jacob item) throws Exception {
        log.info("Translating " + item.getDataset().getName() );
        return new Benchmark(item.getBenchmark().getName(),
                            item.getBenchmark().getReferenceArticle(),
                            getDataset(item));
    }

    public org.wallerlab.jacob.domain.Dataset getDataset(Jacob item){
        return new org.wallerlab.jacob.domain.Dataset(item.getDataset().getName(),
                           item.getDataset().getReferenceArticle(),
                           getSystems(item));
    }

    public Set<org.wallerlab.jacob.domain.System> getSystems(Jacob item){
        Set<org.wallerlab.jacob.domain.System> systems = new HashSet<>();
        for(org.wallerlab.jacob.generated.System system : item.getDataset().getSystem())
            systems.add(new org.wallerlab.jacob.domain.System(system.getName(),
                                                              getMolecules(system),
                                                              getMethods(system),
                                                              new Reference(system.getReferenceResult()
                                                                                  .getReferenceValue()
                                                                                  .getValue()
                                                                                  .doubleValue())));

        systems.stream()
               .forEach(system ->
                       system.getMethods().forEach(method ->
                                            method.setError(Math.abs(method.getResult().getValue() - system.getReference().getValue()))));
        return systems;
    }

    public Set<Molecule> getMolecules(org.wallerlab.jacob.generated.System system){
        Set<Molecule> molecules = new HashSet<>();
        for(org.wallerlab.jacob.generated.Molecule molecule: system.getMolecules().getMolceules())
           molecules.add( new Molecule(molecule.getName(),molecule.getSmileString()));
        return molecules;
    }

    public Set<org.wallerlab.jacob.domain.Method> getMethods(org.wallerlab.jacob.generated.System system){
        Set<org.wallerlab.jacob.domain.Method> methods = new HashSet<>();
        for(org.wallerlab.jacob.generated.Method method : system.getMethods().getMethod())
            methods.add(new org.wallerlab.jacob.domain.Method(method.getHamiltonian(),method.getBasis(),getResult(method)));
        return methods;
    }

    private org.wallerlab.jacob.domain.Result getResult(org.wallerlab.jacob.generated.Method method){
        return new org.wallerlab.jacob.domain.Result(method.getResult().getCalculatedValue().doubleValue());
    }


}
