
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
    "companyId",
    "deviceId",
    "deviceType",
    "ipAddress",
    "languageId",
    "location",
    "sessionId",
    "signedIn",
    "userId",
    "userName"
})
public class MessageContext {

    @JsonProperty("companyId")
    private Integer companyId;
    @JsonProperty("deviceId")
    private String deviceId;
    @JsonProperty("deviceType")
    private String deviceType;
    @JsonProperty("ipAddress")
    private String ipAddress;
    @JsonProperty("languageId")
    private String languageId;
    @JsonProperty("location")
    private Location location;
    @JsonProperty("sessionId")
    private String sessionId;
    @JsonProperty("signedIn")
    private Boolean signedIn;
    @JsonProperty("userId")
    private Integer userId;
    @JsonProperty("userName")
    private String userName;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The companyId
     */
    @JsonProperty("companyId")
    public Integer getCompanyId() {
        return companyId;
    }

    /**
     * 
     * @param companyId
     *     The companyId
     */
    @JsonProperty("companyId")
    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    /**
     * 
     * @return
     *     The deviceId
     */
    @JsonProperty("deviceId")
    public String getDeviceId() {
        return deviceId;
    }

    /**
     * 
     * @param deviceId
     *     The deviceId
     */
    @JsonProperty("deviceId")
    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    /**
     * 
     * @return
     *     The deviceType
     */
    @JsonProperty("deviceType")
    public String getDeviceType() {
        return deviceType;
    }

    /**
     * 
     * @param deviceType
     *     The deviceType
     */
    @JsonProperty("deviceType")
    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    /**
     * 
     * @return
     *     The ipAddress
     */
    @JsonProperty("ipAddress")
    public String getIpAddress() {
        return ipAddress;
    }

    /**
     * 
     * @param ipAddress
     *     The ipAddress
     */
    @JsonProperty("ipAddress")
    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    /**
     * 
     * @return
     *     The languageId
     */
    @JsonProperty("languageId")
    public String getLanguageId() {
        return languageId;
    }

    /**
     * 
     * @param languageId
     *     The languageId
     */
    @JsonProperty("languageId")
    public void setLanguageId(String languageId) {
        this.languageId = languageId;
    }

    /**
     * 
     * @return
     *     The location
     */
    @JsonProperty("location")
    public Location getLocation() {
        return location;
    }

    /**
     * 
     * @param location
     *     The location
     */
    @JsonProperty("location")
    public void setLocation(Location location) {
        this.location = location;
    }

    /**
     * 
     * @return
     *     The sessionId
     */
    @JsonProperty("sessionId")
    public String getSessionId() {
        return sessionId;
    }

    /**
     * 
     * @param sessionId
     *     The sessionId
     */
    @JsonProperty("sessionId")
    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    /**
     * 
     * @return
     *     The signedIn
     */
    @JsonProperty("signedIn")
    public Boolean getSignedIn() {
        return signedIn;
    }

    /**
     * 
     * @param signedIn
     *     The signedIn
     */
    @JsonProperty("signedIn")
    public void setSignedIn(Boolean signedIn) {
        this.signedIn = signedIn;
    }

    /**
     * 
     * @return
     *     The userId
     */
    @JsonProperty("userId")
    public Integer getUserId() {
        return userId;
    }

    /**
     * 
     * @param userId
     *     The userId
     */
    @JsonProperty("userId")
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * 
     * @return
     *     The userName
     */
    @JsonProperty("userName")
    public String getUserName() {
        return userName;
    }

    /**
     * 
     * @param userName
     *     The userName
     */
    @JsonProperty("userName")
    public void setUserName(String userName) {
        this.userName = userName;
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
        return new HashCodeBuilder().append(companyId).append(deviceId).append(deviceType).append(ipAddress).append(languageId).append(location).append(sessionId).append(signedIn).append(userId).append(userName).append(additionalProperties).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof MessageContext) == false) {
            return false;
        }
        MessageContext rhs = ((MessageContext) other);
        return new EqualsBuilder().append(companyId, rhs.companyId).append(deviceId, rhs.deviceId).append(deviceType, rhs.deviceType).append(ipAddress, rhs.ipAddress).append(languageId, rhs.languageId).append(location, rhs.location).append(sessionId, rhs.sessionId).append(signedIn, rhs.signedIn).append(userId, rhs.userId).append(userName, rhs.userName).append(additionalProperties, rhs.additionalProperties).isEquals();
    }

}
