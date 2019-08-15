package com.anchore.jenkins.plugins.zap;

import com.anchore.jenkins.plugins.anchore.ConsoleLog;
import com.google.common.base.Strings;
import hudson.Extension;
import hudson.FilePath;
import hudson.Launcher;
import hudson.model.AbstractProject;
import hudson.model.Run;
import hudson.model.TaskListener;
import hudson.tasks.BuildStepDescriptor;
import hudson.tasks.Builder;
import jenkins.tasks.SimpleBuildStep;
import net.sf.json.JSONObject;
import org.jenkinsci.Symbol;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.DataBoundSetter;
import org.kohsuke.stapler.StaplerRequest;

import javax.annotation.Nonnull;
import java.io.IOException;

public class ZapBuilder extends Builder implements SimpleBuildStep {
    private String server = null;

    private boolean enableDebug = false;

    @DataBoundConstructor
    public ZapBuilder(String server) {
        this.server = server;
    }

    @Override
    public void perform(@Nonnull Run<?, ?> run, @Nonnull FilePath filePath, @Nonnull Launcher launcher, @Nonnull TaskListener taskListener) throws InterruptedException, IOException {
        ConsoleLog console = new ConsoleLog("AnchorePlugin", taskListener.getLogger(), enableDebug);
        DescriptorImpl globalConfig = (DescriptorImpl)getDescriptor();

        if (Strings.isNullOrEmpty(server))
            server = globalConfig.getEurekaServer();

        console.logInfo("Started Cavas ZAP Plugin with Eureka server: " + server + " timeout: " + globalConfig.getTimeout());
    }

    @Symbol("zap") // For Jenkins pipeline workflow. This lets pipeline refer to step using the defined identifier
    @Extension // This indicates to Jenkins that this is an implementation of an extension point.
    public static final class DescriptorImpl extends BuildStepDescriptor<Builder> {
        private String eurekaServer;
        private String timeout;

        private int DEFAULT_TIMEOUT = 300;

        public String getEurekaServer() {
            return eurekaServer;
        }

        public String getTimeout() {
            return timeout;
        }

        public void setEurekaServer(String eurekaServer) {
            this.eurekaServer = eurekaServer;
        }

        public void setTimeout(String timeout) {
            this.timeout = timeout;
        }

        public int getTimeoutAsNumber() {
            try {
                return Integer.valueOf(timeout);
            } catch (NumberFormatException e) {
                return DEFAULT_TIMEOUT;
            }

        }

        @Override
        public boolean isApplicable(Class<? extends AbstractProject> aClass) {
            return true;
        }

        @Override
        public String getDisplayName() {
            return "Cavas OWASP ZAP Security Scanner";
        }

        @Override
        public boolean configure(StaplerRequest req, JSONObject formData) throws FormException {
            req.bindJSON(this, formData); // Use stapler request to bind
            save();
            return true;
        }
    }

}
