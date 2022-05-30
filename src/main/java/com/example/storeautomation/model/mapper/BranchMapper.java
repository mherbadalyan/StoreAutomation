package com.example.storeautomation.model.mapper;

import com.example.storeautomation.model.dto.BranchDto;
import com.example.storeautomation.model.entity.Branch;

public class BranchMapper implements BaseMapper<Branch, BranchDto>{
    @Override
    public Branch convertToEntity(BranchDto dto) {
        return null;
    }

    @Override
    public BranchDto convertToDto(Branch entity) {
        return null;
    }
}
