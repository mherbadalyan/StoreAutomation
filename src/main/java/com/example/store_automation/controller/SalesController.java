package com.example.store_automation.controller;

import com.example.store_automation.internationalization.services.LocaleService;
import com.example.store_automation.model.dto.ProductInBranchDto;
import com.example.store_automation.model.entity.Sales;
import com.example.store_automation.model.mapper.SalesMapper;
import com.example.store_automation.response.EntityLookupResponse;
import com.example.store_automation.response.TransferResponse;
import com.example.store_automation.service.RemoveOldsSoldProductsService;
import com.example.store_automation.service.SalesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@RestController
@RequestMapping("/store-automation/sales")
@AllArgsConstructor
public class SalesController {

    private final SalesService salesService;

    private final LocaleService localeService;

    private static final Logger logger = LoggerFactory.getLogger(SalesController.class);

    @PutMapping("/{id}/{quantity}")
    @Operation(summary = "Return product",
            description = "Returning product"
    )
    @SecurityRequirement(name = "store_automation")
    private ResponseEntity<?> returnProduct(@PathVariable("id") Long id,
                                            @PathVariable("quantity") Integer quantity,
                                            HttpServletRequest request) {

        logger.info("Received request to return product with id " + id);
        String message;
        if (!salesService.existById(id)) {
            logger.error("There is no sold product with id " + id);
            message = localeService.getMessage("Product_not_found", request);
            return new EntityLookupResponse<Sales>().onFailure(message);
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String branchName = auth.getName();

        Optional<ProductInBranchDto> returnedProduct = salesService.returnProduct(id,branchName,quantity);

        if (returnedProduct.isEmpty()) {
            logger.error("Can't return sold product by sale id " + id);
            message = localeService.getMessage("Return_failure", request);
            return new TransferResponse<ProductInBranchDto>().onFailureReturning(message);
        }

        logger.info("Returned product with productInBranch id " + returnedProduct.get().getId());
        return new TransferResponse<ProductInBranchDto>().onSuccessReturning(returnedProduct.get());
    }
}
