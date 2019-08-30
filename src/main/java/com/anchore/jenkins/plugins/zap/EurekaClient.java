package com.anchore.jenkins.plugins.zap;

import com.anchore.jenkins.plugins.anchore.ConsoleLog;
import com.anchore.jenkins.plugins.zap.model.Alert;
import com.anchore.jenkins.plugins.zap.model.Result;
import com.anchore.jenkins.plugins.zap.model.Status;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import hudson.AbortException;
import hudson.FilePath;
import hudson.Launcher;
import hudson.model.Run;
import hudson.model.TaskListener;
import hudson.tasks.ArtifactArchiver;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import javax.annotation.Nonnull;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class EurekaClient {
    private String commitId;
    private String serverUrl;
    private ConsoleLog console;
    List<Alert> alerts;

    private interface Condition {
        boolean evaluate(HttpResponse response) throws AbortException;
    }

    public EurekaClient(String commitId, String server, ConsoleLog console) {
        this.commitId = commitId;
        this.serverUrl = server;
        this.console = console;
    }

    private AbortException abortDueToException(Exception e) {
        e.printStackTrace();
        return new AbortException(String.format("FAILING due to Exception: %s, message: %s", e.getCause(), e.getMessage()));
    }

    private HttpResponse requestUntil(Condition condition, String url, int timeout) throws AbortException {

        try {

            HttpResponse response;
            int timeElapsed = 0;
            int interval = 500;
            do {
                if (timeElapsed > timeout)
                    throw new TimeoutException();

                HttpClient client = HttpClientBuilder.create().build();
                HttpGet request = new HttpGet(serverUrl + url);
                request.addHeader("Content-Type", "application/json");
                response = client.execute(request);

                Thread.sleep(interval);
                timeElapsed += interval;
            } while (!condition.evaluate(response));

            return response;
        }
        catch (TimeoutException e) { throw new AbortException("FAILING due to Timeout on url  " + serverUrl + url); }
        catch (Exception e) { throw abortDueToException(e); }
    }

    private HttpResponse requestOn(String url) throws AbortException {
        try {
            HttpClient client = HttpClientBuilder.create().build();
            HttpGet request = new HttpGet(serverUrl + url);
            request.addHeader("Content-Type", "application/json");
            return client.execute(request);
        } catch (Exception e) { throw abortDueToException(e); }
    }

    private <T> T parseToJson(HttpResponse response, TypeReference<T> c) throws AbortException {
        try {
            String responseBody = EntityUtils.toString(response.getEntity());
            return new ObjectMapper().readValue(responseBody, c);
        } catch (IOException e) { throw abortDueToException(e); }
    }

    public void runAnalysis(int timeout) throws AbortException {
        timeout *= 1000;
        console.logInfo("Starting ZAP testing for application with commit Id: " + commitId);

        // wait until the application has registered
        console.logInfo("Waiting for application registration");
        String statusUrl = String.format("/app/%s/status", commitId);
        requestUntil((HttpResponse response) -> { return response.getStatusLine().getStatusCode() == 200; }, statusUrl, timeout);

        // wait until the testing is done
        console.logInfo("Waiting for the scan to finish");
        requestUntil((HttpResponse response) -> {
            Status status = parseToJson(response, new TypeReference<Status>(){});
            if (status != null) return !status.running;
            return false;
        }, statusUrl, timeout);

        // query the alerts
        console.logInfo("Retrieving alerts");
        String alertsUrl = String.format("/app/%s/alerts", commitId);
        alerts = parseToJson(requestOn(alertsUrl), new TypeReference<List<Alert>>(){});

        // query the result
        console.logInfo("Querying the result");
        String resultUrl = String.format("/app/%s/result", commitId);
        Result result = parseToJson(requestOn(resultUrl), new TypeReference<Result>(){});

        if (!result.result.equals("success"))
            throw new AbortException(String.format("FAILING due to scan result: '%s', message: '%s'", result.result, result.message));
    }

    public List<Alert> getAlerts() {
        return alerts;
    }
}
