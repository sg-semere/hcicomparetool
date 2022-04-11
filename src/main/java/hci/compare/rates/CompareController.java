package hci.compare.rates;

import hci.compare.rates.entity.ProductRateDetails;
import hci.compare.rates.service.CompareServiceInf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/comparejson")
public class CompareController {

    private final CompareServiceInf compareServiceInf;

    @Autowired
    public CompareController(CompareServiceInf compareServiceInf) {
        this.compareServiceInf = compareServiceInf;
    }

    @GetMapping("/javaapi")
    public ProductRateDetails[] getJavaApiData(@RequestParam String saleDate, String vin, String dealerCode,
                                               @RequestParam(required = false) String odometer, @RequestParam(required = false) String vehicleCondition) {
        ProductRateDetails[] javaData = compareServiceInf.getJavaData(saleDate, vin, dealerCode, odometer, vehicleCondition);
        return javaData;
    }

    @GetMapping("/muleapi")
    public ProductRateDetails[] getMuleApiData(@RequestParam String saleDate, String vin, String dealerCode,
                                               @RequestParam(required = false) String odometer, @RequestParam(required = false) String vehicleCondition) {
        ProductRateDetails[] muleData = compareServiceInf.getMuleData(saleDate, vin, dealerCode, odometer, vehicleCondition);
        return muleData;
    }

    @GetMapping("/compare")
	public Object[] getCompare(@RequestParam String saleDate, String vin, String dealerCode,
			@RequestParam(required = false) String odometer, @RequestParam(required = false) String vehicleCondition) {

        Object[] comparedResult = compareServiceInf.getComparedResult(saleDate, vin, dealerCode, odometer, vehicleCondition);
        return comparedResult;
	}

}
