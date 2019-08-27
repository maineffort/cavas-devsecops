package com.anchore.jenkins.plugins.zap.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Result {
    @JsonProperty public String result;
    @JsonProperty public String message;
}
