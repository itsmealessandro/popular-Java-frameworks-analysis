package org.acme.rest;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

@Path("")
public class Endpoint {

  @GET
  public String hello() {
    return "jakarta and quarkus";
  }

  @GET
  @Path("hello")
  public String helloGET() {
    return "jakarta and quarkus from path";
  }
}
