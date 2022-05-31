package com.example.store_automation.security;

import com.example.store_automation.model.entity.Branch;
import com.example.store_automation.model.entity.Role;
import com.example.store_automation.repository.BranchRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private BranchRepository branchRepository;

    public CustomUserDetailsService(BranchRepository branchRepository) {
        this.branchRepository = branchRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String nameOrAddress) throws UsernameNotFoundException {
        Branch branch = branchRepository.findByNameOrAddress(nameOrAddress, nameOrAddress)
                .orElseThrow(() ->
                        new UsernameNotFoundException("Branch not found with name or address:" + nameOrAddress));
        return new org.springframework.security.core.userdetails.User(branch.getName(),
                branch.getPassword(), mapRolesToAuthorities(branch.getRoles()));
    }

    private Collection< ? extends GrantedAuthority> mapRolesToAuthorities(Set<Role> roles){
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }
}
