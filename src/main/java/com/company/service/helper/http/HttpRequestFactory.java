package com.company.service.helper.http;

import java.io.IOException;
import java.net.http.HttpResponse;

public interface HttpRequestFactory {
    HttpResponse<String> sendTokenGetRequest(String uri, String token)
            throws IOException, InterruptedException;
    HttpResponse<String> sendGetRequest(String uri)
            throws IOException, InterruptedException;
    HttpResponse<String> sendTokenPostRequest(String uri, String req, String token)
            throws IOException, InterruptedException;
    HttpResponse<String> sendPostRequest(String uri, String req)
            throws IOException, InterruptedException;

}
