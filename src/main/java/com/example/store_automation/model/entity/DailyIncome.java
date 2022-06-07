package com.example.store_automation.model.entity;

import lombok.Data;
import org.springframework.security.core.Transient;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table
@Data
public class DailyIncome {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private LocalDate date;

    @Column
    private Double dailyIncome;

    @ManyToOne
    @JoinColumn(name = "branch_id")
    private Branch branch;
}
