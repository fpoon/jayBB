package com.fpoon.jaybb.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Message extends AuditingEntity {
    @Id
    @SequenceGenerator(name = "sequenceGenerator", sequenceName = "hibernate_sequence")
    @GeneratedValue(generator = "sequenceGenerator")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "threadId")
    private Thread thread;

    @ManyToOne
    private User user;

    @Length(min = 3)
    @Lob
    private String title;

    @Length(min = 3)
    @Lob
    private String content;

    private boolean removed = false;

    public Message(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
