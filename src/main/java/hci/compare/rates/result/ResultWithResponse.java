package hci.compare.rates.result;

import hci.compare.rates.entity.ProductRateDetails;

import java.util.function.Supplier;

public class ResultWithResponse {

    private String responseCode;
    private String responseStatus;
    private ProductRateDetails[] productRateDetails;
    private ResultClass resultClass;
    private Object[] comparedResult;

    public Object[] getComparedResult() {
        return comparedResult;
    }

    public void setComparedResult(Object[] comparedResult) {
        this.comparedResult = comparedResult;
    }

    public ResultClass getResultClass() {
        return resultClass;
    }

    public void setResultClass(ResultClass resultClass) {
        this.resultClass = resultClass;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(String responseStatus) {
        this.responseStatus = responseStatus;
    }

    public ProductRateDetails[] getProductRateDetails() {
        return productRateDetails;
    }

    public void setProductRateDetails(ProductRateDetails[] productRateDetails) {
        this.productRateDetails = productRateDetails;
    }

}
