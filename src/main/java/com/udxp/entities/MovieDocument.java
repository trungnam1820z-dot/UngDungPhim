package com.udxp.entities;

import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.List;
@Data
@Document(indexName = "movies")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MovieDocument {
    @Id
    String id;
    String title;
    String description;
    Integer releaseDate;
    List<String> categories;
}
