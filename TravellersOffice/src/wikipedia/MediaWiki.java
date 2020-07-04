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
        "batchcomplete",
        "warnings",
        "query"
})
public class MediaWiki {

    @JsonProperty("batchcomplete")
    private Boolean batchcomplete;
    @JsonProperty("warnings")
    private Warnings warnings;
    @JsonProperty("query")
    private Query query;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     *
     */
    public MediaWiki() {
    }

    /**
     *
     * @param batchcomplete
     * @param warnings
     * @param query
     */
    public MediaWiki(Boolean batchcomplete, Warnings warnings, Query query) {
        super();
        this.batchcomplete = batchcomplete;
        this.warnings = warnings;
        this.query = query;
    }

    @JsonProperty("batchcomplete")
    public Boolean getBatchcomplete() {
        return batchcomplete;
    }

    @JsonProperty("batchcomplete")
    public void setBatchcomplete(Boolean batchcomplete) {
        this.batchcomplete = batchcomplete;
    }

    @JsonProperty("warnings")
    public Warnings getWarnings() {
        return warnings;
    }

    @JsonProperty("warnings")
    public void setWarnings(Warnings warnings) {
        this.warnings = warnings;
    }

    @JsonProperty("query")
    public Query getQuery() {
        return query;
    }

    @JsonProperty("query")
    public void setQuery(Query query) {
        this.query = query;
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
        return new ToStringBuilder(this).append("batchcomplete", batchcomplete).append("warnings", warnings).append("query", query).append("additionalProperties", additionalProperties).toString();
    }

}