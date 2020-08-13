package softuni.springproject.web.config;

import softuni.springproject.web.filters.UserChefInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
@EnableScheduling
public class WebConfig implements WebMvcConfigurer {
}
