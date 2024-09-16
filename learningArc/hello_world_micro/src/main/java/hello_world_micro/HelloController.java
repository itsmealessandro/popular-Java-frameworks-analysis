
package hello_world_micro;

import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;

@Controller("/hello") // (1)
public class HelloController {

  @Get(produces = MediaType.TEXT_PLAIN) // (2)
  public String index() {
    return "Hello World"; // (3)
  }
}
