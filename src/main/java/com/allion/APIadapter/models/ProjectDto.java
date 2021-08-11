package com.allion.APIadapter.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
                       "project_id",
                       "from_week",
                       "to_week",
                       "types",
                       "states"
                   })
public class ProjectDto {

    @JsonProperty("project_id")
    public String projectId;
    @JsonProperty("from_week")
    public String fromWeek;
    @JsonProperty("to_week")
    public String toWeek;
    @JsonProperty("types")
    public List<String> types;
    @JsonProperty("states")
    public List<String> states;

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getFromWeek() {
        return fromWeek;
    }

    public void setFromWeek(String fromWeek) {
        this.fromWeek = fromWeek;
    }

    public String getToWeek() {
        return toWeek;
    }

    public void setToWeek(String toWeek) {
        this.toWeek = toWeek;
    }

    public List<String> getTypes() {
        return types;
    }

    public void setTypes(List<String> types) {
        this.types = types;
    }

    public List<String> getStates() {
        return states;
    }

    public void setStates(List<String> states) {
        this.states = states;
    }
}
