package com.example.animalsapi.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum Sex {
  MALE("male"), FEMALE("female");
  private final String name;

  Sex(String name) {
    this.name = name;
  }
  @JsonValue
  public String getName() {
    return name;
  }

  @JsonCreator
  public static Sex fromString(String name) {
    for (Sex sex : Sex.values()) {
      if (sex.name.equalsIgnoreCase(name)) {
        return sex;
      }
    }
    throw new IllegalArgumentException("Invalid Sex name: " + name);
  }
}
