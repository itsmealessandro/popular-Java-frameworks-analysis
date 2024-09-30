package org.springframework.samples.petclinic.rest.dto;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.samples.petclinic.rest.dto.SpecialtyDto;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * Editable fields of a veterinarian.
 */

@Schema(name = "VetFields", description = "Editable fields of a veterinarian.")
@JsonTypeName("VetFields")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-09-30T10:56:23.721398395+02:00[Europe/Rome]", comments = "Generator version: 7.8.0")
public class VetFieldsDto {

  private String firstName;

  private String lastName;

  @Valid
  private List<@Valid SpecialtyDto> specialties = new ArrayList<>();

  public VetFieldsDto() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public VetFieldsDto(String firstName, String lastName, List<@Valid SpecialtyDto> specialties) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.specialties = specialties;
  }

  public VetFieldsDto firstName(String firstName) {
    this.firstName = firstName;
    return this;
  }

  /**
   * The first name of the vet.
   * @return firstName
   */
  @NotNull @Pattern(regexp = "^[a-zA-Z]*$") @Size(min = 1, max = 30) 
  @Schema(name = "firstName", example = "James", description = "The first name of the vet.", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("firstName")
  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public VetFieldsDto lastName(String lastName) {
    this.lastName = lastName;
    return this;
  }

  /**
   * The last name of the vet.
   * @return lastName
   */
  @NotNull @Pattern(regexp = "^[a-zA-Z]*$") @Size(min = 1, max = 30) 
  @Schema(name = "lastName", example = "Carter", description = "The last name of the vet.", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("lastName")
  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public VetFieldsDto specialties(List<@Valid SpecialtyDto> specialties) {
    this.specialties = specialties;
    return this;
  }

  public VetFieldsDto addSpecialtiesItem(SpecialtyDto specialtiesItem) {
    if (this.specialties == null) {
      this.specialties = new ArrayList<>();
    }
    this.specialties.add(specialtiesItem);
    return this;
  }

  /**
   * The specialties of the vet.
   * @return specialties
   */
  @NotNull @Valid 
  @Schema(name = "specialties", description = "The specialties of the vet.", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("specialties")
  public List<@Valid SpecialtyDto> getSpecialties() {
    return specialties;
  }

  public void setSpecialties(List<@Valid SpecialtyDto> specialties) {
    this.specialties = specialties;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    VetFieldsDto vetFields = (VetFieldsDto) o;
    return Objects.equals(this.firstName, vetFields.firstName) &&
        Objects.equals(this.lastName, vetFields.lastName) &&
        Objects.equals(this.specialties, vetFields.specialties);
  }

  @Override
  public int hashCode() {
    return Objects.hash(firstName, lastName, specialties);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class VetFieldsDto {\n");
    sb.append("    firstName: ").append(toIndentedString(firstName)).append("\n");
    sb.append("    lastName: ").append(toIndentedString(lastName)).append("\n");
    sb.append("    specialties: ").append(toIndentedString(specialties)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

