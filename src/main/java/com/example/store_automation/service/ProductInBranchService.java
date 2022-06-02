package com.example.store_automation.service;

import com.example.store_automation.model.dto.ProductInBranchDto;
import com.example.store_automation.model.dto.SalesDto;
import com.example.store_automation.model.entity.Branch;
import com.example.store_automation.model.entity.Product;
import com.example.store_automation.model.entity.ProductInBranch;
import com.example.store_automation.model.entity.Sales;
import com.example.store_automation.model.mapper.ProductInBranchMapper;
import com.example.store_automation.model.mapper.SalesMapper;
import com.example.store_automation.repository.BranchRepository;
import com.example.store_automation.repository.ProductInBranchRepository;
import com.example.store_automation.repository.ProductRepository;
import com.example.store_automation.repository.SalesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductInBranchService {

    private final ProductInBranchRepository productInBranchRepository;

    private final ProductRepository productRepository;

    private final BranchRepository branchRepository;

    private final ProductInBranchMapper productInBranchMapper;

    private final SalesRepository salesRepository;

    private final SalesMapper salesMapper;
    public Optional<ProductInBranchDto> createProductInBranch(Long branchId,
                                                              Long productId,
                                                              Integer quantity,
                                                              Double price) {

        Optional<Product> optionalProduct = productRepository.findById(productId);

        Optional<Branch> optionalBranch = branchRepository.findById(branchId);

        if (optionalBranch.isEmpty() || optionalProduct.isEmpty()) {
            return Optional.empty();
        }

        optionalProduct.get().setPrice(price);

        ProductInBranch productInBranchToSave = ProductInBranch.builder().
                branch(optionalBranch.get()).
                product(optionalProduct.get()).
                date(LocalDateTime.now()).
                quantity(quantity).build();

        ProductInBranch savedProductInBranch = productInBranchRepository.save(productInBranchToSave);
        return Optional.of(productInBranchMapper.convertToDto(savedProductInBranch));
    }

    public Optional<ProductInBranchDto> transferFromBranchToBranch(Long productInBranchId, Long branchId, Integer quantity) {
        Optional<ProductInBranch> optionalProductInBranch = productInBranchRepository.findById(productInBranchId);

        if (optionalProductInBranch.get().getQuantity() < quantity) {
            return Optional.empty();
        }
        int newQuantity = optionalProductInBranch.get().getQuantity() - quantity;

        Product product = optionalProductInBranch.get().getProduct();

        if (newQuantity == 0) {
            productInBranchRepository.delete(optionalProductInBranch.get());
        }else {
            optionalProductInBranch.get().setQuantity(newQuantity);
        }

        Optional<Branch> optionalBranch = branchRepository.findById(branchId);

        ProductInBranch productInBranchToSave = ProductInBranch.builder().
                branch(optionalBranch.get()).
                product(product).
                date(LocalDateTime.now()).
                quantity(quantity).build();

        ProductInBranch savedProductInBranch = productInBranchRepository.save(productInBranchToSave);

        return Optional.of(productInBranchMapper.convertToDto(savedProductInBranch));

    }

    public boolean existById(Long productInBranchId) {
        return productInBranchRepository.existsById(productInBranchId);
    }

    public Optional<SalesDto> sellingProduct(String branchName, Long productInBranchId, Integer quantity) {

        Optional<ProductInBranch> prInBrFromData = productInBranchRepository.findById(productInBranchId);
        if (!prInBrFromData.get().getBranch().getName().equals(branchName) || prInBrFromData.get().getQuantity() < quantity) {
            return Optional.empty();
        }

        int newQuantity = prInBrFromData.get().getQuantity() - quantity;

        prInBrFromData.get().setQuantity(newQuantity);


        Sales salesToSave = new Sales();
        salesToSave.setBranch(prInBrFromData.get().getBranch());
        salesToSave.setProduct(prInBrFromData.get().getProduct());
        salesToSave.setQuantity(quantity);
        salesToSave.setSalesDate(LocalDateTime.now());
        salesToSave.setPrice(prInBrFromData.get().getProduct().getPriceToSale() * quantity);

        Sales savedSales = salesRepository.save(salesToSave);


        if (newQuantity == 0) {
            productInBranchRepository.delete(prInBrFromData.get());
        }

        return Optional.of(salesMapper.convertToDto(savedSales));
    }

    public Optional<List<ProductInBranchDto>> getProductsById(Long id) {

        Optional<List<ProductInBranch>> productsList = productInBranchRepository.findProductInBranchesByProductId(id);

        if (productsList.isEmpty()) {
            return Optional.empty();
        }

        List<ProductInBranchDto> productInBranchDtos = (List<ProductInBranchDto>) productInBranchMapper.
                                                                                    convertToDtoColl(productsList.get());

        return Optional.of(productInBranchDtos);
    }
}
