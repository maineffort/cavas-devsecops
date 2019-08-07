package com.anchore.jenkins.plugins.anchore.model.security;

import com.anchore.jenkins.plugins.anchore.model.ImageVulnerability;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class AnchoreVulnerabilities {
    private static String[] columnNames = {"Tag", "CVE ID", "CWE ID", "Severity", "CVSS Score", "CVSS Vector", "Vulnerability Package", "Fix Available", "URL"};

    @JsonProperty
    private List<Column> columns;
    @JsonProperty
    private List<List<String>> data;

    public AnchoreVulnerabilities(List<ImageVulnerability> imageVulnerabilities) {
        columns = new LinkedList<>();
        data = new LinkedList<>();

        for (String columnName : columnNames)
            columns.add(new Column(columnName));

        for (ImageVulnerability vulnerability : imageVulnerabilities) {
            List<String> row = Arrays.asList(
                    vulnerability.getImagename(),
                    vulnerability.getCveId(),
                    vulnerability.getCweId(),
                    vulnerability.getSeverity(),
                    String.valueOf(vulnerability.getCvssScore()),
                    vulnerability.getCvssVector(),
                    vulnerability.getVulnerabilityPackage(),
                    vulnerability.getFixAvailable(),
                    vulnerability.getUrl());
            data.add(row);
        }
    }
}
