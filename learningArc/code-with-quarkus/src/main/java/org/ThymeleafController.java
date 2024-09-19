package org;

import org.thymeleaf.context.Context;
import org.thymeleaf.TemplateEngine;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

@Path("thymeleaf")
public class ThymeleafController {
  private final TemplateEngine templateEngine;

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
