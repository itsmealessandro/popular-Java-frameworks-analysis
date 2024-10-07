package template;

import io.micronaut.context.annotation.Factory;
import io.micronaut.context.annotation.Bean;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templatemode.TemplateMode;

@Factory
public class ThymeleafConfig {

  @Bean
  public TemplateEngine templateEngine() {
    ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();

    // Configurazione del resolver, simile all'esempio fornito
    templateResolver.setTemplateMode(TemplateMode.HTML);
    templateResolver.setPrefix("/templates/");
    templateResolver.setSuffix(".html");
    templateResolver.setCacheable(true);
    templateResolver.setCacheTTLMs(3600000L);
    templateResolver.setCharacterEncoding("UTF-8");

    TemplateEngine templateEngine = new TemplateEngine();
    templateEngine.setTemplateResolver(templateResolver);
    return templateEngine;
  }
}
