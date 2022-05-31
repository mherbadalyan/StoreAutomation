package com.example.store_automation.controller;

import com.example.store_automation.model.dto.LoginDto;
import com.example.store_automation.model.dto.SignUpDto;
import com.example.store_automation.model.entity.Branch;
import com.example.store_automation.model.entity.Role;
import com.example.store_automation.repository.BranchRepository;
import com.example.store_automation.repository.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;


@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private BranchRepository branchRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @PostMapping("/signin")
    public ResponseEntity<String> authenticateUser(@RequestBody LoginDto loginDto){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getName(), loginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new ResponseEntity<>("Branch signed-in successfully!.", HttpStatus.OK);
    }


    @PreAuthorize("hasRole('GENERAL')")
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignUpDto signUpDto){

        // add check for branch name exists in a DB
        if(branchRepository.existsByName(signUpDto.getName())){
            return new ResponseEntity<>("Branch name is already taken!", HttpStatus.BAD_REQUEST);
        }

        // add check for branch address exists in DB
        if(branchRepository.existsByAddress(signUpDto.getAddress())){
            return new ResponseEntity<>("Address is already taken!", HttpStatus.BAD_REQUEST);
        }

        // create branch object
        Branch branch = new Branch();
        branch.setName(signUpDto.getName());
        branch.setAddress(signUpDto.getAddress());
        branch.setPassword(passwordEncoder.encode(signUpDto.getPassword()));

        Role roles = null;
        if(signUpDto.getRole().equals("GENERAL")){
            roles = roleRepository.findByName("GENERAL").get();
        }
        if(signUpDto.getRole().equals("BRANCH")){
            roles = roleRepository.findByName("BRANCH").get();
        }


        branch.setRoles(Collections.singleton(roles));

        branchRepository.save(branch);

        return new ResponseEntity<>("Branch registered successfully", HttpStatus.OK);

    }
}
