package com.example.barkr.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

/**
 * Resource class for Barks (posts).
 */
public class Bark {

    private final String id;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty("creation_ts")
    private final LocalDateTime creationTs;
    private final String title;
    private final String content;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public Bark(
            @JsonProperty("id") String id,
            @JsonProperty("creation_ts") LocalDateTime creationTs,
            @JsonProperty("title") String title,
            @JsonProperty("content") String content) {
        this.id = id;
        this.creationTs = creationTs;
        this.title = title;
        this.content = content;
    }

    public String getId() {
        return this.id;
    }

    public LocalDateTime getCreationTs() {
        return this.creationTs;
    }

    public String getTitle() {
        return this.title;
    }

    public String getContent() {
        return this.content;
    }
}
