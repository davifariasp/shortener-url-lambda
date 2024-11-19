package com.davifariasp.urlShortener;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import java.util.Map;
import java.util.UUID;

public class UrlShortenerService {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final S3Client s3Client = S3Client.builder().build();

    public APIGatewayProxyResponseEvent shortenUrl(String body){

        Map<String, String> bodyMap;

        try {
            //desserializa o JSON
            bodyMap = objectMapper.readValue(body, Map.class);
        } catch (Exception e) {
            throw new RuntimeException("Error parsing JSON: " + e.getMessage(), e);
        }

        String originalUrl = bodyMap.get("originalUrl");
        String expirationTime = bodyMap.get("expirationTime");

        String shortUrlCode = UUID.randomUUID().toString().substring(0, 8);

        UrlData urlData = new UrlData(originalUrl, Long.parseLong(expirationTime));

        try {
            String urlDataJson = objectMapper.writeValueAsString(urlData);

            PutObjectRequest request = PutObjectRequest.builder()
                    .bucket("bucket-shortener-url-lambda")
                    .key(shortUrlCode + ".json")
                    .build();

            s3Client.putObject(request, RequestBody.fromString(urlDataJson));

        } catch (Exception e){
            throw new RuntimeException("Error saving URL data to S3: " + e.getMessage(), e);
        }

        return new APIGatewayProxyResponseEvent().withStatusCode(200).withBody(shortUrlCode);
    }

    public APIGatewayProxyResponseEvent getUrlOriginal(String urlId){

        GetObjectRequest request = GetObjectRequest.builder()
                .bucket("bucket-shortener-url-lambda")
                .key(urlId + ".json")
                .build();

        GetObjectResponse response = s3Client.getObject(request).response();

        String urlDataJson = response.contentDisposition();

        try {
            UrlData urlData = objectMapper.readValue(urlDataJson, UrlData.class);
            return new APIGatewayProxyResponseEvent().withBody(urlData.getOriginalUrl());
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error parsing JSON: " + e.getMessage(), e);
        }
    }
}
