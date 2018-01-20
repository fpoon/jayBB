package com.fpoon.jaybb.domain;

import com.fpoon.jaybb.constant.UserRoles;
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

    @ManyToMany
    private List<User> moderators = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Forum> forums = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "forumId")
    private List<Thread> threads = new ArrayList<>();

    public boolean isModerator(User user) {
        if (user == null)
            return false;

        if (user.getRoles().contains(UserRoles.ADMIN))
            return true;

        if (user.getRoles().contains(UserRoles.MODERATOR) && moderators.contains(user))
            return true;

        return false;
    }
}
