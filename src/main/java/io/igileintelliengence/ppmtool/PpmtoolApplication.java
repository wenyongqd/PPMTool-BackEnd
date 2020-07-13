package io.igileintelliengence.ppmtool;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.springframework.boot.SpringApplication.run;

@SpringBootApplication
public class PpmtoolApplication {

    @Bean
    BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    public static void main(String[] args) {
        run(PpmtoolApplication.class, args);
    }

}
