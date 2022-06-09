package com.example.store_automation.controller;

import com.example.store_automation.model.dto.CategoryDto;
import com.example.store_automation.model.dto.ProductDto;
import com.example.store_automation.model.dto.ProductInBranchDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ProductInBranchControllerTest {

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
    @WithMockUser(username = "Gor")
    public void createProductInBranchSuccessTest() throws Exception {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(1L);

        ProductDto product = new ProductDto();
        product.setId(1L);
        product.setCategoryDto(categoryDto);

        ProductInBranchDto productInBranchDto = new ProductInBranchDto();
        productInBranchDto.setProductDto(product);
        productInBranchDto.setDate(LocalDate.now());
        productInBranchDto.setExpDate(LocalDate.now().plusDays(16L));

        String userJson = mapper.writeValueAsString(productInBranchDto);
        mockMvc.perform(MockMvcRequestBuilders.post("/store-automation/productInBranch/{productId}/{quantity}/{price}/{expMonth}",
                                product.getId(),
                                10,
                                78900.0,
                                56
                                )
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(userJson.getBytes()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void getProductInBranchTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/store-automation/productInBranch/{id}", "1")
                        .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());

    }

    @Test
    public void transferFromBranchToBranchTest() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/store-automation/productInBranch/{branchId}/{productInBranchId}/{quantity}",
                                1L,
                                5L,
                                1)
                        .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }


    @Test
    @WithMockUser(username = "Gor")
    public void sellingProductTest() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/store-automation/productInBranch/{productInBranchId}/{quantity}/{salePercent}",
                                1L,
                                1,
                                20)
                        .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }




}
