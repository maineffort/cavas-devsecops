package com.anchore.jenkins.plugins.zap.model.json;
import com.anchore.jenkins.plugins.zap.model.Alert;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class ZapAlerts {
    Field[] fields = Alert.class.getFields();

    @JsonProperty
    private List<Column> columns;
    @JsonProperty
    private List<List<String>> data;

    public ZapAlerts(List<Alert> alerts) {
        columns = new LinkedList<>();
        data = new LinkedList<>();

        for (Field field : fields)
            columns.add(new Column(field.getName()));

        for (Alert alert : alerts) {
            try {
                List<String> row = new LinkedList<>();
                for (Field field : fields)
                    row.add(String.valueOf(field.get(alert)));
                data.add(row);
            } catch (IllegalAccessException e) {}
        }
    }
}
