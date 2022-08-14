package com.connector.iirsSearch.model;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="entitypos")
public class Entitypos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="simulation_time")
    private Long simulationTime;
    @Column
    private String entity;
    @Column
    private int y_digit_1;
    @Column
    private int y_digit_0;
    @Column
    private int x_digit_1;
    @Column
    private int x_digit_0;
}