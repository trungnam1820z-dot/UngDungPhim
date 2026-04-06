package com.udxp.user.entities;

import com.udxp.authentication.Role;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long Id;
    String userName;
    String password;
    String fullName;
    String email;
    String gender;
    @Enumerated(EnumType.STRING)
    Role role;
}
