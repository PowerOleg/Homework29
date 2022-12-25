package ru.netology.http;

import org.apache.http.HttpHeaders;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.HttpClientBuilder;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class CloseableHttpClient {
    public static final String REMOTE_URI = "https://raw.githubusercontent.com/netology-code/jd-homeworks/master/http/task1/cats";
    public static final ObjectMapper jsonMapper = new ObjectMapper();
    public static void main(String[] args) {
        org.apache.http.impl.client.CloseableHttpClient httpClient = HttpClientBuilder.create()
                .setUserAgent("Yandex/22.11.3.818 (64-bit)")
                .setDefaultRequestConfig(RequestConfig.custom()
                .setConnectTimeout(5000)
                        .setSocketTimeout(30000)
                        .setRedirectsEnabled(false)
                        .build()).build();

        HttpGet request = new HttpGet(REMOTE_URI);
        request.setHeader(HttpHeaders.ACCEPT, ContentType.APPLICATION_JSON.getMimeType());
        request.addHeader(HttpHeaders.ACCEPT_ENCODING, "gzip");
        request.addHeader(HttpHeaders.ACCEPT_LANGUAGE, "en");
//        httpURLConnection.addRequestProperty("Content-Type", "application/x-www-form-urlencoded");

        try (CloseableHttpResponse response = httpClient.execute(request)) {
            Arrays.stream(response.getAllHeaders()).forEach(n -> System.out.println(n));

            List<Cat> cats = jsonMapper.readValue(response.getEntity().getContent(), new TypeReference<List<Cat>>() {});
            cats.forEach(n -> System.out.println(n));


        } catch (ClientProtocolException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}
