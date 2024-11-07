package org.acme.rest;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;

@Entity
public class VetSpecialty extends PanacheEntityBase {

  // WARNING: IDK WHY IT WORKS
  @EmbeddedId
  private VetSpecialtyId id;

  @ManyToOne
  @MapsId("vetId")
  private Vet vet;

  @ManyToOne
  @MapsId("specialtyId")
  private Specialty specialty;

  public VetSpecialtyId getId() {
    return id;
  }

  public void setId(VetSpecialtyId id) {
    this.id = id;
  }

  public Vet getVet() {
    return vet;
  }

  public void setVet(Vet vet) {
    this.vet = vet;
  }

  public Specialty getSpecialty() {
    return specialty;
  }

  public void setSpecialty(Specialty specialty) {
    this.specialty = specialty;
  }
}
