package org.acme.rest;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

@Path("")
public class Endpoint {

  @GET
  public String hello() {
    return "jakarta and quarkus";
  }
}
