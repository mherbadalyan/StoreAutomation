package com.example.store_automation.controller;

import com.example.store_automation.model.dto.CategoryDto;
import com.example.store_automation.model.dto.ProductDto;
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
public class ProductControllerTests {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    ObjectMapper mapper;

    @Before
    public void setupMockMvc() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    @Transactional
    public void createProductSuccessTest() throws Exception {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setCategoryName("Nerk");

        ProductDto product = new ProductDto();
        product.setCategoryDto(categoryDto);
        product.setProductName("Latex Italia Blue 50L");
        product.setPrice(60000.0);
        product.setPercent(27.0);


        String userJson = mapper.writeValueAsString(product);
        mockMvc.perform(MockMvcRequestBuilders.post("/store-automation/product")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(userJson.getBytes()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @Transactional
    public void createProductFailureTest() throws Exception {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setCategoryName("Bomb");

        ProductDto product = new ProductDto();
        product.setCategoryDto(categoryDto);
        product.setProductName("Latex Italia Blue 20L");
        product.setPrice(60000.0);
        product.setPercent(27.0);


        String userJson = mapper.writeValueAsString(product);
        mockMvc.perform(MockMvcRequestBuilders.post("/store-automation/product")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(userJson.getBytes()))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andDo(MockMvcResultHandlers.print());
    }
}
