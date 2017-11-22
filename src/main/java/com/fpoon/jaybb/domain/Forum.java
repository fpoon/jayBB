package com.fpoon.jaybb.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Forum extends AuditingEntity {
    @Id
    @SequenceGenerator(name = "sequenceGenerator", sequenceName = "hibernate_sequence")
    @GeneratedValue(generator = "sequenceGenerator")
    private Long id;

    private String title;

    private String description;

    private boolean root = false;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Forum> forums = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "forumId")
    private List<Thread> threads = new ArrayList<>();
}
