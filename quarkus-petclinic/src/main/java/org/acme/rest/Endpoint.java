package org.acme.rest;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

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

  // Add a pet owner (POST /owners)
  @POST
  @Path("/owners")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response addOwner(Owner owner) {
    databaseService.addOwner(owner.getFirstName(), owner.getLastName(), owner.getAddress(), owner.getCity(),
        owner.getTelephone());
    return Response.status(Response.Status.CREATED).entity(owner).build();
  }

  // List pet owners (GET /owners)
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
  @POST
  @Path("/owners/{ownerId}/pets")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response addPetToOwner(@PathParam("ownerId") int ownerId, Pet pet) {
    // TODO:databaseService.addPetToOwner(ownerId, pet.getName(),
    // pet.getBirthDate().toString(), pet.getTypeId());
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
    Pet pet = databaseService.getPet(petId);
    if (pet == null) {
      return Response.status(Response.Status.NOT_FOUND).build();
    }
    return Response.ok(pet).build();
  }

  // Add a vet (POST /vets)
  @POST
  @Path("/vets")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response addVet(Vet vet) {
    // TODO:databaseService.addVet(vet.getFirstName(), vet.getLastName(),
    // vet.getSpecialties());
    return Response.status(Response.Status.CREATED).entity(vet).build();
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
    // TODO: databaseService.addVisit(visit.getPetId(), visit.getDate().toString(),
    // visit.getDescription());
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

}
