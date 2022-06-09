package com.example.store_automation.repository;

import com.example.store_automation.model.entity.Branch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BranchRepository extends JpaRepository<Branch,Long> {

    Optional<Branch> findByName(String name);
    Optional<Branch> findByNameOrAddress(String name, String address);
    Boolean existsByAddress(String address);
    Boolean existsByName(String name);
    void deleteByName(String name);

    @Query("SELECT br FROM Branch br join br.roles r where r.name = 'BRANCH'")
    List<Branch> getBranches();
}
