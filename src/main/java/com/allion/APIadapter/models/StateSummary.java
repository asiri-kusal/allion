
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
    "state",
    "count",
    "issues"
})
@Getter
@Setter
public class StateSummary {

    @JsonProperty("state")
    public String state;
    @JsonProperty("count")
    public Integer count;
    @JsonProperty("issues")
    public List<Issue> issues = null;

}
