package com.rest.api.enumerator;

import lombok.Getter;

@Getter
public enum QueueType {
    SOLO_RANK_QUEUE(420);

    private final int queue;
    QueueType(int queue) {
        this.queue = queue;
    }
}
