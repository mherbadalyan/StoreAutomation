package com.example.store_automation.controller;

import com.example.store_automation.model.dto.ProductInBranchDto;
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
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("store-automation/productInBranch")
public class ProductInBranchController {

    private final ProductInBranchService productInBranchService;

    private final ProductService productService;

    private final BranchService branchService;

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @PostMapping("/{branchId}/{productId}/{quantity}/{price}")
    private ResponseEntity<?> createProductInBranch(@PathVariable("branchId")Long branchId,
                                                    @PathVariable("productId")Long productId,
                                                    @PathVariable("quantity") Integer quantity,
                                                    @PathVariable("price") Double price) {
        logger.info("Received request to create productInBranch");

        if (!productService.existById(productId)) {
            logger.error("There is not product with given parameter");
            return new EntityLookupResponse<Product>().onFailure("Product");
        }

        if (!branchService.existById(branchId)) {
            logger.error("There is not branch with given parameter");
            return new EntityLookupResponse<Branch>().onFailure("Branch");
        }


        Optional<ProductInBranchDto> optionalProductInBranchDto = productInBranchService.
                createProductInBranch(branchId,productId,quantity,price);
        if (optionalProductInBranchDto.isEmpty()) {
            logger.error("There is a productInBranch with given parameter");
            return new EntityCreatingResponse<ProductInBranch>().onFailure("ProductInBranch");
        }
        logger.info("ProductInBranch successfully created");
        return new EntityCreatingResponse<ProductInBranchDto>().onSuccess(optionalProductInBranchDto.get());
    }

    @PutMapping("/{branchId}/{productInBranchId}/{quantity}")
    private ResponseEntity<?> transferFromBranchToBranch(@PathVariable("quantity") Integer quantity,
                                                         @PathVariable("branchId")Long branchId,
                                                         @PathVariable("productInBranchId")Long productInBranchId) {
        if (!branchService.existById(branchId)) {
            logger.error("There is not branch with given parameter");
            return new EntityLookupResponse<Branch>().onFailure("Branch");
        }

        if (!productInBranchService.existById(productInBranchId)) {
            logger.error("There is not productInBranch with given parameter");
            return new EntityLookupResponse<ProductInBranch>().onFailure("ProductInBranch");
        }

        Optional<ProductInBranchDto> productInBranchDto = productInBranchService.
                                                            transferFromBranchToBranch(
                                                                productInBranchId, branchId, quantity);
        if (productInBranchDto.isEmpty()) {
            logger.error("Insufficient products in the branch.");
            return new TransferResponse<ProductInBranchDto>().insufficientQuantity();
        }
        logger.info("Product successfully transferred from branch to branch");
        return new EntityUpdatingResponse<ProductInBranchDto>().onSuccess(productInBranchDto.get());
    }

//    @PutMapping("/{branchId}/{productInBranchId}/{quantity}")
//    private ResponseEntity<?> sellingProduct(@PathVariable("quantity") Integer quantity,
//                                             @PathVariable("branchId")Long branchId,
//                                             @PathVariable("productInBranchId")Long productInBranchId) {
//
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        Branch branch = (Branch) auth.getDetails();
//        System.out.println(branch.getId());
//
//
//        return null;
//    }

}
