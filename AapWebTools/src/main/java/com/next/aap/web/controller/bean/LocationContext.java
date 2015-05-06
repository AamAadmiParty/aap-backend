package com.next.aap.web.controller.bean;

public class LocationContext {

    private long livingAcId = 0;
    private long votingAcId = 0;
    private long livingPcId = 0;
    private long votingPcId = 0;
    private long nriCountryId = 0;
    private long nriCountryRegionId = 0;
    private long nriCountryRegionAreaId = 0;
    private long domainStateId = 0;

    public long getLivingAcId() {
        return livingAcId;
    }

    public void setLivingAcId(long livingAcId) {
        this.livingAcId = livingAcId;
    }

    public long getVotingAcId() {
        return votingAcId;
    }

    public void setVotingAcId(long votingAcId) {
        this.votingAcId = votingAcId;
    }

    public long getLivingPcId() {
        return livingPcId;
    }

    public void setLivingPcId(long livingPcId) {
        this.livingPcId = livingPcId;
    }

    public long getVotingPcId() {
        return votingPcId;
    }

    public void setVotingPcId(long votingPcId) {
        this.votingPcId = votingPcId;
    }

    public long getNriCountryId() {
        return nriCountryId;
    }

    public void setNriCountryId(long nriCountryId) {
        this.nriCountryId = nriCountryId;
    }

    public long getNriCountryRegionId() {
        return nriCountryRegionId;
    }

    public void setNriCountryRegionId(long nriCountryRegionId) {
        this.nriCountryRegionId = nriCountryRegionId;
    }

    public long getNriCountryRegionAreaId() {
        return nriCountryRegionAreaId;
    }

    public void setNriCountryRegionAreaId(long nriCountryRegionAreaId) {
        this.nriCountryRegionAreaId = nriCountryRegionAreaId;
    }

    public long getDomainStateId() {
        return domainStateId;
    }

    public void setDomainStateId(long domainStateId) {
        this.domainStateId = domainStateId;
    }
}
