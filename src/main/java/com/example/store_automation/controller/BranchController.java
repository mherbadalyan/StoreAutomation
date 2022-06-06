package com.example.store_automation.controller;

import com.example.store_automation.model.dto.BranchDto;
import com.example.store_automation.model.entity.Branch;
import com.example.store_automation.response.EntityDeletingResponse;
import com.example.store_automation.response.EntityLookupResponse;
import com.example.store_automation.response.EntityUpdatingResponse;
import com.example.store_automation.service.BranchService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@RequestMapping("store-automation/branch")
@Transactional
public class BranchController {
    private static final Logger logger = LoggerFactory.getLogger(BranchController.class);

    private final BranchService branchService;

    public BranchController(BranchService branchService) {
        this.branchService = branchService;
    }


    @GetMapping("/{name}")
    public ResponseEntity<?> getBranch(@PathVariable("name") String name) {
        logger.info("Received a request to get a Branch.");
        Optional<BranchDto> branchDto = branchService.getBranch(name);

        if (branchDto.isPresent()) {
            logger.info("Branch  with given name found");
            return new EntityLookupResponse<BranchDto>().onSuccess(branchDto.get());
        }
        logger.warn("There is not Branch with given name");
        return new EntityLookupResponse<BranchDto>().onFailure("Branch");
    }
    @PutMapping("/{name}")
    @SecurityRequirement(name = "store_automation")
    public ResponseEntity<?> updateBranch(@RequestBody BranchDto branchDto,
                                            @PathVariable("name") String name){
        logger.info("Received a request to get a Branch.");
        Optional<BranchDto> optionalBranchDto =branchService.updateBranch(branchDto,name);
        if (optionalBranchDto.isEmpty()) {
            logger.warn("There is not a Branch with this name.");
            return new EntityUpdatingResponse<Branch>().onFailure("Branch");
        }
        logger.info("Branch successfully updated.");
        return new EntityUpdatingResponse<BranchDto>().onSuccess(optionalBranchDto.get());

    }

    @DeleteMapping("/{name}")
    @SecurityRequirement(name = "store_automation")
    public ResponseEntity<?> deleteBranch(@PathVariable("name") String name) {
        logger.info("Received a request to delete a Branch.");
        Optional<BranchDto> optionalBranchDto = branchService.getBranch(name);

        if (optionalBranchDto.isPresent()) {
            branchService.deleteBranch(name);
            return new EntityDeletingResponse<BranchDto>().onSuccess(optionalBranchDto.get(),"Branch");
        }
        logger.warn("There is not branch with given name");
        return new EntityLookupResponse<BranchDto>().onFailure("Branch");
    }




}
