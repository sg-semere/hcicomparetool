package hci.compare.rates.service;

import hci.compare.rates.entity.ProductRateDetails;
import hci.compare.rates.entity.RatesResponseTemplet;
import hci.compare.rates.result.ResultClass;
import hci.compare.rates.result.ResultWithResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
public class CompareServiceImp implements CompareServiceInf {

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

    @Override
    public ResultWithResponse getJavaData(String saleDate, String vin, String dealerCode, String odometer, String vehicleCondition) {

        String urlJava = javaAPIUrl + javaAPIContextPath + "?saleDate={saleDate}&vin={vin}&dealer={dealerCode}";

        if (odometer != null)
            urlJava += "&odometer={odometer}";
        if (vehicleCondition != null)
            urlJava += "&vehicleCondition={vehicleCondition}";

        ResponseEntity<RatesResponseTemplet> javaResponse = restTemplate.getForEntity(urlJava, RatesResponseTemplet.class, saleDate,
                vin, dealerCode, odometer, vehicleCondition);

        ResultWithResponse withResponse = new ResultWithResponse();
        withResponse.setResponseCode(javaResponse.getBody().getResponseCode());
        withResponse.setResponseStatus(javaResponse.getBody().getStatus());
        withResponse.setProductRateDetails(javaResponse.getBody().getData().getProductRateDetails());

        return withResponse;
    }

    @Override
    public ResultWithResponse getMuleData(String saleDate, String vin, String dealerCode, String odometer, String vehicleCondition) {

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
        ResponseEntity<RatesResponseTemplet> muleResponse = restTemplate.exchange(urlMule, HttpMethod.GET, entity,
                RatesResponseTemplet.class, saleDate, vin, dealerCode, odometer, vehicleCondition);

        ResultWithResponse withResponse = new ResultWithResponse();
        withResponse.setResponseCode(muleResponse.getBody().getResponseCode());
        withResponse.setResponseStatus(muleResponse.getBody().getStatus());
        withResponse.setProductRateDetails(muleResponse.getBody().getData().getProductRateDetails());

        return withResponse;
    }

    @Override
    public ResultWithResponse getComparedResult(String saleDate, String vin, String dealerCode, String odometer, String vehicleCondition) {
              ResultWithResponse withResponse = new ResultWithResponse();
        try {
            ResultWithResponse javaApiData = getJavaData(saleDate, vin, dealerCode, odometer, vehicleCondition);
            ResultWithResponse muleApiData = getMuleData(saleDate, vin, dealerCode, odometer, vehicleCondition);

            ArrayList<Object> resultArray = new ArrayList<Object>();
            Map<String, String> responseMap = new TreeMap<>();
            ArrayList<String> allJavaSku = new ArrayList<String>();
            ArrayList<String> allMuleSku = new ArrayList<String>();

            ProductRateDetails[] array = null;
            String productSkuMuleApi = null;

            String responseCodeJava = javaApiData.getResponseCode();
            String responseCodeMule = muleApiData.getResponseCode();

            if (responseCodeJava.contentEquals(responseCodeMule)) {
                withResponse.setResponseCode(javaApiData.getResponseCode());
                withResponse.setResponseStatus(javaApiData.getResponseStatus());
            } else {
                if (Integer.valueOf(responseCodeJava) > Integer.valueOf(responseCodeMule)) {
                    withResponse.setResponseCode(javaApiData.getResponseCode());
                    withResponse.setResponseStatus(javaApiData.getResponseStatus());
                } else {
                    withResponse.setResponseCode(muleApiData.getResponseCode());
                    withResponse.setResponseStatus(muleApiData.getResponseStatus());
                }
            }

            ProductRateDetails[] productRateDetailsJava = javaApiData.getProductRateDetails();
            ProductRateDetails[] productRateDetailsMule = muleApiData.getProductRateDetails();

            for (int i = 0; i <= productRateDetailsJava.length - 1; i++) {
                String productSkuJavaApi = productRateDetailsJava[i].getProductSku();
                allJavaSku.add(productRateDetailsJava[i].getProductSku());

                for (int j = 0; j <= productRateDetailsMule.length - 1; j++) {
                    productSkuMuleApi = productRateDetailsMule[j].getProductSku();
                    if (!allMuleSku.contains(productSkuMuleApi))
                        allMuleSku.add(productRateDetailsMule[j].getProductSku());

                    if (productSkuJavaApi.contentEquals(productSkuMuleApi)) {
                        ResultClass result = new ResultClass();
                        result.setProductSku_java(productRateDetailsJava[i].getProductSku());
                        result.setDealerCost_java(productRateDetailsJava[i].getDealerCost());
                        result.setMultiType_java(productRateDetailsJava[i].getMultiType());
                        result.setFormNumber_java(productRateDetailsJava[i].getFormNumber());
                        result.setRetailCost_java(productRateDetailsJava[i].getRetailCost());

                        result.setProductSku_mule(productRateDetailsMule[j].getProductSku());
                        result.setDealerCost_mule(productRateDetailsMule[j].getDealerCost());
                        result.setMultiType_mule(productRateDetailsMule[j].getMultiType());
                        result.setFormNumber_mule(productRateDetailsMule[j].getFormNumber());
                        result.setRetailCost_mule(productRateDetailsMule[j].getRetailCost());

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
                        j = productRateDetailsMule.length - 1;
                    }

                }
            }
            int javaObjectCount = productRateDetailsJava.length;
            int muleObjectCount = productRateDetailsMule.length;

            responseMap.put("Java ProductRateDetails Count", String.valueOf(javaObjectCount));
            responseMap.put("Mule ProductRateDetails Count", String.valueOf(muleObjectCount));

            if (javaObjectCount == muleObjectCount) {
                boolean checkJavaWithMule = allMuleSku.containsAll(allJavaSku);
                boolean checkMuleWithJava = allJavaSku.containsAll(allMuleSku);
                if (checkJavaWithMule == true && checkMuleWithJava == true)
                    responseMap.put("Extra Products ",
                            "No Extra products all the products are matching with each other response..!");
            } else {
                if (javaObjectCount > muleObjectCount) {
                    for (int k = 0; k <= allJavaSku.size() - 1; k++) {
                        String java = allJavaSku.get(k);
                        if (!allMuleSku.contains(java))
                            responseMap.put(java, "not matched with any of the productSku of mule API response.");
                    }
                } else {
                    for (int k = 0; k <= allMuleSku.size() - 1; k++) {
                        String mule = allMuleSku.get(k);
                        if (!allJavaSku.contains(mule))
                            responseMap.put(mule, "not matched with any of the productSku of java API response.");
                    }
                }
            }
            Set<Map.Entry<String, String>> entrySet = responseMap.entrySet();
            resultArray.addAll(entrySet);
            Object[] objects = resultArray.toArray();
            withResponse.setComparedResult(objects);
            return withResponse;
        } catch (Exception e) {
            e.printStackTrace();
            return withResponse;
        }
    }

}
