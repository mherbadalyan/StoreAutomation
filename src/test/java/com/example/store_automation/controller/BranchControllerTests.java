package com.example.store_automation.controller;

import com.example.store_automation.StoreAutomationApplication;
import com.example.store_automation.model.dto.BranchDto;
import com.example.store_automation.model.entity.Branch;
import com.example.store_automation.model.entity.Category;
import com.example.store_automation.repository.BranchRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.val;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.Optional;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = StoreAutomationApplication.class)
@AutoConfigureMockMvc
public class BranchControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BranchRepository branchRepository;

    @Autowired
    private WebApplicationContext webApplicationContext;
    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Autowired
    ObjectMapper mapper;

    @Test
    public void testGetBranch() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/store-automation/branch/{name}", "Tigran")
                                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @Transactional
    public void putBranchTest() throws Exception {

        BranchDto optionalBranch =new BranchDto();
       optionalBranch.setName("bobo");
       optionalBranch.setAddress("bbb");
       optionalBranch.setPassword("1111");
        String userJson = mapper.writeValueAsString(optionalBranch);
        mockMvc.perform(MockMvcRequestBuilders.put("/store-automation/branch/{name}","Jiro")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(userJson.getBytes()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

 

}
