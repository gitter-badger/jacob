package org.wallerlab.jacob.domain;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;


/**
 * Created by jacob on 28/07/16.
 */
@NodeEntity
public class Reference {

    @GraphId
    private Long id;

    private Double value;

    public Reference(Double value) {
        this.value = value;
    }

    public Double getValue() {
        return value;
    }
}
