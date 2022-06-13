package com.example.store_automation.model.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Sales {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Integer quantity;

    @Column
    private LocalDateTime salesDate;

    @Column
    private Double price;

    @Column
    private Double priceIn;

    @Column
    private LocalDate expDate;

    @Column
    private LocalDate dateIn;

    @ManyToOne
    @JoinColumn(name = "branch_id")
    private Branch branch;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Sales sales = (Sales) o;
        return id != null && Objects.equals(id, sales.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
