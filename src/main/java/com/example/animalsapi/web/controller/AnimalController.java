package com.example.animalsapi.web.controller;

import com.example.animalsapi.service.AnimalService;
import com.example.animalsapi.web.dto.AnimalDto;
import com.example.animalsapi.web.dto.ExceptionResponse;
import com.example.animalsapi.web.mapper.AnimalMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(value = "/api/v1/animals", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Tag(name = "Animal Controller")
public class AnimalController {

  private final AnimalService animalService;
  private final AnimalMapper animalMapper;

  @Operation(summary = "Upload animals from a file",
      description = "Endpoint to upload animals from a CSV or XML file",
      parameters = {
          @Parameter(name = "file",description = "The file to upload",
              required = true,
              content = @Content(mediaType = "multipart/form-data"))
      },
      responses = {
          @ApiResponse(responseCode = "200", description = "List of uploaded animals",
              content = @Content(mediaType = "application/json",
                  schema = @Schema(type = "array", implementation = AnimalDto.class))),
          @ApiResponse(responseCode = "400", description = "Invalid file format",
              content = @Content(mediaType = "application/json",
                  schema = @Schema(implementation = ExceptionResponse.class)))
      })
  @PostMapping("/files/upload")
  public ResponseEntity<List<AnimalDto>> uploadAnimals(@RequestParam(name = "file") MultipartFile file) {
    return ResponseEntity.ok(animalService.uploadFromFile(file).stream()
        .map(animalMapper::toDto).toList());
  }


  @Operation(summary = "Search animals with filters",
      description = "Endpoint to search animals based on type, category, sex, and sorting",
      parameters = {
          @Parameter(name = "type", in = ParameterIn.QUERY, description = "Type of the animal",
              schema = @Schema(type = "string")),
          @Parameter(name = "category", in = ParameterIn.QUERY, description = "Category of the animal",
              schema = @Schema(type = "string")),
          @Parameter(name = "sex", in = ParameterIn.QUERY, description = "Sex of the animal",
              schema = @Schema(type = "string")),
          @Parameter(name = "sort", in = ParameterIn.QUERY, description = "Sorting criteria for the results",
              schema = @Schema(type = "string", implementation = String.class)),
          @Parameter(name = "sortType", in = ParameterIn.QUERY,
              description = "Sorting order (asc/desc), default is asc",
              schema = @Schema(type = "string"))
      },
      responses = {
          @ApiResponse(responseCode = "200", description = "List of animals matching the filters",
              content = @Content(mediaType = "application/json",
                  schema = @Schema(type = "array", implementation = AnimalDto.class))),
          @ApiResponse(responseCode = "400", description = "Invalid sort or filter parameters",
              content = @Content(mediaType = "application/json",
                  schema = @Schema(implementation = ExceptionResponse.class)))
      })
  @GetMapping("/search")
  public ResponseEntity<List<AnimalDto>> searchAnimals(@RequestParam(required = false) String type,
                                                       @RequestParam(required = false) String category,
                                                       @RequestParam(required = false) String sex,
                                                       @RequestParam(required = false) List<String> sort,
                                                       @RequestParam(required = false, defaultValue = "asc") String sortType) {
    return ResponseEntity.ok(animalService.search(type, category, sex, sort, sortType)
        .stream().map(animalMapper::toDto).toList());
  }
}