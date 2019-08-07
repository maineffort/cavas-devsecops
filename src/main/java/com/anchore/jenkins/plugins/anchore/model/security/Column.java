package com.anchore.jenkins.plugins.anchore.model.security;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Column {
    @JsonProperty
    String title;

    public Column(String title) {
        this.title = title;
    }
}
