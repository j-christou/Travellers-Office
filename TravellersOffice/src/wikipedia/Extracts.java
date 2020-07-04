package wikipedia;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang.builder.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "warnings"
})
public class Extracts {

    @JsonProperty("warnings")
    private String warnings;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     *
     */
    public Extracts() {
    }

    /**
     *
     * @param warnings
     */
    public Extracts(String warnings) {
        super();
        this.warnings = warnings;
    }

    @JsonProperty("warnings")
    public String getWarnings() {
        return warnings;
    }

    @JsonProperty("warnings")
    public void setWarnings(String warnings) {
        this.warnings = warnings;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("warnings", warnings).append("additionalProperties", additionalProperties).toString();
    }

}
