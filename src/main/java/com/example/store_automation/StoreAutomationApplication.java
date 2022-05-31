package com.example.store_automation;

import com.example.store_automation.model.entity.Role;
import com.example.store_automation.repository.RoleRepository;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@SecurityScheme(name = "store_automation", scheme = "basic", type = SecuritySchemeType.HTTP, in = SecuritySchemeIn.HEADER)
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

        Role generalRole = new Role();

        if(roleRepository.findByName("GENERAL").isEmpty()){
            generalRole.setName("GENERAL");
            roleRepository.save(generalRole);
        }

        Role branchRole = new Role();
        if(roleRepository.findByName("BRANCH").isEmpty()) {
            branchRole.setName("BRANCH");
            roleRepository.save(branchRole);
        }


    }

}
