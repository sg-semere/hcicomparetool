package hci.compare.rates;

import hci.compare.rates.result.ResultWithResponse;
import hci.compare.rates.service.CompareServiceInf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/comparejson")
public class CompareController {

//    static Gson gson = new Gson();

    @Autowired
    private CompareServiceInf compareServiceInf;

    @GetMapping("/javaapi")
    public ResultWithResponse getJavaApiData(@RequestParam String saleDate, String vin, String dealerCode,
                                             @RequestParam(required = false) String odometer, @RequestParam(required = false) String vehicleCondition) {
        ResultWithResponse javaData = compareServiceInf.getJavaData(saleDate, vin, dealerCode, odometer, vehicleCondition);
        return javaData;
    }

    @GetMapping("/muleapi")
    public ResultWithResponse getMuleApiData(@RequestParam String saleDate, String vin, String dealerCode,
                                             @RequestParam(required = false) String odometer, @RequestParam(required = false) String vehicleCondition) {
        ResultWithResponse muleData = compareServiceInf.getMuleData(saleDate, vin, dealerCode, odometer, vehicleCondition);
        return muleData;
    }

    @GetMapping("/compare")
    public ResultWithResponse getCompare(@RequestParam String saleDate, String vin, String dealerCode,
                                         @RequestParam(required = false) String odometer, @RequestParam(required = false) String vehicleCondition) {

        ResultWithResponse comparedResult = null;
        try {
            comparedResult = compareServiceInf.getComparedResult(saleDate, vin, dealerCode, odometer, vehicleCondition);
            if (comparedResult.getResponseCode().contentEquals("200"))
                return comparedResult;

        } catch (Exception e) {
            e.printStackTrace();
            comparedResult.setResponseCode("500");
            comparedResult.setResponseStatus("Internal server Error");
            return comparedResult;
        }
        return comparedResult;
    }
}





