package com.example.store_automation.controller;

import com.example.store_automation.StoreAutomationApplication;
import com.example.store_automation.model.dto.BranchDto;
import com.example.store_automation.model.dto.LoginDto;
import com.example.store_automation.model.dto.SignUpDto;
import com.example.store_automation.model.entity.Branch;
import com.example.store_automation.model.entity.Role;
import com.example.store_automation.repository.RoleRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.transaction.Transactional;
import java.util.Collections;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = StoreAutomationApplication.class)
@AutoConfigureMockMvc
public class AuthControllerTest {

    @Autowired
    private  RoleRepository roleRepository;


    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;



    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Autowired
    ObjectMapper mapper;


    @Test
    @Transactional
    public void postTestLogin() throws Exception {
        LoginDto loginDto = new LoginDto();
        loginDto.setName("Jiro");
        loginDto.setPassword("1111");

        String userJson = mapper.writeValueAsString(loginDto);
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/auth/signin")
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .content(userJson.getBytes()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
        
    }

    @Test
    @Transactional
    public void postTestRegister() throws Exception {

        SignUpDto signUpDto=new SignUpDto();
        signUpDto.setRole("GENERAL");
        signUpDto.setName("Rsutuni");
        signUpDto.setAddress("13Dar");
        signUpDto.setPassword(passwordEncoder.encode("1111"));


        String userJson = mapper.writeValueAsString(signUpDto);
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/auth/signup")
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .content(userJson.getBytes()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }
}
