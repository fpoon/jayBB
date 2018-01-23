package com.fpoon.jaybb.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Table(name = "\"user\"")
@EqualsAndHashCode(of = {"id", "username", "email"})
@ToString(of = {"id", "username", "email", "roles"})
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

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "\"USER_ROLES\"")
    private Set<String> roles = new HashSet<>();
}
