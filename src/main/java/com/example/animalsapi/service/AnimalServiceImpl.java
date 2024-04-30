package com.example.animalsapi.service;

import com.example.animalsapi.domain.Animal;
import com.example.animalsapi.exception.InvalidFileException;
import com.example.animalsapi.exception.InvalidSortParameterException;
import com.example.animalsapi.repository.AnimalRepository;
import com.example.animalsapi.utils.MappingTable;
import com.example.animalsapi.web.dto.AnimalStore;
import com.example.animalsapi.web.dto.AnimalUploadDto;
import com.example.animalsapi.web.mapper.AnimalMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.HeaderColumnNameTranslateMappingStrategy;
import jakarta.validation.Validator;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import static com.example.animalsapi.repository.AnimalSpecification.*;

@Service
@RequiredArgsConstructor
public class AnimalServiceImpl implements AnimalService {

  private final AnimalRepository animalRepository;
  private final Validator validator;
  private final AnimalMapper animalMapper;


  @Override
  public List<Animal> uploadFromFile(MultipartFile file) {
    String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());

    if (Objects.requireNonNull(fileExtension).equalsIgnoreCase("csv")) {
      return processCsvFile(file);
    } else if (Objects.requireNonNull(fileExtension).equalsIgnoreCase("xml")) {
      return processXmlFile(file);
    } else {
      throw new InvalidFileException("Unsupported file format");
    }
  }

  private List<Animal> processCsvFile(MultipartFile file) {
    var animals = parseCsvFile(file).stream()
        .filter(this::isAnimalDtoValid)
        .map(animalMapper::toEntity).toList();
    return saveAll(animals);
  }

  private List<Animal> processXmlFile(MultipartFile file) {
    var animals = parseXmlFile(file).stream()
        .filter(this::isAnimalDtoValid)
        .map(animalMapper::toEntity)
        .toList();
    return saveAll(animals);
  }

  @Override
  public List<Animal> search(String type, String category, String sex, List<String> sort, String sortType) {
    Specification<Animal> filters = Specification.where(StringUtils.isBlank(type) ? null : typeSpecification(type))
        .and(StringUtils.isBlank(category) ? null : categorySpecification(category))
        .and(StringUtils.isBlank(sex) ? null : sexSpecification(sex));

    Sort sortingSettings = createSortObject(sort, sortType);
    return animalRepository.findAll(filters, sortingSettings);
  }

  private Sort createSortObject(List<String> sort, String sortType) {
    if (sort == null || sort.isEmpty()) {
      return Sort.unsorted();
    }

    Sort.Order[] orders = sort.stream()
        .map(sortName -> parseSortParameter(sortName, sortType))
        .toArray(Sort.Order[]::new);
    return Sort.by(orders);
  }

  private Sort.Order parseSortParameter(String sortParam, String sortType) {
    if (!isSortParameterCorrect(sortParam)) {
      throw new InvalidSortParameterException("Field - %s doesnt exist".formatted(sortParam));
    }
    if (!(sortType.equals("asc") || sortType.equals("desc"))){
      throw new InvalidSortParameterException("Invalid sorting type. Should be {asc} or {desc}");
    }
    return new Sort.Order(Sort.Direction.fromString(sortType), sortParam);
  }

  private boolean isSortParameterCorrect(String sort) {
    return Arrays.stream(Animal.class.getDeclaredFields())
        .map(Field::getName)
        .anyMatch(fieldName -> fieldName.equalsIgnoreCase(sort));
  }

  private List<Animal> saveAll(List<Animal> animals) {
    return animalRepository.saveAll(animals);
  }

  private boolean isAnimalDtoValid(AnimalUploadDto animalUploadDto) {
    return validator.validate(animalUploadDto).isEmpty();
  }

  private List<AnimalUploadDto> parseCsvFile(MultipartFile file) {
    try (CSVReader reader = new CSVReader(new InputStreamReader(file.getInputStream()))) {
      HeaderColumnNameTranslateMappingStrategy<AnimalUploadDto> strategy = createMappingStrategy();
      CsvToBean<AnimalUploadDto> csvToBean = createCsvToBean(reader, strategy);
      return csvToBean.parse();
    } catch (IOException e) {
      throw new InvalidFileException("Error occurred while parsing csv file");
    }
  }

  private List<AnimalUploadDto> parseXmlFile(MultipartFile file) {
    XmlMapper xmlMapper = new XmlMapper();
    try {
      AnimalStore animals = xmlMapper.readValue(file.getInputStream(), AnimalStore.class);
      return animals.getAnimals();
    } catch (IOException e) {
      System.out.println(e.getMessage());
      throw new InvalidFileException("Error occurred while parsing xml file");
    }
  }

  private HeaderColumnNameTranslateMappingStrategy<AnimalUploadDto> createMappingStrategy() {
    HeaderColumnNameTranslateMappingStrategy<AnimalUploadDto> strategy =
        new HeaderColumnNameTranslateMappingStrategy<>();
    strategy.setType(AnimalUploadDto.class);
    strategy.setColumnMapping(MappingTable.GET_MAPPING_TABLE());
    return strategy;
  }

  private CsvToBean<AnimalUploadDto> createCsvToBean(CSVReader reader,
                                                     HeaderColumnNameTranslateMappingStrategy<AnimalUploadDto> strategy) {
    CsvToBean<AnimalUploadDto> csvToBean = new CsvToBean<>();
    csvToBean.setCsvReader(reader);
    csvToBean.setMappingStrategy(strategy);
    return csvToBean;
  }
}