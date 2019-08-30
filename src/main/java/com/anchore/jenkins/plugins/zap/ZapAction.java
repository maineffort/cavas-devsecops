package com.anchore.jenkins.plugins.zap;

import com.anchore.jenkins.plugins.anchore.AnchoreProjectAction;
import hudson.model.Action;
import hudson.model.Job;
import hudson.model.Run;
import jenkins.model.Jenkins;
import jenkins.tasks.SimpleBuildStep;

import java.security.SecureRandom;
import java.util.Collection;
import java.util.Collections;

public class ZapAction implements SimpleBuildStep.LastBuildAction{

    private Run<?, ?> build;
    private String alertsUrl;
    private String status;

    public ZapAction(Run<?, ?> build, String alertsLocation, String status) {
        this.build = build;
        this.alertsUrl =  "../artifact/" + alertsLocation;
        this.status = status;
    }

    @Override
    public Collection<? extends Action> getProjectActions() {
        Job<?,?> job = this.build.getParent();
        return Collections.singleton(new ZapProjectAction(job));
    }

    @Override
    public String getIconFileName() {
        return Jenkins.RESOURCE_PATH + "/plugin/anchore-container-scanner/images/cavas_logo.png";
    }

    @Override
    public String getDisplayName() {
        return "ZAP Report (" + status + ")";
    }

    @Override
    public String getUrlName() {
        return "zap-results";
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Run<?, ?> getBuild() {
        return build;
    }

    public void setBuild(Run<?, ?> build) {
        this.build = build;
    }

    public String getAlertsUrl() {
        return alertsUrl;
    }

    public void setAlertsUrl(String alertsUrl) {
        this.alertsUrl = alertsUrl;
    }
}
