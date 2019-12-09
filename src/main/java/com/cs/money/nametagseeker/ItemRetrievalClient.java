package com.cs.money.nametagseeker;

import java.util.Arrays;

import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ItemRetrievalClient {
	
	private static final String URL_CS_MONEY = "https://cs.money/730/load_bots_inventory";
	private static final String USER_AGENT_KEY = "user-agent";
	private static final String USER_AGENT_VALUE = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36";

	public ResponseEntity<Item[]> getItems() {
		RestTemplate rt = buildRestTemplate();
		HttpHeaders headers = buildHeaders(rt);
		log.info("Retrieving items");
		ResponseEntity<Item[]> listItems = rt.getForEntity(URL_CS_MONEY, Item[].class, headers);
		log.info("Retrieved");
		return listItems;
	}

	private static HttpHeaders buildHeaders(RestTemplate rt) {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.add(USER_AGENT_KEY,	USER_AGENT_VALUE);
		return headers;
	}

	private static RestTemplate buildRestTemplate() {
		CloseableHttpClient httpClient = HttpClients.custom().setSSLHostnameVerifier(new NoopHostnameVerifier()).build();
		HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
		requestFactory.setHttpClient(httpClient);
		return new RestTemplate(requestFactory);
	}
}