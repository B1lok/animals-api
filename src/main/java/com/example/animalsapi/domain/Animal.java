package com.example.animalsapi.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "animal")
public class Animal {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;

  @Column(name = "name")
  private String name;

  @Column(name = "type")
  private String type;

  @Enumerated(EnumType.STRING)
  @Column(name = "sex")
  private Sex sex;

  @Column(name = "weight")
  private Integer weight;

  @Column(name = "cost")
  private Double cost;

  @Enumerated(EnumType.STRING)
  @Column(name = "category")
  private Category category;
}