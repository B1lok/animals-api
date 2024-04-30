package com.example.animalsapi.repository;

import com.example.animalsapi.domain.Animal;
import org.springframework.data.jpa.domain.Specification;

public class AnimalSpecification {

  public static Specification<Animal> typeSpecification(String type){
    return ((root, query, builder) -> builder.equal(root.get("type"), type));
  }
  public static Specification<Animal> categorySpecification(String category){
    return ((root, query, builder) -> builder.equal(root.get("category"), category.toUpperCase()));
  }
  public static Specification<Animal> sexSpecification(String sex){
    return ((root, query, builder) -> builder.equal(root.get("sex"), sex.toUpperCase()));
  }
}
