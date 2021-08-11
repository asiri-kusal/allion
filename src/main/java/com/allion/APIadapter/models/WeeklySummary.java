
package com.allion.APIadapter.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "week",
    "state_summaries"
})
@Getter
@Setter
public class WeeklySummary {

    @JsonProperty("week")
    public String week;
    @JsonProperty("state_summaries")
    public List<StateSummary> stateSummaries = null;

}
