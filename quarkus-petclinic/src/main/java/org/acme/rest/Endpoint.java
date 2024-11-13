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
import java.util.Set;

import javax.naming.NamingException;

import org.json.JSONObject;

@Path("petclinic/api")
public class Endpoint {

  @Inject
  DatabaseService databaseService;

  /**
   * @return all the instances of all the tables in the DB
   */
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Response homePage() {
    try {
      JSONObject result = databaseService.fetchAllTablesData();
      return Response.ok(result.toString()).build();
    } catch (Exception e) {
      return Response.ok(e.getMessage() + "aaaaaaaaaaaaa").build();
    }
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
    try {

      Pet pet = databaseService.getPet(petId);
      if (pet == null) {
        return Response.status(Response.Status.NOT_FOUND).build();
      }
      if (pet.getType() == null)
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
      return Response.ok(pet).build();
    } catch (SQLException e) {
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }

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

      databaseService.addPet(pet);
      return Response.status(Response.Status.CREATED).entity(pet).build();

    } catch (BadRequestException e) {
      return Response.status(Response.Status.BAD_REQUEST).build();
    } catch (SQLException e) {
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
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

  // NOTE: pettypes methods ##################################################

  @GET
  @Path("/pettypes/{pettypeId}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getPettype(@PathParam("pettypeId") long pettypeId) {
    try {
      Type type = databaseService.getType(pettypeId);
      if (type == null) {

        return Response.status(Response.Status.NOT_FOUND).build();
      }

      return Response.ok(type).build();
    } catch (SQLException e) {
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }
  }

  /**
   * get pettypes Set
   * 
   * @return the Set of pet types
   */
  @GET
  @Path("/pettypes")
  @Produces(MediaType.APPLICATION_JSON)
  public Response listPetTypes() {
    try {
      Set<Type> types = databaseService.listPetTypes();
      return Response.ok(types).build();

    } catch (SQLException e) {
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }
  }

  /**
   * create pettype given the name
   * 
   * @param petType
   * @return
   */
  @POST
  @Path("/pettypes")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response addPetType(Type petType) {
    try {

      databaseService.addPetType(petType);
      return Response.status(Response.Status.CREATED).entity(petType).build();
    } catch (SQLException e) {
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    } catch (NamingException e) {
      return Response.status(Response.Status.BAD_REQUEST).build();
    }
  }

  /**
   * update pettype
   * 
   * @param petType
   * @return
   */
  @PUT
  @Path("/pettypes/{petTypeId}")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response updatePetType(Type petType, @PathParam("petTypeId") long petTypeId) {
    try {
      if (petTypeId != petType.getId()) {
        return Response.status(Response.Status.BAD_REQUEST).build();
      }
      databaseService.updatePetType(petType);
      return Response.ok(petType).build();
    } catch (SQLException e) {
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    } catch (NamingException e) {
      return Response.status(Response.Status.BAD_REQUEST).build();
    } catch (NotFoundException e) {
      return Response.status(Response.Status.NOT_FOUND).build();
    }
  }

  /**
   * detele pettype
   * 
   * @param petTypeId
   * @return
   */
  @DELETE
  @Path("/pettypes/{petTypeId}")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response deletePetType(@PathParam("petTypeId") long petTypeId) {
    try {
      Type type = databaseService.deletePetType(petTypeId);
      return Response.ok(type).build();
    } catch (SQLException e) {
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    } catch (ObjectReferenceException e) {
      return Response.status(Response.Status.BAD_REQUEST).build();
    } catch (NotFoundException e) {
      return Response.status(Response.Status.NOT_FOUND).build();
    }
  }

  // NOTE: Visits

  @GET
  @Path("/visits/{visitId}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getVisit(@PathParam("visitId") long visitId) {
    try {
      Visit visit = databaseService.getVisit(visitId);
      if (visit == null) {
        return Response.status(Response.Status.NOT_FOUND).build();
      }
      return Response.ok(visit).build();
    } catch (SQLException e) {
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }
  }

  /**
   * get visits Set
   * 
   * @return the Set of pet types
   */
  @GET
  @Path("/visits")
  @Produces(MediaType.APPLICATION_JSON)
  public Response listVisits() {
    try {
      Set<Visit> types = databaseService.listVisits();
      return Response.ok(types).build();

    } catch (SQLException e) {
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }
  }

  /**
   * create visit no pet id (nonsense)
   * 
   * @return
   */
  @POST
  @Path("/visits")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response addVisit(Visit visit) {
    try {

      databaseService.addVisit(visit);
      return Response.status(Response.Status.CREATED).entity(visit).build();
    } catch (SQLException e) {
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    } catch (NamingException e) {
      return Response.status(Response.Status.BAD_REQUEST).build();
    }
  }

  /**
   * create visit for a pet that has a owner
   * 
   * @return
   */
  @POST
  @Path("/owners/{ownerId}/pets/{petId}/visits")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response addVisitToPet(Visit visit, @PathParam("ownerId") long ownerId, @PathParam("petId") long petId) {
    try {

      databaseService.addVisitToPet(visit, ownerId, petId);
      return Response.status(Response.Status.CREATED).entity(visit).build();
    } catch (SQLException e) {
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    } catch (NamingException e) {
      return Response.status(Response.Status.BAD_REQUEST).build();
    } catch (NotFoundException e) {
      return Response.status(Response.Status.NOT_FOUND).build();
    }
  }

  /**
   * update visit
   * 
   * @param visit
   * @return
   */
  @PUT
  @Path("/visits/{visitId}")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response updateVisit(Visit visit, @PathParam("visitId") long visitId) {
    try {
      visit.setId(visitId);
      databaseService.updateVisit(visit);
      return Response.ok(visit).build();
    } catch (SQLException e) {
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    } catch (NamingException e) {
      return Response.status(Response.Status.BAD_REQUEST).build();
    } catch (NotFoundException e) {
      return Response.status(Response.Status.NOT_FOUND).build();
    }
  }

  /**
   * detele visit
   * 
   * @param visitId
   * @return
   */
  @DELETE
  @Path("/visits/{visitId}")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response deleteVisit(@PathParam("visitId") long visitId) {
    try {
      Visit visit = databaseService.deleteVisit(visitId);
      return Response.ok(visit).build();
    } catch (SQLException e) {
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    } catch (ObjectReferenceException e) {
      return Response.status(Response.Status.BAD_REQUEST).build();
    } catch (NotFoundException e) {
      return Response.status(Response.Status.NOT_FOUND).build();
    }
  }

  // NOTE: Specialties ##########################################################

  /**
   * get Specialty by id
   * 
   * @param specialtyId
   * @return
   */
  @GET
  @Path("/specialties/{specialtyId}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getSpecialty(@PathParam("specialtyId") long specialtyId) {
    try {
      Specialty specialty = databaseService.getSpecialty(specialtyId);
      if (specialty == null) {
        return Response.status(Response.Status.NOT_FOUND).build();
      }
      return Response.ok(specialty).build();
    } catch (SQLException e) {
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }
  }

  /**
   * get a set of Specialty
   * 
   * @return
   */
  @GET
  @Path("/specialties")
  @Produces(MediaType.APPLICATION_JSON)
  public Response listSpecialties() {
    try {
      Set<Specialty> specialtyes = databaseService.listSpecialties();
      return Response.ok(specialtyes).build();
    } catch (SQLException e) {
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }
  }

  /**
   * create specialty given the name
   * 
   * @param petType
   * @return
   */
  @POST
  @Path("/specialties")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response addSpecialty(Specialty specialty) {
    try {

      databaseService.addSpecialty(specialty);
      return Response.status(Response.Status.CREATED).entity(specialty).build();
    } catch (SQLException e) {
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    } catch (NamingException e) {
      return Response.status(Response.Status.BAD_REQUEST).build();
    }
  }

  /**
   * update specialty
   * 
   * @param petType
   * @return
   */
  @PUT
  @Path("/specialties/{specialtyId}")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response updateSpecialty(Specialty specialty, @PathParam("specialtyId") long specialtyId) {
    try {
      specialty.setId(specialtyId);
      databaseService.updateSpecialty(specialty);
      return Response.ok(specialty).build();
    } catch (SQLException e) {
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    } catch (NamingException e) {
      return Response.status(Response.Status.BAD_REQUEST).build();
    } catch (NotFoundException e) {
      return Response.status(Response.Status.NOT_FOUND).build();
    }
  }

  /**
   * detele specialty
   * 
   * @param specialtyId
   * @return
   */
  @DELETE
  @Path("/specialties/{specialtyId}")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response deleteSpecialty(@PathParam("specialtyId") long specialtyId) {
    try {
      Specialty specialty = databaseService.deleteSpecialty(specialtyId);
      return Response.ok(specialty).build();
    } catch (SQLException e) {
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    } catch (ObjectReferenceException e) {
      return Response.status(Response.Status.BAD_REQUEST).build();
    } catch (NotFoundException e) {
      return Response.status(Response.Status.NOT_FOUND).build();
    }
  }

  // NOTE: vets methods ##################################################

  /**
   * Get a vet by ID (GET /vets/{vetsId})
   * 
   * @param vetId
   * @return
   */
  @GET
  @Path("/vets/{vetId}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getvet(@PathParam("vetId") long vetId) {
    try {

      Vet vet = databaseService.getVet(vetId);
      if (vet == null) {
        return Response.status(Response.Status.NOT_FOUND).build();
      }
      return Response.ok(vet).build();
    } catch (SQLException e) {
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }
  }

  /**
   * get the list of all vets
   * 
   * @return
   */
  @GET
  @Path("/vets")
  @Produces(MediaType.APPLICATION_JSON)
  public Response listVets() {
    try {

      Set<Vet> petlist = databaseService.listVets();
      return Response.ok(petlist).build();
    } catch (SQLException e) {
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }
  }

  /**
   * Adds a new vet (needs specialty)
   * 
   * @param vet
   * @return the created Vet
   */
  @POST
  @Path("/vets")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response addVet(Vet vet) {
    try {

      databaseService.addVet(vet);
      return Response.status(Response.Status.CREATED).entity(vet).build();

    } catch (BadRequestException e) {
      return Response.status(Response.Status.BAD_REQUEST).build();
    } catch (SQLException e) {
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }

  }

  /**
   * updates a vet (needs specialty)
   * 
   * @param vet
   * @return the created Vet
   */
  @PUT
  @Path("/vets/{vetId}")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response updateVet(Vet vet, @PathParam("vetId") long vetId) {
    try {

      vet.setId(vetId);
      databaseService.updateVet(vet);
      return Response.status(Response.Status.CREATED).entity(vet).build();

    } catch (BadRequestException e) {
      return Response.status(Response.Status.BAD_REQUEST).build();
    } catch (SQLException e) {
      e.printStackTrace();
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }

  }

  /**
   * Delete vet
   * 
   * @param ownerId
   * @return the deleted owner
   */
  @DELETE
  @Path("/vets/{vetId}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response deleteOwner(@PathParam("vetId") long vetId) {
    try {

      Vet vet = databaseService.deleteVet(vetId);
      if (vet == null) {
        return Response.status(Response.Status.BAD_REQUEST).build();
      }
      return Response.ok(vet).build();
    } catch (NotFoundException e) {
      return Response.status(Response.Status.NOT_FOUND).build();
    } catch (SQLException e) {
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }
  }

}
