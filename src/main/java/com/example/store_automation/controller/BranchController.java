package com.example.store_automation.controller;

import com.example.store_automation.internationalization.services.LocaleService;
import com.example.store_automation.model.dto.BranchDto;
import com.example.store_automation.model.entity.Branch;
import com.example.store_automation.response.EntityDeletingResponse;
import com.example.store_automation.response.EntityLookupResponse;
import com.example.store_automation.response.EntityUpdatingResponse;
import com.example.store_automation.service.BranchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;


@RestController
@RequestMapping("store-automation/branch")
@Transactional
@AllArgsConstructor
public class BranchController {
    private static final Logger logger = LoggerFactory.getLogger(BranchController.class);

    private final BranchService branchService;

    private final LocaleService localeService;



    @Operation(summary = "Get branch",
            description = "Getting branch by name"
    )
    @GetMapping("/{name}")
    public ResponseEntity<?> getBranch(@PathVariable("name") String name , HttpServletRequest request) {
        logger.info("Received a request to get a Branch by name " + name);
        Optional<BranchDto> branchDto = branchService.getBranch(name);

        if (branchDto.isPresent()) {
            logger.info("Branch with name " + name + " found");
            return new EntityLookupResponse<BranchDto>().onSuccess(branchDto.get());
        }
        logger.warn("There is not Branch with name " + name);
        String message = localeService.getMessage("Branch_not_found", request);
        return new EntityLookupResponse<BranchDto>().onFailure(message);
    }

    @Operation(summary = "Update branch",
            description = "Updating branch by name"
    )
    @PutMapping("/{name}")
    @SecurityRequirement(name = "store_automation")
    public ResponseEntity<?> updateBranch(@RequestBody BranchDto branchDto,
                                          @PathVariable("name") String name,
                                          HttpServletRequest request) {
        logger.info("Received a request to update a Branch by name " + name);
        Optional<BranchDto> optionalBranchDto = branchService.updateBranch(branchDto, name);
        if (optionalBranchDto.isEmpty()) {
            logger.warn("There is not a Branch with name " + name);
            String message = localeService.getMessage("Branch_not_found", request);
            return new EntityLookupResponse<Branch>().onFailure(message);
        }
        logger.info("Branch successfully updated.");
        return new EntityUpdatingResponse<BranchDto>().onSuccess(optionalBranchDto.get());
    }
    @Operation(summary = "Delete branch",
            description = "Deleting branch by name"
    )
    @DeleteMapping("/{name}")
    @SecurityRequirement(name = "store_automation")
    public ResponseEntity<?> deleteBranch(@PathVariable("name") String name, HttpServletRequest request) {
        logger.info("Received a request to delete a Branch wit name " + name);
        Optional<BranchDto> optionalBranchDto = branchService.getBranch(name);

        if (optionalBranchDto.isPresent()) {
            branchService.deleteBranch(name);
            return new EntityDeletingResponse<BranchDto>()
                    .onSuccess(optionalBranchDto.get(), "Branch");
        }
        logger.warn("There is not branch with given name");
        String message = localeService.getMessage("Branch_not_found", request);
        return new EntityLookupResponse<BranchDto>().onFailure(message);
    }
}
