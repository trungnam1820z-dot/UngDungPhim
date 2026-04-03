package com.udxp.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Entity
@Table(name = "director")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Director {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long Id;
    String directorName;
    // Many director - 1 country
    @ManyToOne
    @JoinColumn(name = "country_id")
    Country country;

    // mappedBy để tránh tạo 2 bảng trung gian
    @ManyToMany(mappedBy = "directors")
    List<Movie> movies;
}
