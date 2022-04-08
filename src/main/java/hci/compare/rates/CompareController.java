package hci.compare.rates;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Map.Entry;

import hci.compare.rates.entity.ProductRateDetails;
import hci.compare.rates.entity.RatesResponseTemplet;
import hci.compare.rates.result.ResultClass;
import hci.compare.rates.service.CompareServiceInf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/comparejson")
public class CompareController {

//    static Gson gson = new Gson();

    @Autowired
    private CompareServiceInf compareServiceInf;

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
