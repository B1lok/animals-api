package com.example.animalsapi.web.dto;

import com.example.animalsapi.domain.Sex;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import lombok.Data;

@Data
public class AnimalUploadDto implements Serializable {
  @JacksonXmlProperty
  @NotEmpty
  @Size(min = 1, max = 100)
  private String name;

  @JacksonXmlProperty
  @NotNull
  @Size(min = 1, max = 100)
  private String type;

  @JacksonXmlProperty
  @NotNull
  private Sex sex;

  @NotNull
  @Positive
  @JacksonXmlProperty
  private Integer weight;

  @JacksonXmlProperty
  @NotNull
  @Positive
  private Double cost;
}