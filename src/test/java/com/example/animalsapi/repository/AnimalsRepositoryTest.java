package com.example.animalsapi.repository;

import com.example.animalsapi.domain.Animal;
import com.example.animalsapi.domain.Category;
import com.example.animalsapi.domain.Sex;
import com.example.animalsapi.util.DataUtils;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
public class AnimalsRepositoryTest {
  @Autowired
  private AnimalRepository animalRepository;

  @BeforeEach
  public void deleteAll(){
    animalRepository.deleteAll();
  }

  @Test
  @DisplayName("Test save list of animals functionality ")
  public void givenAnimalsList_whenSaveAll_AnimalsAreCreated(){
    // given
    List<Animal> animals = DataUtils.getAnimalList();
    // when
    List<Animal> createdAnimals = animalRepository.saveAll(animals);
    // then
    assertThat(createdAnimals).isNotEmpty();
    assertThat(createdAnimals).size().isEqualTo(3);
    assertThat(createdAnimals.get(0).getId()).isNotNull();
  }

  @Test
  @DisplayName("Test find animals by filter parameters")
  public void givenFilterParameters_whenFindAll_AnimalsAreFiltered(){
    // given
    animalRepository.saveAll(DataUtils.getAnimalList());
    Specification<Animal> specifications = DataUtils.getSpecifications();
    Sort sort = DataUtils.getSortSettings();
    // when
    List<Animal> findAllBySpecifications = animalRepository.findAll(specifications, sort);
    // then
    assertThat(findAllBySpecifications.size()).isEqualTo(1);
    assertThat(findAllBySpecifications.get(0).getSex()).isEqualTo(Sex.MALE);
    assertThat(findAllBySpecifications.get(0).getCategory()).isEqualTo(Category.FIRST);
    assertThat(findAllBySpecifications.get(0).getType()).isEqualTo("cat");
    assertThat(findAllBySpecifications.get(0).getName()).isEqualTo("Sadie");
  }
}