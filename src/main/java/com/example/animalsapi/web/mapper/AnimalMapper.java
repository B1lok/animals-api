package com.example.animalsapi.web.mapper;

import com.example.animalsapi.domain.Animal;
import com.example.animalsapi.domain.Category;
import com.example.animalsapi.web.dto.AnimalDto;
import com.example.animalsapi.web.dto.AnimalUploadDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AnimalMapper {

  @Mapping(target = "category", source = "animalUploadDto", qualifiedByName = "mapAnimalCategory")
  Animal toEntity(AnimalUploadDto animalUploadDto);

  AnimalDto toDto(Animal animal);

  @Named("mapAnimalCategory")
  default Category mapAnimalCategory(AnimalUploadDto animalUploadDto){
    double cost = animalUploadDto.getCost();
    return Category.getCategory(cost);
  }
}
