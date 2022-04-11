package hci.compare.rates.service;

import hci.compare.rates.entity.ProductRateDetails;
import org.springframework.web.bind.annotation.RequestParam;

public interface CompareServiceInf {

    ProductRateDetails[] getJavaData(@RequestParam String saleDate, String vin, String dealerCode,
                                     @RequestParam(required = false) String odometer,
                                     @RequestParam(required = false) String vehicleCondition);

    ProductRateDetails[] getMuleData(@RequestParam String saleDate, String vin, String dealerCode,
                                     @RequestParam(required = false) String odometer,
                                     @RequestParam(required = false) String vehicleCondition);

    Object[] getComparedResult(@RequestParam String saleDate, String vin, String dealerCode,
                               @RequestParam(required = false) String odometer,
                               @RequestParam(required = false) String vehicleCondition);
}
