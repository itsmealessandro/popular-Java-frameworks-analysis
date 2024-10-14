package org.acme.rest;

import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

@Path("")
public class Endpoint {

  @Inject
  DatabaseService databaseService;

  @GET
  public String hello() {

    return "DB name: " +databaseService.getDatabaseName() +"---------" + "First Pet Name: " + databaseService.listTables() + "-----" + "pets: " + databaseService.getAllPets();
  }

  @GET
  @Path("hello")
  public String helloGET() {
    return "jakarta and quarkus from path";
  }
}
