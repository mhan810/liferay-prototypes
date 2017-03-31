
package com.liferay.prototype.analytics.storage.stubs;

import java.util.HashMap;
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
    "additionalInfo",
    "applicationId",
    "channel",
    "event",
    "groupId",
    "messageContext",
    "messageFormat",
    "properties",
    "timestamp"
})
public class StoredAnalyticsEvent {

    @JsonProperty("additionalInfo")
    private AdditionalInfo additionalInfo;
    @JsonProperty("applicationId")
    private String applicationId;
    @JsonProperty("channel")
    private String channel;
    @JsonProperty("event")
    private String event;
    @JsonProperty("groupId")
    private Integer groupId;
    @JsonProperty("messageContext")
    private MessageContext messageContext;
    @JsonProperty("messageFormat")
    private String messageFormat;
    @JsonProperty("properties")
    private Properties properties;
    @JsonProperty("timestamp")
    private String timestamp;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The additionalInfo
     */
    @JsonProperty("additionalInfo")
    public AdditionalInfo getAdditionalInfo() {
        return additionalInfo;
    }

    /**
     * 
     * @param additionalInfo
     *     The additionalInfo
     */
    @JsonProperty("additionalInfo")
    public void setAdditionalInfo(AdditionalInfo additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    /**
     * 
     * @return
     *     The applicationId
     */
    @JsonProperty("applicationId")
    public String getApplicationId() {
        return applicationId;
    }

    /**
     * 
     * @param applicationId
     *     The applicationId
     */
    @JsonProperty("applicationId")
    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    /**
     * 
     * @return
     *     The channel
     */
    @JsonProperty("channel")
    public String getChannel() {
        return channel;
    }

    /**
     * 
     * @param channel
     *     The channel
     */
    @JsonProperty("channel")
    public void setChannel(String channel) {
        this.channel = channel;
    }

    /**
     * 
     * @return
     *     The event
     */
    @JsonProperty("event")
    public String getEvent() {
        return event;
    }

    /**
     * 
     * @param event
     *     The event
     */
    @JsonProperty("event")
    public void setEvent(String event) {
        this.event = event;
    }

    /**
     * 
     * @return
     *     The groupId
     */
    @JsonProperty("groupId")
    public Integer getGroupId() {
        return groupId;
    }

    /**
     * 
     * @param groupId
     *     The groupId
     */
    @JsonProperty("groupId")
    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    /**
     * 
     * @return
     *     The messageContext
     */
    @JsonProperty("messageContext")
    public MessageContext getMessageContext() {
        return messageContext;
    }

    /**
     * 
     * @param messageContext
     *     The messageContext
     */
    @JsonProperty("messageContext")
    public void setMessageContext(MessageContext messageContext) {
        this.messageContext = messageContext;
    }

    /**
     * 
     * @return
     *     The messageFormat
     */
    @JsonProperty("messageFormat")
    public String getMessageFormat() {
        return messageFormat;
    }

    /**
     * 
     * @param messageFormat
     *     The messageFormat
     */
    @JsonProperty("messageFormat")
    public void setMessageFormat(String messageFormat) {
        this.messageFormat = messageFormat;
    }

    /**
     * 
     * @return
     *     The properties
     */
    @JsonProperty("properties")
    public Properties getProperties() {
        return properties;
    }

    /**
     * 
     * @param properties
     *     The properties
     */
    @JsonProperty("properties")
    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    /**
     * 
     * @return
     *     The timestamp
     */
    @JsonProperty("timestamp")
    public String getTimestamp() {
        return timestamp;
    }

    /**
     * 
     * @param timestamp
     *     The timestamp
     */
    @JsonProperty("timestamp")
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
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
        return new HashCodeBuilder().append(additionalInfo).append(applicationId).append(channel).append(event).append(groupId).append(messageContext).append(messageFormat).append(properties).append(timestamp).append(additionalProperties).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof StoredAnalyticsEvent) == false) {
            return false;
        }
        StoredAnalyticsEvent rhs = ((StoredAnalyticsEvent) other);
        return new EqualsBuilder().append(additionalInfo, rhs.additionalInfo).append(applicationId, rhs.applicationId).append(channel, rhs.channel).append(event, rhs.event).append(groupId, rhs.groupId).append(messageContext, rhs.messageContext).append(messageFormat, rhs.messageFormat).append(properties, rhs.properties).append(timestamp, rhs.timestamp).append(additionalProperties, rhs.additionalProperties).isEquals();
    }

}
