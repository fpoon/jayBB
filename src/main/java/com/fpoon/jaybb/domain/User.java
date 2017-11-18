package com.fpoon.jaybb.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "\"user\"")
public class User {
    @Id
    @SequenceGenerator(name = "sequenceGenerator", sequenceName = "hibernate_sequence")
    @GeneratedValue(generator = "sequenceGenerator")
    private Long id;

    @NotBlank
    @Length(min = 3, max = 50)
    @Column(unique = true)
    private String username;

    @JsonIgnore
    private String password;

    @NotBlank
    @Email
    @Length(min = 3, max = 100)
    @Column(unique = true)
    private String email;

    @ElementCollection
    private List<String> roles = new ArrayList<>();
}
