package com.example.animalsapi.domain;

public enum Category {
  FIRST, SECOND, THIRD, FOURTH;

  public static Category getCategory(double cost){
    if (cost <= 20) return Category.FIRST;
    else if (cost > 20 && cost <= 40) return Category.SECOND;
    else if (cost > 40 && cost <= 60) return Category.THIRD;
    else return Category.FOURTH;
  }
}
