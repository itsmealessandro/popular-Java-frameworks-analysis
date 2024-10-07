package controllers;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Controller("/thymeleaf")
public class ThymeleafController {

  TemplateEngine templateEngine;

  // NOTE: Iniezione
  public ThymeleafController(TemplateEngine templateEngine) {
    this.templateEngine = templateEngine;
  }

  @Get("/")
  public String renderTemplate() {
    Context context = new Context();
    context.setVariable("name", "Micronaut with Thymeleaf");
    return templateEngine.process("home", context); // 'home' si riferisce al template home.html
  }
}
