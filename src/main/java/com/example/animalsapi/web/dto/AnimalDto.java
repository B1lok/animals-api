package com.example.animalsapi.web.dto;

import com.example.animalsapi.domain.Category;
import com.example.animalsapi.domain.Sex;
import java.io.Serializable;
import lombok.Value;

/**
 * DTO for {@link com.example.animalsapi.domain.Animal}
 */
@Value
public class AnimalDto implements Serializable {
  Long id;
  String name;
  String type;
  Sex sex;
  Integer weight;
  Double cost;
  Category category;
}