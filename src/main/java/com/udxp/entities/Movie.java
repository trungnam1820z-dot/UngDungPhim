package com.udxp.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "movie")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    String title;
    String description;
    Integer releaseDate;
    int duration;
    //Many movie - 1 country
    @ManyToOne
    @JoinColumn(name = "country_id")
    Country country;
    // Many-to-Many với Director
    @ManyToMany
    @JoinTable(
            name = "movie_director",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "director_id")
    )
    List<Director> directors;
    // Many-to-Many với Category
    @ManyToMany
    @JoinTable(
            name = "movie_category",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    List<Category> categories;
}
