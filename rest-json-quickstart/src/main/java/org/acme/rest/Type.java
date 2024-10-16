package org.acme.rest;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;

@Entity
public class Type extends PanacheEntity {
  public String name;

  public void setName(String name) {
    this.name = name;
  }
}
