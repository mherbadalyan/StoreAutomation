package com.example.storeautomation.model.mapper;

import com.example.storeautomation.model.dto.BranchDto;
import com.example.storeautomation.model.entity.Branch;

public class BranchMapper implements BaseMapper<Branch, BranchDto>{
    @Override
    public Branch convertToEntity(BranchDto dto) {
        Branch branch=new Branch();
        branch.setId(dto.getId());
        branch.setName(dto.getName());
        branch.setAddress(dto.getAddress());
;       return branch;
    }

    @Override
    public BranchDto convertToDto(Branch entity) {
        return BranchDto.builder().id(entity.getId()).
                name(entity.getName()).
                address(entity.getAddress()).build();

    }
}
