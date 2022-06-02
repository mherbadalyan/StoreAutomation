package com.example.store_automation.model.mapper;

import com.example.store_automation.model.dto.BranchDto;
import com.example.store_automation.model.entity.Branch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BranchMapper implements BaseMapper<Branch, BranchDto>{
    @Override
    public Branch convertToEntity(BranchDto dto) {
        Branch branch=new Branch();
        branch.setId(dto.getId());
        branch.setName(dto.getName());
        branch.setAddress(dto.getAddress());
        branch.setPassword(dto.getPassword());
       return branch;
    }

    @Override
    public BranchDto convertToDto(Branch entity) {
        return BranchDto.builder().id(entity.getId()).
                name(entity.getName()).
                address(entity.getAddress()).build();

    }
}
