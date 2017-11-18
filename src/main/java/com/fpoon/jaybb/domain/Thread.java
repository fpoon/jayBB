package com.fpoon.jaybb.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Thread extends AuditingEntity {
    @Id
    @SequenceGenerator(name = "sequenceGenerator", sequenceName = "hibernate_sequence")
    @GeneratedValue(generator = "sequenceGenerator")
    private Long id;

    @OneToMany
    @OrderBy("createdDate ASC")
    private List<Message> messages;

    private boolean closed = false;
}