package hci.compare.rates;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

import hci.compare.rates.entity.ProductRateDetails;
import hci.compare.rates.entity.RatesResponseTemplet;
import hci.compare.rates.result.ResultClass;
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

    @Value("${mule-api.url}")
    private String muleAPIUrl;

    @Value("${mule-api.context-path}")
    private String muleAPIContextPath;

    @Value("${java-api.url}")
    private String javaAPIUrl;

    @Value("${java-api.context-path}")
    private String javaAPIContextPath;

    @Autowired
    private RestTemplate restTemplate;

//    static Gson gson = new Gson();

    @GetMapping("/javaapi")
    public ProductRateDetails[] getJavaApiData(@RequestParam String saleDate, String vin, String dealerCode,
                                               @RequestParam(required = false) String odometer, @RequestParam(required = false) String vehicleCondition) {

        String urlJava = javaAPIUrl + javaAPIContextPath + "?saleDate={saleDate}&vin={vin}&dealer={dealerCode}";

        if (odometer != null)
            urlJava += "&odometer={odometer}";
        if (vehicleCondition != null)
            urlJava += "&vehicleCondition={vehicleCondition}";

        RatesResponseTemplet JavaApiDatainJavaObj = restTemplate.getForObject(urlJava, RatesResponseTemplet.class, saleDate,
                vin, dealerCode, odometer, vehicleCondition);

        return JavaApiDatainJavaObj.getData().getProductRateDetails();
    }

    @GetMapping("/muleapi")
    public ProductRateDetails[] getMuleApiData(@RequestParam String saleDate, String vin, String dealerCode,
                                               @RequestParam(required = false) String odometer, @RequestParam(required = false) String vehicleCondition) {

        String urlMule = muleAPIUrl + muleAPIContextPath + "?saleDate={saleDate}&vin={vin}&dealerCode={dealerCode}";

        if (odometer != null)
            urlMule += "&odometer={odometer}";
        if (vehicleCondition != null)
            urlMule += "&vehicleCondition={vehicleCondition}";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Client_id", "5b0c387efa6942a297808086528e3393");
        headers.set("Client_secret", "bf1F8E59d74E4c0FB5D85F8AbC4C150F");
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<String>(headers);
        ResponseEntity<RatesResponseTemplet> exchange = restTemplate.exchange(urlMule, HttpMethod.GET, entity,
                RatesResponseTemplet.class, saleDate, vin, dealerCode, odometer, vehicleCondition);
        ProductRateDetails[] productRateDetails = exchange.getBody().getData().getProductRateDetails();

        return productRateDetails;
    }

    @GetMapping("/compare")
    public Object[] getCompare(@RequestParam String saleDate, String vin, String dealerCode,
                               @RequestParam(required = false) String odometer, @RequestParam(required = false) String vehicleCondition) {

        ProductRateDetails[] javaApiData = getJavaApiData(saleDate, vin, dealerCode, odometer, vehicleCondition);
        ProductRateDetails[] muleApi = getMuleApiData(saleDate, vin, dealerCode, odometer, vehicleCondition);

        Set<String> allJavaSku = new TreeSet<String>();
        Set<String> allMuleSku = new TreeSet<String>();
        ArrayList<ResultClass> resultArray = new ArrayList<>();

        Object[] array = null;

        for (int i = 0; i <= javaApiData.length - 1; i++) {
            String productSkuJavaApi = javaApiData[i].getProductSku();
            allJavaSku.add(productSkuJavaApi);

            for (int j = 0; j <= muleApi.length - 1; j++) {
                String productSkuMuleApi = muleApi[j].getProductSku();
                allMuleSku.add(productSkuMuleApi);
                try {
                    if (productSkuJavaApi.contentEquals(productSkuMuleApi)) {
                        ResultClass result = new ResultClass();
                        result.setProductSku_java(javaApiData[i].getProductSku());
                        result.setDealerCost_java(javaApiData[i].getDealerCost());
                        result.setMultiType_java(javaApiData[i].getMultiType());
                        result.setFormNumber_java(javaApiData[i].getFormNumber());
                        result.setRetailCost_java(javaApiData[i].getRetailCost());

                        result.setProductSku_mule(muleApi[j].getProductSku());
                        result.setDealerCost_mule(muleApi[j].getDealerCost());
                        result.setMultiType_mule(muleApi[j].getMultiType());
                        result.setFormNumber_mule(muleApi[j].getFormNumber());
                        result.setRetailCost_mule(muleApi[j].getRetailCost());

                        result.setProductSku(result.getProductSku_java().contentEquals(result.getProductSku_mule()));
                        result.setDealerCost(
                                String.valueOf(Math.abs(Double.parseDouble(result.getDealerCost_java()))).contentEquals(
                                        String.valueOf(Math.abs(Double.parseDouble(result.getDealerCost_mule())))));
                        result.setMultiType(result.getMultiType_java().contentEquals(result.getMultiType_mule()));
                        result.setFormNumber(result.getFormNumber_java().contentEquals(result.getFormNumber_mule()));
                        result.setRetailCost(
                                String.valueOf(Math.abs(Double.parseDouble(result.getRetailCost_java()))).contentEquals(
                                        String.valueOf(Math.abs(Double.parseDouble(result.getRetailCost_mule())))));

                        resultArray.add(result);
                        j = muleApi.length - 1;
                    } else {
                        ResultClass result = new ResultClass();
                        result.setProductSku_mule(productSkuMuleApi);
                        result.setNot_Matched(productSkuMuleApi + " not matched with Java API");
                        resultArray.add(result);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        array = resultArray.toArray();
        return array;
    }

}
