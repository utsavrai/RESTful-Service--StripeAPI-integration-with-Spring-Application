package com.utsav;
import javax.persistence.Id;
import javax.persistence.Entity;

@Entity
public class SSubscription {
	@Id
	public String planId;
	public String getPlanId() {
		return planId;
	}
	public void setPlanId(String planId) {
		this.planId = planId;
	}
	public String getCustId() {
		return custId;
	}
	public void setCustId(String custId) {
		this.custId = custId;
	}
	public String getTrialEnd() {
		return trialEnd;
	}
	public void setTrialEnd(String trialEnd) {
		this.trialEnd = trialEnd;
	}
	public String custId;
	public String trialEnd;
}
