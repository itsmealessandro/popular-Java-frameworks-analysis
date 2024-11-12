package org.acme.rest;

import java.util.Set;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

@Entity
public class Vet extends PanacheEntity {
  public String firstName;
  public String lastName;

  @OneToMany
  Set<Specialty> Specialties;

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public Set<Specialty> getSpecialties() {
    return Specialties;
  }

  public void setSpecialties(Set<Specialty> specialties) {
    Specialties = specialties;
  }
}
