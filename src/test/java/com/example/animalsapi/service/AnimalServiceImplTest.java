package com.example.animalsapi.service;

import com.example.animalsapi.domain.Animal;
import com.example.animalsapi.repository.AnimalRepository;
import com.example.animalsapi.util.DataUtils;
import com.example.animalsapi.web.mapper.AnimalMapper;
import jakarta.validation.Validator;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.crypto.Data;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.multipart.MultipartFile;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;

@ExtendWith(MockitoExtension.class)
public class AnimalServiceImplTest {

  @Mock
  AnimalRepository animalRepository;

  @Mock
  Validator validator;

  @Mock
  AnimalMapper animalMapper;

  @InjectMocks
  private AnimalServiceImpl animalService;

  @Test
  @DisplayName("Test upload file and save animals")
  public void givenFileToUpload_whenUploadFile_AnimalsAreSaved() throws IOException {
    // given
    MultipartFile file = DataUtils.getMockMultipartFile();
    BDDMockito.given(animalRepository.saveAll(anyList()))
        .willReturn(DataUtils.getAnimalList());
    // when
    List<Animal> uploadedAnimals = animalService.uploadFromFile(file);
    // then
    assertThat(uploadedAnimals).isNotNull();
    assertThat(uploadedAnimals.size()).isEqualTo(3);
  }

  @Test
  @DisplayName("Test search animals and get one by provided filter parameters")
  public void givenSearchParameters_whenFindAll_GetFilteredAnimal(){
    // given
    String type = "cat";
    String category = "FIRST";
    String sex = "MALE";
    List<String> sort = new ArrayList<>();
    sort.add("name");
    String sortType = "asc";
    BDDMockito.given(animalRepository.findAll(any(Specification.class), any(Sort.class)))
        .willReturn(DataUtils.getAnimalList().subList(2, 3));
    // when
    List<Animal> searchedAnimals = animalService.search(type, category, sex, sort, sortType);
    // then
    assertThat(searchedAnimals).isNotNull();
    assertThat(searchedAnimals.size()).isEqualTo(1);
    assertThat(searchedAnimals.get(0).getName()).isEqualTo("Sadie");
  }
}
