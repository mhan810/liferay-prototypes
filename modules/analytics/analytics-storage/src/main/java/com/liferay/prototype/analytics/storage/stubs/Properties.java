
package com.liferay.prototype.analytics.storage.stubs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "elementId",
    "elementName",
    "entityId",
    "entityName",
    "entityType",
    "lastElementId",
    "lastElementName",
    "referrers"
})
public class Properties {

    @JsonProperty("elementId")
    private String elementId;
    @JsonProperty("elementName")
    private String elementName;
    @JsonProperty("entityId")
    private Integer entityId;
    @JsonProperty("entityName")
    private String entityName;
    @JsonProperty("entityType")
    private String entityType;
    @JsonProperty("lastElementId")
    private String lastElementId;
    @JsonProperty("lastElementName")
    private String lastElementName;
    @JsonProperty("referrers")
    private List<Object> referrers = new ArrayList<Object>();
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The elementId
     */
    @JsonProperty("elementId")
    public String getElementId() {
        return elementId;
    }

    /**
     * 
     * @param elementId
     *     The elementId
     */
    @JsonProperty("elementId")
    public void setElementId(String elementId) {
        this.elementId = elementId;
    }

    /**
     * 
     * @return
     *     The elementName
     */
    @JsonProperty("elementName")
    public String getElementName() {
        return elementName;
    }

    /**
     * 
     * @param elementName
     *     The elementName
     */
    @JsonProperty("elementName")
    public void setElementName(String elementName) {
        this.elementName = elementName;
    }

    /**
     * 
     * @return
     *     The entityId
     */
    @JsonProperty("entityId")
    public Integer getEntityId() {
        return entityId;
    }

    /**
     * 
     * @param entityId
     *     The entityId
     */
    @JsonProperty("entityId")
    public void setEntityId(Integer entityId) {
        this.entityId = entityId;
    }

    /**
     * 
     * @return
     *     The entityName
     */
    @JsonProperty("entityName")
    public String getEntityName() {
        return entityName;
    }

    /**
     * 
     * @param entityName
     *     The entityName
     */
    @JsonProperty("entityName")
    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    /**
     * 
     * @return
     *     The entityType
     */
    @JsonProperty("entityType")
    public String getEntityType() {
        return entityType;
    }

    /**
     * 
     * @param entityType
     *     The entityType
     */
    @JsonProperty("entityType")
    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }

    /**
     * 
     * @return
     *     The lastElementId
     */
    @JsonProperty("lastElementId")
    public String getLastElementId() {
        return lastElementId;
    }

    /**
     * 
     * @param lastElementId
     *     The lastElementId
     */
    @JsonProperty("lastElementId")
    public void setLastElementId(String lastElementId) {
        this.lastElementId = lastElementId;
    }

    /**
     * 
     * @return
     *     The lastElementName
     */
    @JsonProperty("lastElementName")
    public String getLastElementName() {
        return lastElementName;
    }

    /**
     * 
     * @param lastElementName
     *     The lastElementName
     */
    @JsonProperty("lastElementName")
    public void setLastElementName(String lastElementName) {
        this.lastElementName = lastElementName;
    }

    /**
     * 
     * @return
     *     The referrers
     */
    @JsonProperty("referrers")
    public List<Object> getReferrers() {
        return referrers;
    }

    /**
     * 
     * @param referrers
     *     The referrers
     */
    @JsonProperty("referrers")
    public void setReferrers(List<Object> referrers) {
        this.referrers = referrers;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
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
    public int hashCode() {
        return new HashCodeBuilder().append(elementId).append(elementName).append(entityId).append(entityName).append(entityType).append(lastElementId).append(lastElementName).append(referrers).append(additionalProperties).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Properties) == false) {
            return false;
        }
        Properties rhs = ((Properties) other);
        return new EqualsBuilder().append(elementId, rhs.elementId).append(elementName, rhs.elementName).append(entityId, rhs.entityId).append(entityName, rhs.entityName).append(entityType, rhs.entityType).append(lastElementId, rhs.lastElementId).append(lastElementName, rhs.lastElementName).append(referrers, rhs.referrers).append(additionalProperties, rhs.additionalProperties).isEquals();
    }

}
