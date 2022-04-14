package hci.compare.rates.service;

import hci.compare.rates.entity.ProductRateDetails;
import hci.compare.rates.result.ResultWithResponse;
import org.springframework.web.bind.annotation.RequestParam;

public interface CompareServiceInf {

    public ResultWithResponse getJavaData(@RequestParam String saleDate, String vin, String dealerCode,
                                          @RequestParam(required = false) String odometer,
                                          @RequestParam(required = false) String vehicleCondition);

    public ResultWithResponse getMuleData(@RequestParam String saleDate, String vin, String dealerCode,
                                            @RequestParam(required = false) String odometer,
                                            @RequestParam(required = false) String vehicleCondition);

    public ResultWithResponse getComparedResult(@RequestParam String saleDate, String vin, String dealerCode,
                                        @RequestParam(required = false) String odometer,
                                        @RequestParam(required = false) String vehicleCondition);
    }
