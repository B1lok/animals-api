package com.example.animalsapi.util;

import com.example.animalsapi.domain.Animal;
import com.example.animalsapi.domain.Category;
import com.example.animalsapi.domain.Sex;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import static com.example.animalsapi.repository.AnimalSpecification.*;

public class DataUtils {
  public static List<Animal> getAnimalList() {
    List<Animal> animals = new ArrayList<>();

    Animal animal1 = new Animal();
    animal1.setId(1L);
    animal1.setName("Duke");
    animal1.setType("cat");
    animal1.setSex(Sex.MALE);
    animal1.setWeight(200);
    animal1.setCost(100.0);
    animal1.setCategory(Category.getCategory(100));

    Animal animal2 = new Animal();
    animal2.setId(2L);
    animal2.setName("Rocky");
    animal2.setType("dog");
    animal2.setSex(Sex.FEMALE);
    animal2.setWeight(10);
    animal2.setCost(50.0);
    animal2.setCategory(Category.getCategory(50));

    Animal animal3 = new Animal();
    animal3.setId(3L);
    animal3.setName("Sadie");
    animal3.setType("cat");
    animal3.setSex(Sex.MALE);
    animal3.setWeight(5);
    animal3.setCost(20.0);
    animal3.setCategory(Category.getCategory(20));

    animals.add(animal1);
    animals.add(animal2);
    animals.add(animal3);

    return animals;
  }

  public static Specification<Animal> getSpecifications(){
    return Specification.where(typeSpecification("cat"))
        .and(sexSpecification("MALE"))
        .and(categorySpecification("FIRST"));

  }

  public static Sort getSortSettings(){
    return Sort.by(Sort.Direction.ASC, "name");
  }
  public static MockMultipartFile getMockMultipartFile() throws IOException {
    String filePath = "src/test/resources/data/animals.csv";
    Path path = Paths.get(filePath);
    String name = path.getFileName().toString();
    String contentType = "application/csv";
    byte[] content = Files.readAllBytes(path);
    return new MockMultipartFile(name, name, contentType, content);
  }
}
