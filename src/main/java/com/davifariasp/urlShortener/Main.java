package com.davifariasp.urlShortener;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;

public class Main implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent input, Context context) {

        UrlShortenerService urlShortenerService = new UrlShortenerService();

        return switch (input.getHttpMethod()) {
            case "POST" -> {
                String body = input.getBody();
                yield urlShortenerService.shortenUrl(body);
            }
            case "GET" -> {
                String urlId = input.getPathParameters().get("urlId");

                yield  urlShortenerService.getUrlOriginal(urlId);
            }
            default -> throw new RuntimeException("Método não suportado");
        };
    }
}