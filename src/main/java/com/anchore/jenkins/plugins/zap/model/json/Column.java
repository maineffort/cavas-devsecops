package com.anchore.jenkins.plugins.zap.model.json;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Column {
    @JsonProperty
    String title;

    public Column(String title) {
        this.title = title;
    }
}
