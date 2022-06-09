package com.example.store_automation.service;

import com.example.store_automation.model.dto.ProductInBranchDto;
import com.example.store_automation.model.entity.ProductInBranch;
import com.example.store_automation.model.entity.Sales;
import com.example.store_automation.model.mapper.ProductInBranchMapper;
import com.example.store_automation.repository.ProductInBranchRepository;
import com.example.store_automation.repository.SalesRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SalesService {

    private final SalesRepository salesRepository;

    private final ProductInBranchRepository productInBranchRepository;

    private final ProductInBranchMapper productInBranchMapper;

    private final IncomeCalcService incomeCalcService;

    public boolean existById(Long id) {
        return salesRepository.existsById(id);
    }

    public Optional<ProductInBranchDto> returnProduct(Long id, String branchName, Integer quantity) {

        Optional<Sales> opSaleFromData = salesRepository.findById(id);

        if (!opSaleFromData.get().getBranch().getName().equals(branchName) ||
            opSaleFromData.get().getQuantity() < quantity) {
            return Optional.empty();
        }

        int newQuantity = opSaleFromData.get().getQuantity() - quantity;
        Double newPrice = (opSaleFromData.get().getPrice() / opSaleFromData.get().getQuantity() ) * newQuantity;

        LocalDate saleDate = opSaleFromData.get().getSalesDate().toLocalDate();
        if (!saleDate.equals(LocalDate.now())) {
            Double incomeToReturn = (opSaleFromData.get().getPrice() - newPrice ) -
                    quantity * opSaleFromData.get().getPriceIn();
            incomeCalcService.changeIncomeInDateByBranchId(incomeToReturn,
                                                            saleDate,
                                                            opSaleFromData.get().getBranch().getId());
        }
        Optional<ProductInBranch> uniqueProduct = productInBranchRepository.findUniqueProduct(
                opSaleFromData.get().getBranch().getId(),
                opSaleFromData.get().getProduct().getId(),
                opSaleFromData.get().getDateIn(),
                opSaleFromData.get().getExpDate(), opSaleFromData.get().getPriceIn());

        if (uniqueProduct.isEmpty()) {
            return Optional.empty();
        }

        if (newQuantity == 0) {
            salesRepository.delete(opSaleFromData.get());
        }else {
            opSaleFromData.get().setQuantity(newQuantity);
            opSaleFromData.get().setPrice(newPrice);
        }

        newQuantity = uniqueProduct.get().getQuantity() + quantity;

        uniqueProduct.get().setQuantity(newQuantity);

        ProductInBranch savedPrInBr = productInBranchRepository.save(uniqueProduct.get());
        return Optional.of(productInBranchMapper.convertToDto(savedPrInBr));
    }
}
