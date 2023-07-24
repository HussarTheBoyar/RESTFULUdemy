package Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfiguration {
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
    //1. Authenticating all request
    //http.authorizeHttpRequests(auth -> auth.anyRequest().authenticated());
    //2. Return default page if request is not authenticated
    //http.httpBasic(Customizer.withDefaults());
    //3. Allowing for PUT and POST (disable CSRF)
    //http.csrf().disable();

    http
        .authorizeHttpRequests(auth -> auth.anyRequest().authenticated())
        .httpBasic()
        .and()
        .formLogin().permitAll()
        .and().csrf().disable();
    return  http.build();
  }

}
