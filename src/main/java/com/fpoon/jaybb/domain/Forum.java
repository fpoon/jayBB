package com.fpoon.jaybb.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Forum extends AuditingEntity {
    @Id
    @SequenceGenerator(name = "sequenceGenerator", sequenceName = "hibernate_sequence")
    @GeneratedValue(generator = "sequenceGenerator")
    private Long id;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Forum> forums;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Thread> threads;
}
