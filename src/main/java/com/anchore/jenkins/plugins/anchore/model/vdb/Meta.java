package com.anchore.jenkins.plugins.anchore.model.vdb;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "limit",
    "next",
    "offset",
    "previous",
    "total_count"
})
public class Meta {

    @JsonProperty("limit")
    private Integer limit;
    @JsonProperty("next")
    private java.lang.Object next;
    @JsonProperty("offset")
    private Integer offset;
    @JsonProperty("previous")
    private java.lang.Object previous;
    @JsonProperty("total_count")
    private Integer totalCount;
    @JsonIgnore
    private Map<String, java.lang.Object> additionalProperties = new HashMap<String, java.lang.Object>();

    @JsonProperty("limit")
    public Integer getLimit() {
        return limit;
    }

    @JsonProperty("limit")
    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    @JsonProperty("next")
    public java.lang.Object getNext() {
        return next;
    }

    @JsonProperty("next")
    public void setNext(java.lang.Object next) {
        this.next = next;
    }

    @JsonProperty("offset")
    public Integer getOffset() {
        return offset;
    }

    @JsonProperty("offset")
    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    @JsonProperty("previous")
    public java.lang.Object getPrevious() {
        return previous;
    }

    @JsonProperty("previous")
    public void setPrevious(java.lang.Object previous) {
        this.previous = previous;
    }

    @JsonProperty("total_count")
    public Integer getTotalCount() {
        return totalCount;
    }

    @JsonProperty("total_count")
    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    @JsonAnyGetter
    public Map<String, java.lang.Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, java.lang.Object value) {
        this.additionalProperties.put(name, value);
    }

}
