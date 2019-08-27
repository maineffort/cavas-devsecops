package com.anchore.jenkins.plugins.zap.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Status {
    @JsonProperty public boolean running = false;
    @JsonProperty public int spiderProgress = 0;
    @JsonProperty public int activeScanProgress = 0;
}
