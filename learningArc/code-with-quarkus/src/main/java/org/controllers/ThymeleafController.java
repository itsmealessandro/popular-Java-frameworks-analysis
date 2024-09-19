package org.controllers;

import org.thymeleaf.context.Context;
import org.thymeleaf.TemplateEngine;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class ThymeleafController {
  private final TemplateEngine templateEngine;

  @Inject
  public ThymeleafController(TemplateEngine templateEngine) {
    this.templateEngine = templateEngine;
  }

  @GET
  public String renderTemplate() {
    Context context = new Context();
    context.setVariable("name", "Quarkus with Thymeleaf");
    return templateEngine.process("home", context); // 'home' si riferisce al template home.html
  }
}
