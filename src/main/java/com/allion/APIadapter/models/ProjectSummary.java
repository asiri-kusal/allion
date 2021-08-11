
package com.allion.APIadapter.models;

import java.util.List;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "project_id",
    "weekly_summaries"
})

@Getter
@Setter
public class ProjectSummary {

    @JsonProperty("project_id")
    public String projectId;
    @JsonProperty("weekly_summaries")
    public List<WeeklySummary> weeklySummaries = null;

}
