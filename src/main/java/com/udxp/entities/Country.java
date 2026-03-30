package com.udxp.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Entity
@Table(name = "country")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    String countryCode;
    String countryName;
    @OneToMany(mappedBy = "country")
    List<Movie> movies;

    @OneToMany(mappedBy = "country")
    List<Director> directors;
}
