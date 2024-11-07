package org.acme.rest;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

import org.acme.utility.UtilityMaps;

@Path("petclinic/api")
public class Endpoint {

  @Inject
  DatabaseService databaseService;

  @GET
  @Produces(MediaType.TEXT_PLAIN)
  public String hello() {

    return databaseService.getAllInstances();
    /*
     * return "DB name: " + databaseService.getDatabaseName() + "---------" +
     * "First Pet Name: "
     * + databaseService.listTables() + "-----" + "pets: " +
     * databaseService.getAllPets() + "\n"
     * + "go check dev ui: http://localhost:8080/q/dev-ui/";
     */
  }

  @GET
  @Path("hello")
  public String helloGET() {
    return "jakarta and quarkus from path /hello";
  }

  // ---------------------------------------------- pet clinic methods
  // Add a pet owner (POST /owners)

  @GET
  @Path("/owners")
  @Produces(MediaType.APPLICATION_JSON)
  public List<Owner> listOwners(@QueryParam("lastName") String lastName) {
    return databaseService.listOwners(lastName != null ? lastName : "");
  }

  // Get a pet owner by ID (GET /owners/{ownerId})
  @GET
  @Path("/owners/{ownerId}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getOwner(@PathParam("ownerId") int ownerId) {
    Owner owner = databaseService.getOwner(ownerId);
    if (owner == null) {
      return Response.status(Response.Status.NOT_FOUND).build();
    }
    return Response.ok(owner).build();
  }

  @POST
  @Path("/owners")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response addOwner(Owner owner) {
    databaseService.addOwner(owner.getFirstName(), owner.getLastName(), owner.getAddress(), owner.getCity(),
        owner.getTelephone());
    return Response.status(Response.Status.CREATED).entity(owner).build();
  }

  // Update a pet owner (PUT /owners/{ownerId})
  @PUT
  @Path("/owners/{ownerId}")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response updateOwner(@PathParam("ownerId") int ownerId, Owner updatedOwner) {
    Owner existingOwner = databaseService.getOwner(ownerId);
    if (existingOwner == null) {
      return Response.status(Response.Status.NOT_FOUND).build();
    }

    databaseService.updateOwner(ownerId, updatedOwner.getFirstName(), updatedOwner.getLastName(),
        updatedOwner.getAddress(), updatedOwner.getCity(), updatedOwner.getTelephone());
    return Response.ok(updatedOwner).build();
  }

  // Delete a pet owner (DELETE /owners/{ownerId})
  @DELETE
  @Path("/owners/{ownerId}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response deleteOwner(@PathParam("ownerId") int ownerId) {
    Owner owner = databaseService.getOwner(ownerId);
    if (owner == null) {
      return Response.status(Response.Status.NOT_FOUND).build();
    }

    databaseService.deleteOwner(ownerId);
    return Response.noContent().build();
  }

  // Add a pet to an owner (POST /owners/{ownerId}/pets)
  // Query: INSERT INTO pets (name, birth_date, owner_id, type_id) VALUES (?, ?,
  // ?, ?)
  @POST
  @Path("/owners/{ownerId}/pets")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response addPetToOwner(@PathParam("ownerId") int ownerId, Pet pet) {
    databaseService.addPetToOwner(ownerId, pet.getName(), pet.getBirthDate().toString(), pet.getType().getId());
    return Response.status(Response.Status.CREATED).entity(pet).build();
  }

  // List pets (GET /pets)
  @GET
  @Path("/pets")
  @Produces(MediaType.APPLICATION_JSON)
  public List<Pet> listPets() {
    return databaseService.listPets();
  }

  // Get a pet by ID (GET /pets/{petId})
  @GET
  @Path("/pets/{petId}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getPet(@PathParam("petId") int petId) {
    try {

      Pet pet = databaseService.getPet(petId);
      if (pet == null) {
        return Response.status(Response.Status.NOT_FOUND).build();
      }
      return Response.ok(pet).build();
    } catch (NotFoundException e) {
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();

    }

  }

  @POST
  @Path("/pets")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response addPet(Pet pet) {
    // databaseService.addPet(pet.getName(), pet.getBirthDate(), pet.getType());
    databaseService.addPet(pet);
    return Response.status(Response.Status.CREATED).entity(pet).build();
  }

  @PUT
  @Path("/pets/{petId}")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response updatePet(@PathParam("petId") int petId, Pet pet) {
    // BUG: boolean updated = databaseService.updatePet(petId, pet.getName(),
    // pet.getBirthDate(), pet.getType());
    /*
     * if (!updated) {
     * return Response.status(Response.Status.NOT_FOUND).build();
     * }
     */
    return Response.ok(pet).build();
  }

  @DELETE
  @Path("/pets/{petId}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response deletePet(@PathParam("petId") int petId) {
    /*
     * BUG: boolean deleted = databaseService.deletePet(petId);
     * if (!deleted) {
     * return Response.status(Response.Status.NOT_FOUND).build();
     * }
     */
    return Response.noContent().build();
  }

  // Add a vet (POST /vets)
  @POST
  @Path("/vets")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response addVet(Vet vet) {
    // databaseService.addVet(vet.getFirstName(), vet.getLastName(),
    // vet.getSpecialties());
    // BUG: solve this
    return Response.status(Response.Status.NOT_IMPLEMENTED).entity(vet).build();
    // return Response.status(Response.Status.CREATED).entity(vet).build();
  }

  // List vets (GET /vets)
  @GET
  @Path("/vets")
  @Produces(MediaType.APPLICATION_JSON)
  public List<Vet> listVets() {
    return databaseService.listVets();
  }

  // Add a visit (POST /visits)
  @POST
  @Path("/visits")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response addVisit(Visit visit) {
    databaseService.addVisit(visit.getPet().getId(), visit.getDate().toString(), visit.getDescription());
    return Response.status(Response.Status.CREATED).entity(visit).build();
  }

  // List visits (GET /visits)
  @GET
  @Path("/visits")
  @Produces(MediaType.APPLICATION_JSON)
  public List<Visit> listVisits() {
    return databaseService.listVisits();
  }

  // Other endpoints can be added similarly for managing specialties, vet types,
  // etc.

  @GET
  @Path("/specialties")
  @Produces(MediaType.APPLICATION_JSON)
  public List<Specialty> listSpecialties() {
    return databaseService.listSpecialties();
  }

  @POST
  @Path("/specialties")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response addSpecialty(Specialty specialty) {
    databaseService.addSpecialty(specialty.getName());
    return Response.status(Response.Status.CREATED).entity(specialty).build();
  }

  @GET
  @Path("/pettypes")
  @Produces(MediaType.APPLICATION_JSON)
  public List<Type> listPetTypes() {
    return databaseService.listPetTypes();
  }

  @POST
  @Path("/pettypes")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response addPetType(Type petType) {
    databaseService.addPetType(petType.getName());
    return Response.status(Response.Status.CREATED).entity(petType).build();
  }

}
