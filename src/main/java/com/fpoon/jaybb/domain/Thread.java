package com.fpoon.jaybb.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Thread extends AuditingEntity {
    @Id
    @SequenceGenerator(name = "sequenceGenerator", sequenceName = "hibernate_sequence")
    @GeneratedValue(generator = "sequenceGenerator")
    private Long id;

    private Long forumId;

    private String title;

    private Integer messagesSize;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("createdDate ASC")
    @JoinColumn(name = "threadId")
    private List<Message> messages = new ArrayList<>();


    private boolean closed = false;
    private boolean deleted = false;

    @PrePersist
    @PreUpdate
    protected void countMessages() {
        messagesSize = messages.size();
    }
}
