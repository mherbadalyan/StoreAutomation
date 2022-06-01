package com.example.store_automation.service;

import com.example.store_automation.model.dto.BranchDto;
import com.example.store_automation.model.mapper.BranchMapper;
import com.example.store_automation.repository.BranchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BranchService {

    private final BranchRepository branchRepository;
    private final BranchMapper branchMapper;
    public Optional<BranchDto> getBranchById(Long id) {
        return Optional.of(branchMapper.convertToDto(branchRepository.findById(id).get()));
    }
    public boolean existById(Long id) {
        return branchRepository.existsById(id);
    }
}
