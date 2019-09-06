package com.anchore.jenkins.plugins.zap;

import com.anchore.jenkins.plugins.anchore.ConsoleLog;
import com.anchore.jenkins.plugins.zap.model.Alert;
import com.anchore.jenkins.plugins.zap.model.json.ZapAlerts;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;
import hudson.Extension;
import hudson.FilePath;
import hudson.Launcher;
import hudson.model.AbstractProject;
import hudson.model.Run;
import hudson.model.TaskListener;
import hudson.tasks.ArtifactArchiver;
import hudson.tasks.BuildStepDescriptor;
import hudson.tasks.Builder;
import jenkins.tasks.SimpleBuildStep;
import net.sf.json.JSONObject;
import org.jenkinsci.Symbol;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.DataBoundSetter;
import org.kohsuke.stapler.StaplerRequest;

import javax.annotation.Nonnull;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class ZapBuilder extends Builder implements SimpleBuildStep {
    static String outputDirName = "zapReport";
    static String alertsFileName = "alerts.json";

    private String server;
    private String gitCommitId;
    private boolean enableDebug = false;

    private ConsoleLog console;

    @DataBoundSetter
    public void setServer(String server) {
        this.server = server;
    }

    @DataBoundConstructor
    public ZapBuilder(String gitCommitId) {
        this.gitCommitId = gitCommitId;
    }

    @Override
    public void perform(@Nonnull Run<?, ?> run, @Nonnull FilePath workspace, @Nonnull Launcher launcher, @Nonnull TaskListener taskListener) throws InterruptedException, IOException {
        console = new ConsoleLog("AnchorePlugin", taskListener.getLogger(), enableDebug);
        DescriptorImpl globalConfig = (DescriptorImpl)getDescriptor();
        if (Strings.isNullOrEmpty(server))
            server = globalConfig.getEurekaServer();
        gitCommitId = gitCommitId.substring(0, 7);

        console.logInfo("Started Cavas ZAP Plugin with Eureka server: " + server + " timeout: " + globalConfig.getTimeout() + " commit-ID: " + gitCommitId);

        int timeout = globalConfig.getTimeoutAsNumber();
        EurekaClient client = new EurekaClient(gitCommitId, server, console);
        client.runAnalysis(timeout);

        archiveAlerts(client.getAlerts(), run, workspace, launcher, taskListener);
    }

    private void archiveAlerts(List<Alert> alerts, Run<?, ?> run, FilePath workspace, Launcher launcher, TaskListener taskListener) throws IOException, InterruptedException{
        if (alerts !=  null) {
            ZapAlerts zapAlerts = new ZapAlerts(alerts);

            String alertsLocation = outputDirName + "/" + alertsFileName;
            FilePath outputFile = new FilePath(workspace, alertsLocation);
            try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(outputFile.write(), StandardCharsets.UTF_8))) {
                bw.write(new ObjectMapper().writeValueAsString(zapAlerts));
            }

            console.logInfo("Archiving results");
            ArtifactArchiver artifactArchiver = new ArtifactArchiver(outputDirName + "/");
            artifactArchiver.perform(run, workspace, launcher, taskListener);

            run.addAction(new ZapAction(run, alertsLocation, "PASS"));
        }
    }

    @Symbol("zap") // For Jenkins pipeline workflow. This lets pipeline refer to step using the defined identifier
    @Extension // This indicates to Jenkins that this is an implementation of an extension point.
    public static final class DescriptorImpl extends BuildStepDescriptor<Builder> {
        private String eurekaServer;
        private String timeout;
        private int timeoutNumber;

        private int DEFAULT_TIMEOUT = 300;

        public String getEurekaServer() {
            return eurekaServer;
        }

        public String getTimeout() {
            if (timeout == null)
                return String.valueOf(DEFAULT_TIMEOUT);
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
