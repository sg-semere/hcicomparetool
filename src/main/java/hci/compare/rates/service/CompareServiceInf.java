package hci.compare.rates.service;

import hci.compare.rates.entity.ProductRateDetails;
import org.springframework.web.bind.annotation.RequestParam;

public interface CompareServiceInf {

    public ProductRateDetails[] getJavaData(@RequestParam String saleDate, String vin, String dealerCode,
                                            @RequestParam(required = false) String odometer,
                                            @RequestParam(required = false) String vehicleCondition);

    public ProductRateDetails[] getMuleData(@RequestParam String saleDate, String vin, String dealerCode,
                                            @RequestParam(required = false) String odometer,
                                            @RequestParam(required = false) String vehicleCondition);

    public Object[] getComparedResult(@RequestParam String saleDate, String vin, String dealerCode,
                                        @RequestParam(required = false) String odometer,
                                        @RequestParam(required = false) String vehicleCondition);
    }
