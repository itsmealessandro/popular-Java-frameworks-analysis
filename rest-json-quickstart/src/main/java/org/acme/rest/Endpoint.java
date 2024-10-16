package org.acme.rest;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("")
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
}
