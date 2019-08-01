package com.anchore.jenkins.plugins.anchore.model.vdb;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "availability",
    "confidentiality",
    "cpe_id",
    "cvss_date",
    "cvss_score",
    "cvss_temp_score",
    "cvss_temp_vector",
    "cvss_vector",
    "cwe_id",
    "description",
    "exploitability_score",
    "identifier",
    "impact_score",
    "imported",
    "integrity",
    "last_modified",
    "oval_id",
    "published_date",
    "refs",
    "sols"
})
public class Object {

    @JsonProperty("availability")
    private String availability;
    @JsonProperty("confidentiality")
    private String confidentiality;
    @JsonProperty("cpe_id")
    private List<CpeId> cpeId = null;
    @JsonProperty("cvss_date")
    private java.lang.Object cvssDate;
    @JsonProperty("cvss_score")
    private Double cvssScore;
    @JsonProperty("cvss_temp_score")
    private java.lang.Object cvssTempScore;
    @JsonProperty("cvss_temp_vector")
    private java.lang.Object cvssTempVector;
    @JsonProperty("cvss_vector")
    private String cvssVector;
    @JsonProperty("cwe_id")
    private String cweId;
    @JsonProperty("description")
    private String description;
    @JsonProperty("exploitability_score")
    private java.lang.Object exploitabilityScore;
    @JsonProperty("identifier")
    private String identifier;
    @JsonProperty("impact_score")
    private java.lang.Object impactScore;
    @JsonProperty("imported")
    private java.lang.Object imported;
    @JsonProperty("integrity")
    private String integrity;
    @JsonProperty("last_modified")
    private String lastModified;
    @JsonProperty("oval_id")
    private java.lang.Object ovalId;
    @JsonProperty("published_date")
    private java.lang.Object publishedDate;
    @JsonProperty("refs")
    private List<Ref> refs = null;
    @JsonProperty("sols")
    private List<java.lang.Object> sols = null;
//    @JsonIgnore
//    private Map<String, java.lang.Object> additionalProperties = new HashMap<String, java.lang.Object>();

    @JsonProperty("availability")
    public String getAvailability() {
        return availability;
    }

    @JsonProperty("availability")
    public void setAvailability(String availability) {
        this.availability = availability;
    }

    @JsonProperty("confidentiality")
    public String getConfidentiality() {
        return confidentiality;
    }

    @JsonProperty("confidentiality")
    public void setConfidentiality(String confidentiality) {
        this.confidentiality = confidentiality;
    }

    @JsonProperty("cpe_id")
    public List<CpeId> getCpeId() {
        return cpeId;
    }

    @JsonProperty("cpe_id")
    public void setCpeId(List<CpeId> cpeId) {
        this.cpeId = cpeId;
    }

    @JsonProperty("cvss_date")
    public java.lang.Object getCvssDate() {
        return cvssDate;
    }

    @JsonProperty("cvss_date")
    public void setCvssDate(java.lang.Object cvssDate) {
        this.cvssDate = cvssDate;
    }

    @JsonProperty("cvss_score")
    public Double getCvssScore() {
        return cvssScore;
    }

    @JsonProperty("cvss_score")
    public void setCvssScore(Double cvssScore) {
        this.cvssScore = cvssScore;
    }

    @JsonProperty("cvss_temp_score")
    public java.lang.Object getCvssTempScore() {
        return cvssTempScore;
    }

    @JsonProperty("cvss_temp_score")
    public void setCvssTempScore(java.lang.Object cvssTempScore) {
        this.cvssTempScore = cvssTempScore;
    }

    @JsonProperty("cvss_temp_vector")
    public java.lang.Object getCvssTempVector() {
        return cvssTempVector;
    }

    @JsonProperty("cvss_temp_vector")
    public void setCvssTempVector(java.lang.Object cvssTempVector) {
        this.cvssTempVector = cvssTempVector;
    }

    @JsonProperty("cvss_vector")
    public String getCvssVector() {
        return cvssVector;
    }

    @JsonProperty("cvss_vector")
    public void setCvssVector(String cvssVector) {
        this.cvssVector = cvssVector;
    }

    @JsonProperty("cwe_id")
    public String getCweId() {
        return cweId;
    }

    @JsonProperty("cwe_id")
    public void setCweId(String cweId) {
        this.cweId = cweId;
    }

    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    @JsonProperty("description")
    public void setDescription(String description) {
        this.description = description;
    }

    @JsonProperty("exploitability_score")
    public java.lang.Object getExploitabilityScore() {
        return exploitabilityScore;
    }

    @JsonProperty("exploitability_score")
    public void setExploitabilityScore(java.lang.Object exploitabilityScore) {
        this.exploitabilityScore = exploitabilityScore;
    }

    @JsonProperty("identifier")
    public String getIdentifier() {
        return identifier;
    }

    @JsonProperty("identifier")
    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    @JsonProperty("impact_score")
    public java.lang.Object getImpactScore() {
        return impactScore;
    }

    @JsonProperty("impact_score")
    public void setImpactScore(java.lang.Object impactScore) {
        this.impactScore = impactScore;
    }

    @JsonProperty("imported")
    public java.lang.Object getImported() {
        return imported;
    }

    @JsonProperty("imported")
    public void setImported(java.lang.Object imported) {
        this.imported = imported;
    }

    @JsonProperty("integrity")
    public String getIntegrity() {
        return integrity;
    }

    @JsonProperty("integrity")
    public void setIntegrity(String integrity) {
        this.integrity = integrity;
    }

    @JsonProperty("last_modified")
    public String getLastModified() {
        return lastModified;
    }

    @JsonProperty("last_modified")
    public void setLastModified(String lastModified) {
        this.lastModified = lastModified;
    }

    @JsonProperty("oval_id")
    public java.lang.Object getOvalId() {
        return ovalId;
    }

    @JsonProperty("oval_id")
    public void setOvalId(java.lang.Object ovalId) {
        this.ovalId = ovalId;
    }

    @JsonProperty("published_date")
    public java.lang.Object getPublishedDate() {
        return publishedDate;
    }

    @JsonProperty("published_date")
    public void setPublishedDate(java.lang.Object publishedDate) {
        this.publishedDate = publishedDate;
    }

    @JsonProperty("refs")
    public List<Ref> getRefs() {
        return refs;
    }

    @JsonProperty("refs")
    public void setRefs(List<Ref> refs) {
        this.refs = refs;
    }

    @JsonProperty("sols")
    public List<java.lang.Object> getSols() {
        return sols;
    }

    @JsonProperty("sols")
    public void setSols(List<java.lang.Object> sols) {
        this.sols = sols;
    }
//
//    @JsonAnyGetter
//    public Map<String, java.lang.Object> getAdditionalProperties() {
//        return this.additionalProperties;
//    }
//
//    @JsonAnySetter
//    public void setAdditionalProperty(String name, java.lang.Object value) {
//        this.additionalProperties.put(name, value);
//    }

	@Override
	public String toString() {
		return "Object [availability=" + availability + ", confidentiality=" + confidentiality + ", cpeId=" + cpeId
				+ ", cvssDate=" + cvssDate + ", cvssScore=" + cvssScore + ", cvssTempScore=" + cvssTempScore
				+ ", cvssTempVector=" + cvssTempVector + ", cvssVector=" + cvssVector + ", cweId=" + cweId
				+ ", description=" + description + ", exploitabilityScore=" + exploitabilityScore + ", identifier="
				+ identifier + ", impactScore=" + impactScore + ", imported=" + imported + ", integrity=" + integrity
				+ ", lastModified=" + lastModified + ", ovalId=" + ovalId + ", publishedDate=" + publishedDate
				+ ", refs=" + refs + ", sols=" + sols + "]";
	}
    
    
    

}
