package com.anchore.jenkins.plugins.zap.model.json;
import com.anchore.jenkins.plugins.zap.model.Alert;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class ZapAlerts {
    List<String> columnNames = Arrays.asList("ID", "Name", "URL", "Other", "Description", "Solution", "CWE-ID", "WASC-ID");
    List<String> fieldNames = Arrays.asList("id", "name", "url", "other", "description", "solution", "cweId", "wascId");

    @JsonProperty
    private List<Column> columns;
    @JsonProperty
    private List<List<String>> data;

    public ZapAlerts(List<Alert> alerts) {
        columns = new LinkedList<>();
        data = new LinkedList<>();

        for (String name : columnNames)
            columns.add(new Column(name));

        for (Alert alert : alerts) {
            try {
                List<String> row = new LinkedList<>();
                for (String name : fieldNames) {
                    Field field = Alert.class.getField(name);
                    row.add(String.valueOf(field.get(alert)));
                }
                data.add(row);
            }
            catch (IllegalAccessException e) {}
            catch (NoSuchFieldException e) { e.printStackTrace(); }
        }
    }
}
