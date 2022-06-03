package com.example.store_automation.service;

import com.example.store_automation.model.entity.Branch;
import com.example.store_automation.model.entity.ProductInBranch;
import com.example.store_automation.repository.BranchRepository;
import com.example.store_automation.repository.ProductInBranchRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class RemoveOldsSoldProductsService {

    private final ProductInBranchRepository productInBranchRepository;

    private final BranchRepository branchRepository;

    public void removeOldProducts(){

        LocalDate date = LocalDate.now().minusDays(14);

        List<ProductInBranch> productsListToRemove = productInBranchRepository.
                                                                getProductsListToRemove(date);

        productInBranchRepository.deleteAll(productsListToRemove);
    }
}
