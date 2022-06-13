package com.example.store_automation.controller;

import com.example.store_automation.internationalization.services.LocaleService;
import com.example.store_automation.model.dto.ProductInBranchDto;
import com.example.store_automation.model.dto.SalesDto;
import com.example.store_automation.model.entity.Branch;
import com.example.store_automation.model.entity.Product;
import com.example.store_automation.model.entity.ProductInBranch;
import com.example.store_automation.response.EntityCreatingResponse;
import com.example.store_automation.response.EntityLookupResponse;
import com.example.store_automation.response.EntityUpdatingResponse;
import com.example.store_automation.response.TransferResponse;
import com.example.store_automation.service.BranchService;
import com.example.store_automation.service.ProductInBranchService;
import com.example.store_automation.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("store-automation/productInBranch")
public class ProductInBranchController {

    private final ProductInBranchService productInBranchService;

    private final ProductService productService;

    private final BranchService branchService;

    private final LocaleService localeService;

    private static final Logger logger = LoggerFactory.getLogger(ProductInBranchController.class);

    @Operation(summary = "Create product in branch",
            description = "Creating product in branch"
    )
    @PostMapping("/{productId}/{quantity}/{price}/{expMonth}")
    @SecurityRequirement(name = "store_automation")
    private ResponseEntity<?> createProductInBranch(@PathVariable("productId")Long productId,
                                                    @PathVariable("quantity") Integer quantity,
                                                    @PathVariable("price") Double price,
                                                    @PathVariable("expMonth")Integer expMonth,
                                                    HttpServletRequest request) {
        logger.info("Received request to create productInBranch");

        if (!productService.existById(productId)) {
            logger.error("There is not product with given parameter");
            String message = localeService.getMessage("Product_not_found", request);
            return new EntityLookupResponse<Product>().onFailure(message);
        }

        Optional<ProductInBranchDto> optionalProductInBranchDto = productInBranchService.
                createProductInBranch(productId,quantity,price,expMonth);
        if (optionalProductInBranchDto.isEmpty()) {
            logger.error("There is a productInBranch with given parameter");
            return new EntityCreatingResponse<ProductInBranch>().onFailure("ProductInBranch");
        }
        logger.info("ProductInBranch successfully created");
        return new EntityCreatingResponse<ProductInBranchDto>().onSuccess(optionalProductInBranchDto.get());
    }


    @Operation(summary = "Transfer product from branch to branch",
            description = "Transfer product from branch to branch"
    )
    @PutMapping("/{branchId}/{productInBranchId}/{quantity}")
    @SecurityRequirement(name = "store_automation")
    private ResponseEntity<?> transferFromBranchToBranch(@PathVariable("quantity") Integer quantity,
                                                         @PathVariable("branchId")Long branchId,
                                                         @PathVariable("productInBranchId")Long productInBranchId,
                                                         HttpServletRequest request) {
        String message;
        if (!branchService.existById(branchId)) {
            logger.error("There is not branch with given parameter");
            message = localeService.getMessage("Branch_not_found", request);
            return new EntityLookupResponse<Branch>().onFailure(message);
        }

        if (!productInBranchService.existById(productInBranchId)) {
            logger.error("There is not productInBranch with given parameter");
            message = localeService.getMessage("Product_not_found", request);
            return new EntityLookupResponse<ProductInBranch>().onFailure(message);
        }

        Optional<ProductInBranchDto> productInBranchDto = productInBranchService.
                                                            transferFromBranchToBranch(
                                                                productInBranchId, branchId, quantity);
        if (productInBranchDto.isEmpty()) {
            logger.error("Insufficient products in the branch.");
            message = localeService.getMessage("Insufficient_products", request);
            return new TransferResponse<ProductInBranchDto>().insufficientQuantity(message);
        }
        logger.info("Product successfully transferred from branch to branch");
        return new EntityUpdatingResponse<ProductInBranchDto>().onSuccess(productInBranchDto.get());
    }

    @Operation(summary = "Sell product",
            description = "Selling product"
    )
    @PutMapping("/{productInBranchId}/{quantity}/{salePercent}/")
    @SecurityRequirement(name = "store_automation")
    private ResponseEntity<?> sellingProduct(@PathVariable("quantity") Integer quantity,
                                             @PathVariable("productInBranchId")Long productInBranchId,
                                             @PathVariable("salePercent")Integer salePercent,
                                             HttpServletRequest request) {
        logger.info("Product sales request received");

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String branchName = auth.getName();
        String message;
        if (!productInBranchService.existById(productInBranchId)) {
            logger.error("There is not product with given parameter");
            message = localeService.getMessage("Product_not_found", request);
            return new EntityLookupResponse<Product>().onFailure(message);
        }

        Optional<SalesDto> soledProduct = productInBranchService.
                sellingProduct(branchName,productInBranchId,quantity,salePercent);

        if (soledProduct.isEmpty()) {
            logger.error("There is no product with this id in this branch or requested quantity of product.");
            message = localeService.getMessage("Selling_failure", request);
            return new TransferResponse<SalesDto>().onFailureSelling(message);
        }

        logger.info("Product successfully sold");
        return new TransferResponse<SalesDto>().onSuccessSelling(soledProduct.get());
    }

    @Operation(summary = "Get products in branches by id",
            description = "Getting products in branches by id"
    )
    @GetMapping("/{id}")
    private ResponseEntity<?> getProductsById(@PathVariable("id") Long id,
                                              HttpServletRequest request) {
        logger.info("Received request to get products by id ");
        String message;
        if (!productService.existById(id)) {
            message = localeService.getMessage("Product_not_found", request);
            return new EntityLookupResponse<Product>().onFailure(message);
        }

        Optional<List<ProductInBranchDto>> productsList = productInBranchService.getProductsById(id);

        if (productsList.isEmpty()) {
            logger.error("There is no products with this id in branches.");
            message = localeService.getMessage("No_products_in_branches", request);
            return new EntityLookupResponse<Product>().onFailure(message);
        }
        logger.info("Product list with " + id + " id successfully found.");
        return new EntityLookupResponse<List<ProductInBranchDto>>().onSuccess(productsList.get());
    }
}
