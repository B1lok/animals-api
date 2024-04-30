package com.example.animalsapi.service;

import com.example.animalsapi.domain.Animal;
import com.example.animalsapi.web.dto.AnimalUploadDto;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface AnimalService {

  List<Animal> uploadFromFile(MultipartFile file);


  List<Animal> search(String type, String category, String sex, List<String> sort, String sortType);


}
