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
		ProductRateDetails[] muleApiData = getMuleApiData(saleDate, vin, dealerCode, odometer, vehicleCondition);

		ArrayList<Object> resultArray = new ArrayList<Object>();
		Map<String, String> responseMap = new TreeMap<>();
		ArrayList<String> allJavaSku = new ArrayList<String>();
		ArrayList<String> allMuleSku = new ArrayList<String>();

		Object[] array = null;
		String productSkuMuleApi = null;
		int i, j = 0;

		for (i = 0; i <= javaApiData.length - 1; i++) {
			String productSkuJavaApi = javaApiData[i].getProductSku();
			allJavaSku.add(javaApiData[i].getProductSku());

			for (j = 0; j <= muleApiData.length - 1; j++) {
				productSkuMuleApi = muleApiData[j].getProductSku();
				if (!allMuleSku.contains(productSkuMuleApi)) {
					allMuleSku.add(muleApiData[j].getProductSku());
				}

				try {
					if (productSkuJavaApi.contentEquals(productSkuMuleApi)) {
						ResultClass result = new ResultClass();
						result.setProductSku_java(javaApiData[i].getProductSku());
						result.setDealerCost_java(javaApiData[i].getDealerCost());
						result.setMultiType_java(javaApiData[i].getMultiType());
						result.setFormNumber_java(javaApiData[i].getFormNumber());
						result.setRetailCost_java(javaApiData[i].getRetailCost());

						result.setProductSku_mule(muleApiData[j].getProductSku());
						result.setDealerCost_mule(muleApiData[j].getDealerCost());
						result.setMultiType_mule(muleApiData[j].getMultiType());
						result.setFormNumber_mule(muleApiData[j].getFormNumber());
						result.setRetailCost_mule(muleApiData[j].getRetailCost());

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
						j = muleApiData.length - 1;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		int javaObjectCount = javaApiData.length;
		int muleObjectCount = muleApiData.length;

		responseMap.put("java ProductRateDetails Count", String.valueOf(javaObjectCount));
		responseMap.put("mule ProductRateDetails Count", String.valueOf(muleObjectCount));

		if (javaObjectCount == muleObjectCount) {
			boolean checkJavaWithMule = allMuleSku.containsAll(allJavaSku);
			boolean checkMuleWithJava = allJavaSku.containsAll(allMuleSku);
			if (checkJavaWithMule == true && checkMuleWithJava == true) {
				responseMap.put("Extra Products: ",
						" No Extra products all the products are matching with each other response..!");
			}
		} else {
			for (int k = 0; k <= allMuleSku.size() - 1; k++) {
				String mule = allMuleSku.get(k);
				if (!allJavaSku.contains(mule)) {
					responseMap.put(mule, " : not matched with any of the productSku of java API response.");
				}
			}
		}
		Set<Entry<String, String>> entrySet = responseMap.entrySet();
		resultArray.addAll(entrySet);
		array = resultArray.toArray();
		return array;
	}

}
