package com.example.store_automation.model.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;

import java.util.*;

@Entity
@Table(name = "branch", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"name"}),
        @UniqueConstraint(columnNames = {"address"})
})
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Branch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private String address;

    @Column
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "branch_roles",
            joinColumns = @JoinColumn(name = "branch_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Set<Role> roles = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Branch branch = (Branch) o;
        return id != null && Objects.equals(id, branch.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
