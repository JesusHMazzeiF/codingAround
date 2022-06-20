package com.jmazzei.restcaller.restcaller.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Collections;
import java.util.List;

//@Component
public class RestCaller {

    public static void main(String[] args) {
        String baseUrl = "orbit/api/bill/";
//        String baseUrl = "http://backend17.ogangi.com:9020/orbit/api/topup/";
        String account = "18765422032";
        String amount = "1.0";
        String callbackUrl = "null";
        String hints = "null";
        String privateKey = "7a551de3-44c3-49b4-9ba5-c565b0c4d04a";
        String publicKey = "c096e3bd-0100-11f8-cb89-0fr5f89f718a";
        callOrbit(baseUrl, account, amount, callbackUrl, hints, privateKey, publicKey);
    }

    @Autowired
    private Environment env;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    private static String sign(String algorithm, String input, byte[] key) throws NoSuchAlgorithmException, InvalidKeyException, IllegalStateException, UnsupportedEncodingException {
        Mac mac = Mac.getInstance(algorithm);
        mac.init(new SecretKeySpec(key, algorithm));
        return DatatypeConverter.printHexBinary(mac.doFinal(input.getBytes("UTF-8"))).toLowerCase();
    }


    public <T> ResponseEntity<T> callServiceJson(String url, HttpMethod method, Object body, String bearer,
                                                 Class<T> clazz) throws JsonProcessingException, RestClientException {

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, bearer);
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<String> entity;

        if (body != null) {
            entity = new HttpEntity<>(objectMapper.writeValueAsString(body), headers);
        } else {
            entity = new HttpEntity<>(headers);
        }

        return restTemplate.exchange(url, method, entity, clazz);

    }

    public static String encodeB64URLSafe(String data) {
        return new String(Base64.getUrlEncoder().encode(data.getBytes(StandardCharsets.UTF_8)),
                          StandardCharsets.UTF_8);
    }

    public static String decodeB64URLSafe(String data) {
        return data.equalsIgnoreCase("null") ? "null" : new String(Base64.getUrlDecoder().decode(
                data.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
    }

    public static String getFormattedAmount(float amount, String currencySymbol) {
        if (currencySymbol.startsWith("_")) {
            return String.format("%s%.2f", currencySymbol.substring(1), amount);
        } else {
            return String.format("%.2f %s", amount, currencySymbol);
        }
    }

    public static boolean hintsContains(String seek, String... hints) {

        for (String hint: hints) {
            if (hint.toLowerCase().contains(seek)) {
                return true;
            }
        }

        return false;

    }

    private static void callOrbit(String endpoint, String account, String amount, String callbackUrl, String hints,
                                  String privateKey, String publicKey){

        try{
            String baseUrl = "https://reycreo.messangi.me/";
            long timestamp = System.currentTimeMillis();

            RestTemplate restTemplate = new RestTemplate();
            StringBuilder billUrl = new StringBuilder();
            billUrl.append(endpoint);
            billUrl.append(account);
            billUrl.append("/");
            billUrl.append(amount);
            billUrl.append("/");
            billUrl.append(RestCaller.encodeB64URLSafe(callbackUrl));
            billUrl.append("/");
            billUrl.append(RestCaller.encodeB64URLSafe("null"));
            billUrl.append("/");
            billUrl.append(RestCaller.encodeB64URLSafe(hints.toString()));

            System.out.println(billUrl);
            System.out.println("Timestamp: " + timestamp);
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("timestamp", String.valueOf(timestamp));
            headers.add("publickey", publicKey);
            headers.add("signature", sign("HmacSHA512", billUrl + "/" + timestamp,
                                          privateKey.getBytes()));

            //put the headers
            HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

            ResponseEntity<String> responseEntity = restTemplate.exchange(baseUrl + billUrl, HttpMethod.GET, entity, String.class);

            System.out.println("Response: " + responseEntity.getBody());

        } catch (Exception e){
            e.printStackTrace();
        }

    }

}
