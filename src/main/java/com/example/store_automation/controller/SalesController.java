package com.example.store_automation.controller;

import com.example.store_automation.model.dto.ProductInBranchDto;
import com.example.store_automation.model.entity.Sales;
import com.example.store_automation.model.mapper.SalesMapper;
import com.example.store_automation.response.EntityLookupResponse;
import com.example.store_automation.response.TransferResponse;
import com.example.store_automation.service.RemoveOldsSoldProductsService;
import com.example.store_automation.service.SalesService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/store-automation/sales")
@AllArgsConstructor
public class SalesController {

    private final SalesService salesService;

    private final SalesMapper salesMapper;

    private final RemoveOldsSoldProductsService removeOldsSoldProductsService;

    private static final Logger logger = LoggerFactory.getLogger(SalesController.class);

    @PutMapping("/{id}/{quantity}")
    private ResponseEntity<?> returnProduct(@PathVariable("id") Long id,
                                            @PathVariable("quantity") Integer quantity) {

        logger.info("Received request to return product");

        if (!salesService.existById(id)) {
            logger.error("There is not sold product with id " + id);
            return new EntityLookupResponse<Sales>().onFailure("Sold product");
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String branchName = auth.getName();

        Optional<ProductInBranchDto> returnedProduct = salesService.returnProduct(id,branchName,quantity);

        if (returnedProduct.isEmpty()) {
            logger.error("Can't return sold product by sale id " + id);
            return new TransferResponse<ProductInBranchDto>().onFailureReturning();
        }

        logger.info("Returned product with productInBranch id " + returnedProduct.get().getId());
        return new TransferResponse<ProductInBranchDto>().onSuccessReturning(returnedProduct.get());
    }
    @DeleteMapping("deleteOldProducts")
    private void deleteProducts() {
        removeOldsSoldProductsService.removeOldProducts();
    }

}
