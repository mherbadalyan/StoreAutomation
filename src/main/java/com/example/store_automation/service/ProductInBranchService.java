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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    public Optional<ProductInBranchDto> createProductInBranch(Long productId,
                                                              Integer quantity,
                                                              Double price,
                                                              Integer expMonth) {

        Optional<Product> optionalProduct = productRepository.findById(productId);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        Optional<Branch> optionalBranch = branchRepository.findByName(auth.getName());

        if (optionalBranch.isEmpty() || optionalProduct.isEmpty()) {
            return Optional.empty();
        }

        optionalProduct.get().setPrice(price);

        ProductInBranch productInBranchToSave = ProductInBranch.builder().
                branch(optionalBranch.get()).
                product(optionalProduct.get()).
                date(LocalDate.now()).
                priceIn(price).
                expDate(dateToMonthYear(expMonth)).
                quantity(quantity).build();

        Optional<ProductInBranch> uniqueProduct = productInBranchRepository.findUniqueProduct(
                productInBranchToSave.getBranch().getId(),
                productInBranchToSave.getProduct().getId(),
                LocalDate.now(),
                dateToMonthYear(expMonth),
                price);

        ProductInBranch savedProductInBranch;
        if (uniqueProduct.isPresent()) {
            int newQuantity = uniqueProduct.get().getQuantity() + quantity;
            uniqueProduct.get().setQuantity(newQuantity);
            savedProductInBranch = productInBranchRepository.save(uniqueProduct.get());
        }else {
         savedProductInBranch = productInBranchRepository.save(productInBranchToSave);
        }
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
            optionalProductInBranch.get().setZeroDate(LocalDate.now());
        }

        optionalProductInBranch.get().setQuantity(newQuantity);


        Optional<Branch> optionalBranch = branchRepository.findById(branchId);

        ProductInBranch productInBranchToSave = ProductInBranch.builder().
                branch(optionalBranch.get()).
                product(product).
                date(optionalProductInBranch.get().getDate()).
                expDate(optionalProductInBranch.get().getExpDate()).
                priceIn(optionalProductInBranch.get().getPriceIn()).
                quantity(quantity).build();

        ProductInBranch savedProductInBranch;
        Optional<ProductInBranch> uniqueProduct = productInBranchRepository.findUniqueProduct(branchId,
                product.getId(),
                productInBranchToSave.getDate(),
                productInBranchToSave.getExpDate(),
                productInBranchToSave.getPriceIn());

        if (uniqueProduct.isPresent()) {
            newQuantity = uniqueProduct.get().getQuantity() + quantity;
            uniqueProduct.get().setQuantity(newQuantity);
            savedProductInBranch = productInBranchRepository.save(uniqueProduct.get());
        }else {
            savedProductInBranch = productInBranchRepository.save(productInBranchToSave);
        }

        return Optional.of(productInBranchMapper.convertToDto(savedProductInBranch));
    }

    public Optional<SalesDto> sellingProduct(String branchName, Long productInBranchId, Integer quantity,Integer salePercent) {

        Optional<ProductInBranch> prInBrFromData = productInBranchRepository.findById(productInBranchId);
        if (!prInBrFromData.get().getBranch().getName().equals(branchName) || prInBrFromData.get().getQuantity() < quantity) {
            return Optional.empty();
        }

        int newQuantity = prInBrFromData.get().getQuantity() - quantity;

        if (newQuantity == 0) {
            prInBrFromData.get().setZeroDate(LocalDate.now());
        }
        prInBrFromData.get().setQuantity(newQuantity);

        double priceToSale = prInBrFromData.get().getProduct().getPriceToSale() * quantity;
        Double priceWithSalePercent = priceToSale - (priceToSale * salePercent /100);

        Sales salesToSave = new Sales();
        salesToSave.setBranch(prInBrFromData.get().getBranch());
        salesToSave.setProduct(prInBrFromData.get().getProduct());
        salesToSave.setQuantity(quantity);
        salesToSave.setSalesDate(LocalDateTime.now());
        salesToSave.setPrice(priceWithSalePercent);
        salesToSave.setPriceIn(prInBrFromData.get().getPriceIn());
        salesToSave.setExpDate(prInBrFromData.get().getExpDate());
        salesToSave.setDateIn(prInBrFromData.get().getDate());

        Sales savedSales = salesRepository.save(salesToSave);

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

    public boolean existById(Long productInBranchId) {
        return productInBranchRepository.existsById(productInBranchId);
    }

    private LocalDate dateToMonthYear(Integer expMonth) {
        LocalDate dateNow = LocalDate.now();
        return LocalDate.of(dateNow.getYear(),dateNow.getMonth(),1).plusMonths(expMonth);
    }
}
