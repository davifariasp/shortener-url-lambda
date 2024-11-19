package com.davifariasp.urlShortener;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import software.amazon.awssdk.services.s3.S3Client;

import java.util.HashMap;
import java.util.Map;

public class GetUrlOriginal implements RequestHandler<Map<String, Object>, Map<String, String>> {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final S3Client s3Client = S3Client.builder().build();

    @Override
    public Map<String, String> handleRequest(Map<String, Object> input, Context context) {

        String params = input.get("queryStringParameters").toString().replace("=", ":").replaceAll("([a-zA-Z0-9_]+)", "\"$1\"");

        Map<String, String> paramsMap;

        try {
            paramsMap = objectMapper.readValue(params, Map.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        String uuid = paramsMap.get("uuid");

        if (uuid.isEmpty()) {
            throw new RuntimeException("Missing required parameter 'uuid'");
        }

        Map<String, String> response = new HashMap<>();
        response.put("uuid", uuid);

        return response;
    }
}
