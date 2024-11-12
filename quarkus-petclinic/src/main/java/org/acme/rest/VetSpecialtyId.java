package org.acme.rest;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

// WARNING: NOTE THAT WE WROTE ID HERE, IDK WHY IT WORKS
@Embeddable
public class VetSpecialtyId implements Serializable {
  private Long vetId;
  private Long specialtyId;

  public VetSpecialtyId() {
  }

  public VetSpecialtyId(Long vetId, Long specialtyId) {
    this.vetId = vetId;
    this.specialtyId = specialtyId;
  }

  public Long getVetId() {
    return vetId;
  }

  public void setVetId(Long vetId) {
    this.vetId = vetId;
  }

  public Long getSpecialtyId() {
    return specialtyId;
  }

  public void setSpecialtyId(Long specialtyId) {
    this.specialtyId = specialtyId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    VetSpecialtyId that = (VetSpecialtyId) o;
    return Objects.equals(vetId, that.vetId) &&
        Objects.equals(specialtyId, that.specialtyId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(vetId, specialtyId);
  }
}
