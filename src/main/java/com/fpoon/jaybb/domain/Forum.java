package com.fpoon.jaybb.domain;

import com.fpoon.jaybb.constant.UserRoles;
import lombok.Data;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

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

    private boolean editable = true;

    @ManyToMany
    private List<User> moderators = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "parentForumId")
    private List<Forum> forums = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "forumId")
    private List<Thread> threads = new ArrayList<>();

    public boolean isModerator() {
        org.springframework.security.core.userdetails.User user;
        try {
            user = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (ClassCastException ex) {
            return false;
        }

        if (user == null)
            return false;

        if (user.getAuthorities().contains(new SimpleGrantedAuthority(UserRoles.ADMIN)))
            return true;

        if (user.getAuthorities().contains(new SimpleGrantedAuthority(UserRoles.MODERATOR))
                && moderators.stream().anyMatch(m -> m.getUsername().equals(user.getUsername())))
            return true;

        return false;
    }
}
