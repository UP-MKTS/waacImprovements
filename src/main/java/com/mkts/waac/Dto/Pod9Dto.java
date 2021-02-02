package com.mkts.waac.Dto;

import lombok.Getter;
import lombok.Setter;

public class Pod9Dto {

    private Integer id;

    private String wasteName;

    private Integer wasteId;

    private Integer wasteCode;

    private String dangerousPowName;

    private String dangerousClassName;

    private String wasteNorm;

    private String activityKindName;

    private String transparentDate;

    private Double wasteGenerate;

    private Double countFromOther;

    private String nameOther;

    private Double countFromPeople;

    private Double countUsed;

    private Double countNeutralized;

    private Double countKeeping;

    private Double wasteWeight;

    private String recipientOrganizationName;

    private String goalName;

    private DepartmentDto department;

    private WasteTypeDto wasteType;

    public Integer getWasteCode() {
        return wasteCode;
    }

    public void setWasteCode(Integer wasteCode) {
        this.wasteCode = wasteCode;
    }

    public String getTransparentDate() {
        return transparentDate!=null?transparentDate:"";
    }

    public void setTransparentDate(String transparentDate) {
        this.transparentDate = transparentDate;
    }

    public Double getWasteGenerate() {
        return wasteGenerate!=null?wasteGenerate:0.0;
    }

    public void setWasteGenerate(Double wasteGenerate) {
        this.wasteGenerate = wasteGenerate;
    }

    public Double getCountFromOther() {
        return countFromOther!=null?countFromOther:0.0;
    }

    public void setCountFromOther(Double countFromOther) {
        this.countFromOther = countFromOther;
    }

    public String getNameOther() {
        return nameOther!=null?nameOther:"";
    }

    public void setNameOther(String nameOther) {
        this.nameOther = nameOther;
    }

    public Double getCountFromPeople() {
        return countFromPeople!=null?countFromPeople:0.0;
    }

    public void setCountFromPeople(Double countFromPeople) {
        this.countFromPeople = countFromPeople;
    }

    public Double getCountUsed() {
        return countUsed!=null?countUsed:0.0;
    }

    public void setCountUsed(Double countUsed) {
        this.countUsed = countUsed;
    }

    public Double getCountNeutralized() {
        return countNeutralized!=null?countNeutralized:0.0;
    }

    public void setCountNeutralized(Double countNeutralized) {
        this.countNeutralized = countNeutralized;
    }

    public Double getCountKeeping() {
        return countKeeping!=null?countKeeping:0.0;
    }

    public void setCountKeeping(Double countKeeping) {
        this.countKeeping = countKeeping;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getWasteName() {
        return wasteName;
    }

    public void setWasteName(String wasteName) {
        this.wasteName = wasteName;
    }

    public Integer getWasteId() {
        return wasteId;
    }

    public void setWasteId(Integer wasterId) {
        this.wasteId = wasterId;
    }

    public String getDangerousPowName() {
        return dangerousPowName;
    }

    public void setDangerousPowName(String dangerousPowName) {
        this.dangerousPowName = dangerousPowName;
    }

    public String getDangerousPowAndClassName(){
        return dangerousClassName+" / "+dangerousPowName;
    }

    public String getDangerousClassName() {
        return dangerousClassName;
    }

    public void setDangerousClassName(String dangerousClassName) {
        this.dangerousClassName = dangerousClassName;
    }

    public String getWasteNorm() {
        return wasteNorm;
    }

    public void setWasteNorm(String wasteNorm) {
        this.wasteNorm = wasteNorm;
    }

    public String getActivityKindName() {
        return activityKindName;
    }

    public void setActivityKindName(String activityKindName) {
        this.activityKindName = activityKindName;
    }

    public Double getWasteWeight() {
        return wasteWeight;
    }

    public void setWasteWeight(Double wasteWeight) {
        this.wasteWeight = wasteWeight;
    }

    public String getRecipientOrganizationName() {
        return recipientOrganizationName;
    }

    public void setRecipientOrganizationName(String recipientOrganizationName) {
        this.recipientOrganizationName = recipientOrganizationName;
    }

    public String getGoalName() {
        return goalName;
    }

    public void setGoalName(String goalName) {
        this.goalName = goalName;
    }
}
