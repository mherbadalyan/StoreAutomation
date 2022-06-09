package com.example.store_automation;

import com.example.store_automation.model.entity.Role;
import com.example.store_automation.repository.RoleRepository;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
@SpringBootApplication
@EnableScheduling

@SecurityScheme(name = "store_automation", scheme = "basic", type = SecuritySchemeType.HTTP, in = SecuritySchemeIn.HEADER)
@OpenAPIDefinition(info=@Info(title="Store Automation"))
public class StoreAutomationApplication implements CommandLineRunner {

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

    public static void main(String[] args) {
        SpringApplication.run(StoreAutomationApplication.class, args);
    }

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {

        if(roleRepository.findByName("GENERAL").isEmpty()){
            Role generalRole = new Role();
            generalRole.setName("GENERAL");
            roleRepository.save(generalRole);
        }

        if(roleRepository.findByName("BRANCH").isEmpty()) {
            Role branchRole = new Role();
            branchRole.setName("BRANCH");
            roleRepository.save(branchRole);
        }
    }
}



