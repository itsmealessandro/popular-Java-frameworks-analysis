package org.acme.rest;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;

@Entity
public class Pet extends PanacheEntity {

    public String name;

    public String getName(){
        return this.name;
    }
}
