package com.fpoon.jaybb.domain;

import com.fpoon.jaybb.listener.ThreadListener;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@EntityListeners({AuditingEntityListener.class, ThreadListener.class})
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

    public Thread(String title, String content) {
        this.title = title;
        this.messages.add(new Message(title, content));
    }
}
