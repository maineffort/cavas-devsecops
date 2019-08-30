package com.anchore.jenkins.plugins.zap;
import com.anchore.jenkins.plugins.anchore.AnchoreAction;
import hudson.model.Action;
import hudson.model.Job;
import hudson.model.Run;
import jenkins.model.Jenkins;
import org.kohsuke.stapler.StaplerRequest;
import org.kohsuke.stapler.StaplerResponse;

import java.io.IOException;

public class ZapProjectAction implements Action {
    public final Job<?,?> job;

    public ZapProjectAction(Job<?,?> job) {
        this.job = job;
    }

    @Override
    public String getIconFileName() {
        return Jenkins.RESOURCE_PATH + "/plugin/anchore-container-scanner/images/cavas_logo.png";
    }

    @Override
    public String getDisplayName() {
        return "Zap Report";
    }

    @Override
    public String getUrlName() {
        return "zap";
    }

    public void doIndex(final StaplerRequest request, final StaplerResponse response) throws IOException {
        Run<?, ?> lastRun = this.job.getLastCompletedBuild();
        if (lastRun != null) {
            ZapAction a = lastRun.getAction(ZapAction.class);
            if (a != null)
                response.sendRedirect2(String.format("../%d/%s", lastRun.getNumber(), a.getUrlName()));
        }
    }
}
