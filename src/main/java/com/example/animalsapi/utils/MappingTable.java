package com.example.animalsapi.utils;

import java.util.HashMap;
import java.util.Map;
import lombok.experimental.UtilityClass;

@UtilityClass
public class MappingTable {
  public static Map<String, String> GET_MAPPING_TABLE(){
    Map<String, String> mappingTable = new HashMap<>();
    mappingTable.put("Name", "name");
    mappingTable.put("Type", "type");
    mappingTable.put("Sex", "sex");
    mappingTable.put("Weight", "weight");
    mappingTable.put("Cost", "cost");
    return mappingTable;
  }
}
