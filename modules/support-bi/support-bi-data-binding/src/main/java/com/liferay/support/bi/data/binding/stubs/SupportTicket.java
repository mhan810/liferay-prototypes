
package com.liferay.support.bi.data.binding.stubs;

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
    "accountCode",
    "component",
    "description",
    "liferayVersion",
    "lppComponents",
    "lppResolution",
    "lppTicket",
    "status",
    "subject",
    "supportRegion",
    "ticketAssignment",
    "ticketClosedDate",
    "ticketCreateDate",
    "ticketNumber"
})
public class SupportTicket {

    @JsonProperty("accountCode")
    private String accountCode;
    @JsonProperty("component")
    private String component;
    @JsonProperty("description")
    private String description;
    @JsonProperty("liferayVersion")
    private String liferayVersion;
    @JsonProperty("lppComponents")
    private List<String> lppComponents = new ArrayList<String>();
    @JsonProperty("lppResolution")
    private String lppResolution;
    @JsonProperty("lppTicket")
    private String lppTicket;
    @JsonProperty("status")
    private String status;
    @JsonProperty("subject")
    private String subject;
    @JsonProperty("supportRegion")
    private String supportRegion;
    @JsonProperty("ticketAssignment")
    private String ticketAssignment;
    @JsonProperty("ticketClosedDate")
    private String ticketClosedDate;
    @JsonProperty("ticketCreateDate")
    private String ticketCreateDate;
    @JsonProperty("ticketNumber")
    private String ticketNumber;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The accountCode
     */
    @JsonProperty("accountCode")
    public String getAccountCode() {
        return accountCode;
    }

    /**
     * 
     * @param accountCode
     *     The accountCode
     */
    @JsonProperty("accountCode")
    public void setAccountCode(String accountCode) {
        this.accountCode = accountCode;
    }

    /**
     * 
     * @return
     *     The component
     */
    @JsonProperty("component")
    public String getComponent() {
        return component;
    }

    /**
     * 
     * @param component
     *     The component
     */
    @JsonProperty("component")
    public void setComponent(String component) {
        this.component = component;
    }

    /**
     * 
     * @return
     *     The description
     */
    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    /**
     * 
     * @param description
     *     The description
     */
    @JsonProperty("description")
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 
     * @return
     *     The liferayVersion
     */
    @JsonProperty("liferayVersion")
    public String getLiferayVersion() {
        return liferayVersion;
    }

    /**
     * 
     * @param liferayVersion
     *     The liferayVersion
     */
    @JsonProperty("liferayVersion")
    public void setLiferayVersion(String liferayVersion) {
        this.liferayVersion = liferayVersion;
    }

    /**
     * 
     * @return
     *     The lppComponents
     */
    @JsonProperty("lppComponents")
    public List<String> getLppComponents() {
        return lppComponents;
    }

    /**
     * 
     * @param lppComponents
     *     The lppComponents
     */
    @JsonProperty("lppComponents")
    public void setLppComponents(List<String> lppComponents) {
        this.lppComponents = lppComponents;
    }

    /**
     * 
     * @return
     *     The lppResolution
     */
    @JsonProperty("lppResolution")
    public String getLppResolution() {
        return lppResolution;
    }

    /**
     * 
     * @param lppResolution
     *     The lppResolution
     */
    @JsonProperty("lppResolution")
    public void setLppResolution(String lppResolution) {
        this.lppResolution = lppResolution;
    }

    /**
     * 
     * @return
     *     The lppTicket
     */
    @JsonProperty("lppTicket")
    public String getLppTicket() {
        return lppTicket;
    }

    /**
     * 
     * @param lppTicket
     *     The lppTicket
     */
    @JsonProperty("lppTicket")
    public void setLppTicket(String lppTicket) {
        this.lppTicket = lppTicket;
    }

    /**
     * 
     * @return
     *     The status
     */
    @JsonProperty("status")
    public String getStatus() {
        return status;
    }

    /**
     * 
     * @param status
     *     The status
     */
    @JsonProperty("status")
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * 
     * @return
     *     The subject
     */
    @JsonProperty("subject")
    public String getSubject() {
        return subject;
    }

    /**
     * 
     * @param subject
     *     The subject
     */
    @JsonProperty("subject")
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * 
     * @return
     *     The supportRegion
     */
    @JsonProperty("supportRegion")
    public String getSupportRegion() {
        return supportRegion;
    }

    /**
     * 
     * @param supportRegion
     *     The supportRegion
     */
    @JsonProperty("supportRegion")
    public void setSupportRegion(String supportRegion) {
        this.supportRegion = supportRegion;
    }

    /**
     * 
     * @return
     *     The ticketAssignment
     */
    @JsonProperty("ticketAssignment")
    public String getTicketAssignment() {
        return ticketAssignment;
    }

    /**
     * 
     * @param ticketAssignment
     *     The ticketAssignment
     */
    @JsonProperty("ticketAssignment")
    public void setTicketAssignment(String ticketAssignment) {
        this.ticketAssignment = ticketAssignment;
    }

    /**
     * 
     * @return
     *     The ticketClosedDate
     */
    @JsonProperty("ticketClosedDate")
    public String getTicketClosedDate() {
        return ticketClosedDate;
    }

    /**
     * 
     * @param ticketClosedDate
     *     The ticketClosedDate
     */
    @JsonProperty("ticketClosedDate")
    public void setTicketClosedDate(String ticketClosedDate) {
        this.ticketClosedDate = ticketClosedDate;
    }

    /**
     * 
     * @return
     *     The ticketCreateDate
     */
    @JsonProperty("ticketCreateDate")
    public String getTicketCreateDate() {
        return ticketCreateDate;
    }

    /**
     * 
     * @param ticketCreateDate
     *     The ticketCreateDate
     */
    @JsonProperty("ticketCreateDate")
    public void setTicketCreateDate(String ticketCreateDate) {
        this.ticketCreateDate = ticketCreateDate;
    }

    /**
     * 
     * @return
     *     The ticketNumber
     */
    @JsonProperty("ticketNumber")
    public String getTicketNumber() {
        return ticketNumber;
    }

    /**
     * 
     * @param ticketNumber
     *     The ticketNumber
     */
    @JsonProperty("ticketNumber")
    public void setTicketNumber(String ticketNumber) {
        this.ticketNumber = ticketNumber;
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
        return new HashCodeBuilder().append(accountCode).append(component).append(description).append(liferayVersion).append(lppComponents).append(lppResolution).append(lppTicket).append(status).append(subject).append(supportRegion).append(ticketAssignment).append(ticketClosedDate).append(ticketCreateDate).append(ticketNumber).append(additionalProperties).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof SupportTicket) == false) {
            return false;
        }
        SupportTicket rhs = ((SupportTicket) other);
        return new EqualsBuilder().append(accountCode, rhs.accountCode).append(component, rhs.component).append(description, rhs.description).append(liferayVersion, rhs.liferayVersion).append(lppComponents, rhs.lppComponents).append(lppResolution, rhs.lppResolution).append(lppTicket, rhs.lppTicket).append(status, rhs.status).append(subject, rhs.subject).append(supportRegion, rhs.supportRegion).append(ticketAssignment, rhs.ticketAssignment).append(ticketClosedDate, rhs.ticketClosedDate).append(ticketCreateDate, rhs.ticketCreateDate).append(ticketNumber, rhs.ticketNumber).append(additionalProperties, rhs.additionalProperties).isEquals();
    }

}
