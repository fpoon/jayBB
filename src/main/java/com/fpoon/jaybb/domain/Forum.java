package com.fpoon.jaybb.domain;

import com.fpoon.jaybb.constant.UserRoles;
import lombok.Data;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parentForumId")
    private Forum parentForum;

    @ManyToMany
    private Set<User> moderators = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "parentForumId")
    private List<Forum> forums = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "forumId")
    private List<Thread> threads = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "forumId")
    private List<RssSource> rssSources = new ArrayList<>();

    public boolean isModerator(String username) {

        if (moderators.stream().anyMatch(m -> m.getUsername().equals(username))) {
            return true;
        } else if (parentForum != null) {
            return parentForum.isModerator(username);
        } else return false;
    }

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

        if (user.getAuthorities().contains(new SimpleGrantedAuthority(UserRoles.MODERATOR))) {
            return isModerator(user.getUsername());
        }

        return false;
    }
}
