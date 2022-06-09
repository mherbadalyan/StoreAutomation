package com.example.store_automation.service;

import com.example.store_automation.model.dto.BranchDto;
import com.example.store_automation.model.dto.CategoryDto;
import com.example.store_automation.model.entity.Branch;
import com.example.store_automation.model.entity.Category;
import com.example.store_automation.model.mapper.BranchMapper;
import com.example.store_automation.repository.BranchRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class BranchService {

    private PasswordEncoder passwordEncoder;
    private final BranchRepository branchRepository;
    private final BranchMapper branchMapper;

    public boolean existById(Long id) {
        return branchRepository.existsById(id);
    }

    public Optional<BranchDto> getBranch(String name) {
        Optional<Branch> branch = branchRepository.findByName(name);

        if (branch.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(branchMapper.convertToDto(branch.get()));
    }

    public Optional<BranchDto> updateBranch(BranchDto branchDto,String branchName){

            if (!branchRepository.existsByName(branchName)) {
                return Optional.empty();
            }

            Optional<Branch> branchFromData = branchRepository.findByName(branchName);
            if (branchFromData.isEmpty()) {
                return Optional.empty();
            }
            Branch branchToSave = branchMapper.convertToEntity(branchDto);
            branchFromData.get().setName(branchToSave.getName());
            branchFromData.get().setAddress(branchToSave.getAddress());
            branchFromData.get().setPassword(passwordEncoder.encode(branchToSave.getPassword()));

            Branch savedBranch = branchRepository.save(branchFromData.get());
            return Optional.of(branchMapper.convertToDto(savedBranch));
    }

    public void deleteBranch(String name){
        branchRepository.deleteByName(name);
    }
}
