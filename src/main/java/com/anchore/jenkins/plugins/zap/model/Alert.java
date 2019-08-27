package com.anchore.jenkins.plugins.zap.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Alert {
    @JsonProperty public String id;
    @JsonProperty public String name;
    @JsonProperty public String risk;
    @JsonProperty public String reliability;
    @JsonProperty public String confidence;
    @JsonProperty public String url;
    @JsonProperty public String other;
    @JsonProperty public String param;
    @JsonProperty public String attack;
    @JsonProperty public String evidence;
    @JsonProperty public String description;
    @JsonProperty public String reference;
    @JsonProperty public String solution;
    @JsonProperty public int cweId;
    @JsonProperty public int wascId;
    @JsonProperty public String messageId;
    @JsonProperty public String pluginId;
    @JsonProperty public String alert;
}
