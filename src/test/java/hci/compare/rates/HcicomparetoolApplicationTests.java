package hci.compare.rates;

import hci.compare.rates.entity.ProductRateDetails;
import hci.compare.rates.entity.RatesResponseTemplet;
import hci.compare.rates.result.ResultClass;
import hci.compare.rates.service.CompareServiceInf;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.client.RestTemplate;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class HcicomparetoolApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void contextLoads() {
	}

	@Test
	public void testJavaApi() throws Exception {
		String url = "/comparejson/javaapi?saleDate=2021-09-12&dealerCode=HYUTX155&vin=1FM5K8GT6EGC35575&odometer=1000&vehicleCondition=NEW";
		MvcResult result = this.mockMvc.perform(get(url)).andExpect(status().isOk()).andReturn();
//		System.out.println(result.getResponse().getContentAsString());
		assertEquals(true, result.getResponse().getContentAsString().contains("\"responseCode\":\"200\""));
	}

	@Test
	public void testMuleApi() throws Exception {
		String url = "/comparejson/muleapi?saleDate=2021-09-12&dealerCode=HYUTX155&vin=1FM5K8GT6EGC35575&odometer=1000&vehicleCondition=NEW";
		MvcResult result = this.mockMvc.perform(get(url)).andExpect(status().isOk()).andReturn();
//		System.out.println(result.getResponse().getContentAsString());
		assertEquals(true, result.getResponse().getContentAsString().contains("\"responseCode\":\"200\""));
	}

	@Test
	public void testCompareApi() throws Exception {
		String url = "/comparejson/compare?saleDate=2021-09-12&dealerCode=HYUTX155&vin=1FM5K8GT6EGC35575&odometer=1000&vehicleCondition=NEW";
		MvcResult result = this.mockMvc.perform(get(url)).andExpect(status().isOk()).andReturn();
//		System.out.println(result.getResponse().getContentAsString());
		assertEquals(true, result.getResponse().getContentAsString().contains("\"responseCode\":\"200\""));
	}

	@Test
	public void testCompareApiInternalServerError() throws Exception {
		String url = "/comparejson/compare?saleDate=2021-09-12&dealerCode=HKUYI155&vin=1FM5K8GT6EGC35575&odometer=1000&vehicleCondition=NEW";
		MvcResult result = this.mockMvc.perform(get(url)).andExpect(status().isOk()).andReturn();
		System.out.println(result.getResponse().getContentAsString());
		assertEquals(true, result.getResponse().getContentAsString().contains("\"responseCode\":\"500\""));
	}

	@Test
	public void testCompareApiDataNotFound() throws Exception {
		String url = "/comparejson/compare?saleDate=2021-09-12&dealerCode=null&vin=1FM5K8GT6EGC35575&odometer=1000&vehicleCondition=NEW";
		MvcResult result = this.mockMvc.perform(get(url)).andExpect(status().isOk()).andReturn();
		System.out.println(result.getResponse().getContentAsString());
		assertEquals(true, result.getResponse().getContentAsString().contains("\"responseCode\":\"500\""));
	}










}
