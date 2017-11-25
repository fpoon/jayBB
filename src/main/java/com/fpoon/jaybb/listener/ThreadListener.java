package com.fpoon.jaybb.listener;

import com.fpoon.jaybb.domain.Thread;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

public class ThreadListener {
    @PrePersist
    public void prePersist(Thread th) {
        th.setMessagesSize(th.getMessages().size());
    }

    @PreUpdate
    public void preUpdate(Thread th) {
        th.setMessagesSize(th.getMessages().size());
    }
}
