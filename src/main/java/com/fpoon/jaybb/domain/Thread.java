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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "forumId")
    private Forum forum;

    private String title;

    private Integer messagesSize;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "threadId")
    @OrderBy("createdDate ASC")
    private List<Message> messages = new ArrayList<>();

    private boolean closed = false;
    private boolean deleted = false;
    private boolean sticky = false;

    private Integer views = 0;

    public Message getLastMessage() {
        if (messages.isEmpty())
            return null;
        return messages.get(messages.size() - 1);
    }

    public Thread(String title, String content) {
        this.title = title;
        this.messages.add(new Message(title, content));
    }
}
