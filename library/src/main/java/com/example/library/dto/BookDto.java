package com.example.library.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class BookDto {
    private Long id;
    private String isbn;
    private String name;
    private String genre;
    private String description;
    private String author;

}
