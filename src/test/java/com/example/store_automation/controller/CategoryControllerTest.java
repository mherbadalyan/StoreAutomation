package com.example.store_automation.controller;


import com.example.store_automation.model.entity.Category;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;




@SpringBootTest
@RunWith(SpringRunner.class)
public class CategoryControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    ObjectMapper mapper;

    @Before
    public void setupMockMvc(){
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void getSuccessTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                                .get("/store-automation/category/{name}", "Nerk")
                                .contentType(MediaType.APPLICATION_JSON_UTF8))
                                .andExpect(MockMvcResultMatchers.status().isOk())
                                .andDo(MockMvcResultHandlers.print());
    }


    @Test
    @Transactional
    public void postTest() throws Exception {
        Category category = new Category();
        category.setCategoryName("Bomb");

        String userJson = mapper.writeValueAsString(category);
        mockMvc.perform(MockMvcRequestBuilders.post("/store-automation/category")
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .content(userJson.getBytes()))
                                .andExpect(MockMvcResultMatchers.status().isOk())
                                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void getFailureTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/store-automation/category/{name}", "ggg")
                        .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void deleteCategorySuccessTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/store-automation/category/{name}", "M")
                        .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }
}
