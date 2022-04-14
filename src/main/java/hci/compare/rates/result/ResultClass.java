package hci.compare.rates.result;

import java.util.Set;

public class ResultClass {

	private String productSku_java;
	private String productSku_mule;
	private boolean productSku;
	
	 private String dealerCost_java;
	 private String dealerCost_mule;
	 private boolean dealerCost;
	 
	 private String multiType_java;
	 private String multiType_mule;
	 private boolean multiType;
	 
	 private String formNumber_java;
	 private String formNumber_mule;
	 private boolean formNumber;
	 
	 private String retailCost_java;
	 private String retailCost_mule;
	 private boolean retailCost;

	public String getProductSku_java() {
		return productSku_java;
	}
	public void setProductSku_java(String productSku_java) {
		this.productSku_java = productSku_java;
	}
	public String getProductSku_mule() {
		return productSku_mule;
	}
	public void setProductSku_mule(String productSku_mule) {
		this.productSku_mule = productSku_mule;
	}
	public boolean isProductSku() {
		return productSku;
	}
	public void setProductSku(boolean productSku) {
		this.productSku = productSku;
	}
	public String getDealerCost_java() {
		return dealerCost_java;
	}
	public void setDealerCost_java(String dealerCost_java) {
		this.dealerCost_java = dealerCost_java;
	}
	public String getDealerCost_mule() {
		return dealerCost_mule;
	}
	public void setDealerCost_mule(String dealerCost_mule) {
		this.dealerCost_mule = dealerCost_mule;
	}
	public boolean isDealerCost() {
		return dealerCost;
	}
	public void setDealerCost(boolean dealerCost) {
		this.dealerCost = dealerCost;
	}
	public String getMultiType_java() {
		return multiType_java;
	}
	public void setMultiType_java(String multiType_java) {
		this.multiType_java = multiType_java;
	}
	public String getMultiType_mule() {
		return multiType_mule;
	}
	public void setMultiType_mule(String multiType_mule) {
		this.multiType_mule = multiType_mule;
	}
	public boolean isMultiType() {
		return multiType;
	}
	public void setMultiType(boolean multiType) {
		this.multiType = multiType;
	}
	public String getFormNumber_java() {
		return formNumber_java;
	}
	public void setFormNumber_java(String formNumber_java) {
		this.formNumber_java = formNumber_java;
	}
	public String getFormNumber_mule() {
		return formNumber_mule;
	}
	public void setFormNumber_mule(String formNumber_mule) {
		this.formNumber_mule = formNumber_mule;
	}
	public boolean isFormNumber() {
		return formNumber;
	}
	public void setFormNumber(boolean formNumber) {
		this.formNumber = formNumber;
	}
	public String getRetailCost_java() {
		return retailCost_java;
	}
	public void setRetailCost_java(String retailCost_java) {
		this.retailCost_java = retailCost_java;
	}
	public String getRetailCost_mule() {
		return retailCost_mule;
	}
	public void setRetailCost_mule(String retailCost_mule) {
		this.retailCost_mule = retailCost_mule;
	}
	public boolean isRetailCost() {
		return retailCost;
	}
	public void setRetailCost(boolean retailCost) {
		this.retailCost = retailCost;
	}
	
	@Override
	public String toString() {
		return "ResultClass [productSku_java=" + productSku_java + ", productSku_mule=" + productSku_mule
				+ ", productSku=" + productSku + ", dealerCost_java=" + dealerCost_java + ", dealerCost_mule="
				+ dealerCost_mule + ", dealerCost=" + dealerCost + ", multiType_java=" + multiType_java
				+ ", multiType_mule=" + multiType_mule + ", multiType=" + multiType + ", formNumber_java="
				+ formNumber_java + ", formNumber_mule=" + formNumber_mule + ", formNumber=" + formNumber
				+ ", retailCost_java=" + retailCost_java + ", retailCost_mule=" + retailCost_mule + ", retailCost="
				+ retailCost + ", getProductSku_java()=" + getProductSku_java() + ", getProductSku_mule()="
				+ getProductSku_mule() + ", isProductSku()=" + isProductSku() + ", getDealerCost_java()="
				+ getDealerCost_java() + ", getDealerCost_mule()=" + getDealerCost_mule() + ", isDealerCost()="
				+ isDealerCost() + ", getMultiType_java()=" + getMultiType_java() + ", getMultiType_mule()="
				+ getMultiType_mule() + ", isMultiType()=" + isMultiType() + ", getFormNumber_java()="
				+ getFormNumber_java() + ", getFormNumber_mule()=" + getFormNumber_mule() + ", isFormNumber()="
				+ isFormNumber() + ", getRetailCost_java()=" + getRetailCost_java() + ", getRetailCost_mule()="
				+ getRetailCost_mule() + ", isRetailCost()=" + isRetailCost() + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}
}
