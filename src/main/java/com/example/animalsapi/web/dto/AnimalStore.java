package com.example.animalsapi.web.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import java.util.List;
import lombok.Data;

@Data
public class AnimalStore {
  @JacksonXmlElementWrapper(useWrapping = false)
  @JacksonXmlProperty(localName = "animal")
  private List<AnimalUploadDto> animals;
}
