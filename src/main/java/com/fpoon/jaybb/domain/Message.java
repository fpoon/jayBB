package com.fpoon.jaybb.domain;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;

@Entity
@Data
public class Message extends AuditingEntity {
    @Id
    @SequenceGenerator(name = "sequenceGenerator", sequenceName = "hibernate_sequence")
    @GeneratedValue(generator = "sequenceGenerator")
    private Long id;

    @Length(min = 3, max = 255)
    @Column(length = 255)
    private String title;

    @Length(min = 3, max = 8192)
    @Column(length = 8192)
    private String content;
}
