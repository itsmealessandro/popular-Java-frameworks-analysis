package hello_world_micro;

import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Controller("/hello")
public class HelloController {

  private final TemplateEngine templateEngine;

  // Iniezione tramite costruttore
  public HelloController(TemplateEngine templateEngine) {
    this.templateEngine = templateEngine;
  }

  @Get(produces = MediaType.TEXT_HTML) // Produci HTML anziché testo semplice
  public String index() {
    // Creiamo un contesto Thymeleaf
    Context context = new Context();
    context.setVariable("myData", "Micronaut with Thymeleaf");

    // Renderizziamo il template 'home.html' e lo restituiamo
    return templateEngine.process("home", context); // 'home' si riferisce a /templates/home.html
  }
}
