package hci.compare.rates.entity;


public class Data {
	
	private ProductRateDetails[] productRateDetails;

    private VehicleDetails vehicleDetails;

    private DealerDetails dealerDetails;
    
    public ProductRateDetails[] getProductRateDetails() {
		return productRateDetails;
	}

	public void setProductRateDetails(ProductRateDetails[] productRateDetails) {
		this.productRateDetails = productRateDetails;
	}




	@Override
    public String toString()
    {
        return "ClassPojo [productRateDetails = "+productRateDetails+"]";
    }
}
