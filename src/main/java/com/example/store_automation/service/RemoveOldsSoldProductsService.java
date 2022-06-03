package com.example.store_automation.service;

import com.example.store_automation.model.entity.ProductInBranch;
import com.example.store_automation.repository.ProductInBranchRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class RemoveOldsSoldProductsService {

    private final ProductInBranchRepository productInBranchRepository;


    public void removeOldProducts(){
        LocalDate date = LocalDate.now().minusDays(14);
        List<ProductInBranch> productsListToRemove = productInBranchRepository.
                                                                getProductsListToRemove(date);

        System.out.println(productsListToRemove);
        productInBranchRepository.deleteAll(productsListToRemove);
    }
}
