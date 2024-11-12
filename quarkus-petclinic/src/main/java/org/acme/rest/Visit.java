package org.acme.rest;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Visit extends PanacheEntity {
  private LocalDate date;
  private String description;

  @JsonIgnore
  @ManyToOne
  private Pet pet;

  // NOTE: the same trick for owner on pet
  private long petId;

  public LocalDate getDate() {
    return date;
  }

  public void setDate(LocalDate visitDate) {
    this.date = visitDate;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Pet getPet() {
    return pet;
  }

  public void setPet(Pet pet) {
    this.pet = pet;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public long getPetId() {
    return petId;
  }

  public void setPetId(long petId) {
    this.petId = petId;
  }

}
