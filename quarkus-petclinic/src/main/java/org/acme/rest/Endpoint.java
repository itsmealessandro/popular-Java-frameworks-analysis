package org.acme.rest;

import jakarta.inject.Inject;
import jakarta.ws.rs.BadRequestException;
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

import java.sql.SQLException;
import java.util.List;

@Path("petclinic/api")
public class Endpoint {

  @Inject
  DatabaseService databaseService;

  /**
   * @return all the instances of all the tables in the DB
   */
  @GET
  @Produces(MediaType.TEXT_PLAIN)
  public String hello() {
    return databaseService.getAllInstances();
  }

  @GET
  @Path("hello")
  public String helloGET() {
    return "jakarta and quarkus from path /hello";
  }

  // ---------------------------------------------- NOTE: pet clinic methods
  // ----------------------------------------------------------------
  // ----------------------------------------------------------------
  // ----------------------------------------------------------------

  // NOTE: Owner methods #######################################
  /**
   * Get a owner by ID (GET /owners/{ownerId})
   * 
   * @param ownerId
   * @return
   */
  @GET
  @Path("/owners/{ownerId}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getOwner(@PathParam("ownerId") int ownerId) {
    try {

      Owner owner = databaseService.getOwner(ownerId);
      if (owner == null) {
        return Response.status(Response.Status.NOT_FOUND).build();
      }
      System.out.println(owner);
      return Response.ok(owner).build();
    } catch (SQLException e) {
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }
  }

  /**
   * Adds a new <code>Owner</code>
   * 
   * @param owner
   * @return the Owner
   */
  @POST
  @Path("/owners")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response addOwner(Owner owner) {
    try {
      databaseService.addOwner(owner);

      return Response.status(Response.Status.CREATED).entity(owner).build();
    } catch (SQLException e) {
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    } catch (IllegalArgumentException e) {
      return Response.status(Response.Status.BAD_REQUEST).build();
    }
  }

  // Update a owner (PUT /owners/{ownerId})
  /**
   * update owners details
   * 
   * @param ownerId
   * @param owner
   * @return the Owner
   */
  @PUT
  @Path("/owners/{ownerId}")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response updateOwner(@PathParam("ownerId") long ownerId, Owner owner) {
    try {
      databaseService.updateOwner(ownerId, owner);

      return Response.ok(owner).build();
    } catch (SQLException e) {
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    } catch (NotFoundException e) {
      return Response.status(Response.Status.NOT_FOUND).build();
    } catch (IllegalArgumentException e) {
      return Response.status(Response.Status.BAD_REQUEST).build();

    }
  }

  /**
   * Delete owner (DELETE /owners/{ownerId})
   * 
   * @param ownerId
   * @return the deleted owner
   */
  @DELETE
  @Path("/owners/{ownerId}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response deleteOwner(@PathParam("ownerId") int ownerId) {
    try {

      Owner owner = databaseService.deleteOwner(ownerId);
      if (owner == null) {
        return Response.status(Response.Status.BAD_REQUEST).build();
      }
      return Response.noContent().build();
    } catch (NotFoundException e) {
      return Response.status(Response.Status.NOT_FOUND).build();
    } catch (SQLException e) {
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    } catch (ObjectReferenceException e) {
      return Response.status(Response.Status.BAD_REQUEST).build();
    }
  }

  /**
   * Get the owner with that last name
   * 
   * @param lastName
   * @return
   */
  @GET
  @Path("/owners")
  @Produces(MediaType.APPLICATION_JSON)
  public Response listOwners(@QueryParam("lastName") String lastName) {
    try {
      Owner owner = databaseService.listOwnerPets(lastName);
      return Response.ok(owner).build();
    } catch (SQLException e) {
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    } catch (NotFoundException e) {
      return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
    }

  }

  // NOTE: Pet methods #######################################

  // List pets (GET /pets)
  /**
   * get the list of all pets
   * 
   * @return
   */
  @GET
  @Path("/pets")
  @Produces(MediaType.APPLICATION_JSON)
  public Response listPets() {
    try {

      List<Pet> petlist = databaseService.listPets();
      return Response.ok(petlist).build();
    } catch (SQLException e) {
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }
  }

  /**
   * // Get a pet by ID (GET /pets/{petId})
   * 
   * @param petId
   * @return
   */
  @GET
  @Path("/pets/{petId}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getPet(@PathParam("petId") int petId) {

    Pet pet = databaseService.getPet(petId);
    System.out.println(pet);
    if (pet == null) {
      return Response.status(Response.Status.NOT_FOUND).build();
    }
    if (pet.getType() == null)
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    return Response.ok(pet).build();

  }

  /**
   * Adds a new pet
   * 
   * @param pet
   * @return the created Pet
   */
  @POST
  @Path("/pets")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response addPet(Pet pet) {
    try {

      System.out.println(pet.getType().toString() + ": is type with his id inside the <>");
      databaseService.addPet(pet);
      return Response.status(Response.Status.CREATED).entity(pet).build();

    } catch (BadRequestException e) {

      System.out.println("nah");
      return Response.status(Response.Status.BAD_REQUEST).build();
    }

  }

  /**
   * Update a Pet
   * 
   * @param petId
   * @param pet
   * @return the updated Pet
   */
  @PUT
  @Path("/pets/{petId}")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response updatePet(@PathParam("petId") long petId, Pet pet) {

    try {
      pet.setId(petId);

      databaseService.updatePet(petId, pet);
      return Response.ok(pet).build();

    } catch (NotFoundException e) {
      return Response.status(Response.Status.NOT_FOUND).build();
    } catch (SQLException e) {
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }
  }

  /**
   * Delete the pet with that id
   * 
   * @param petId
   * @return
   */
  @DELETE
  @Path("/pets/{petId}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response deletePet(@PathParam("petId") long petId) {

    try {
      Pet pet = databaseService.deletePet(petId);
      return Response.ok(pet).build();

    } catch (NotFoundException e) {
      return Response.status(Response.Status.NOT_FOUND).build();
    } catch (SQLException e) {
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }
  }

  /**
   * Get the Pet with that petId associated with the owner with that ownerId
   * 
   * @param ownerId
   * @param petId
   * @return
   */
  @GET
  @Path("/owners/{ownerId}/pets/{petId}")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response getOwnerPet(@PathParam("ownerId") long ownerId, @PathParam("petId") long petId) {

    try {
      Pet pet = databaseService.getOwnerPet(ownerId, petId);
      return Response.ok(pet).build();
    } catch (NotFoundException e) {
      return Response.status(Response.Status.NOT_FOUND).build();
    } catch (SQLException e) {
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }
  }

  /**
   * // Add a pet to an owner (POST /owners/{ownerId}/pets)
   * 
   * @param ownerId
   * @param pet
   * @return
   */
  @POST
  @Path("/owners/{ownerId}/pets")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response addPetToOwner(@PathParam("ownerId") long ownerId, Pet pet) {
    try {

      databaseService.addPetToOwner(ownerId, pet);
      pet.setOwner(databaseService.getOwner(ownerId));

      return Response.ok(pet).build();

    } catch (NotFoundException e) {
      return Response.status(Response.Status.NOT_FOUND).build();
    } catch (SQLException e) {
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }
  }

  // NOTE: vets methods ##################################################

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
