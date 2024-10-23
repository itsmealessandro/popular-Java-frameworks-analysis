package org.acme.rest;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import java.time.LocalDate;

@Entity
public class Visit extends PanacheEntity {
  private LocalDate date;
  private String description;

  @ManyToOne
  private Pet pet;

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
}
