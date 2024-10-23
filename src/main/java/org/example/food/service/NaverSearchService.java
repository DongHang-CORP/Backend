package org.example.food.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

@Service
public class NaverSearchService {

    private static final Logger logger = LoggerFactory.getLogger(NaverSearchService.class);

    @Value("${naver.api.client-id}")
    private String clientId;

    @Value("${naver.api.client-secret}")
    private String clientSecret;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public String searchLocal(String query) {
        String encodedQuery = null;
        try {
            encodedQuery = URLEncoder.encode(query, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            logger.error("검색어 인코딩 실패", e);
            throw new RuntimeException("검색어 인코딩 실패", e);
        }

        String apiURL = "https://openapi.naver.com/v1/search/local.json?query=" + encodedQuery + "&display=10&start=1&sort=sim";

        Map<String, String> requestHeaders = new HashMap<>();
        requestHeaders.put("X-Naver-Client-Id", clientId);
        requestHeaders.put("X-Naver-Client-Secret", clientSecret);

        String response = get(apiURL, requestHeaders);
        return removeBTagsFromTitles(response);
    }

    private String removeBTagsFromTitles(String jsonResponse) {
        try {
            JsonNode rootNode = objectMapper.readTree(jsonResponse);
            if (!rootNode.has("items") || !rootNode.get("items").isArray()) {
                logger.warn("예상치 못한 JSON 구조: 'items' 배열이 없거나 배열이 아닙니다.");
                return jsonResponse; // 원본 응답 반환
            }

            ArrayNode itemsNode = (ArrayNode) rootNode.get("items");

            for (JsonNode item : itemsNode) {
                if (item.has("title")) {
                    String title = item.get("title").asText();
                    String cleanTitle = title.replaceAll("</?b>", "");
                    ((ObjectNode) item).put("title", cleanTitle);
                }
            }

            return objectMapper.writeValueAsString(rootNode);
        } catch (Exception e) {
            logger.error("JSON 처리 중 오류 발생: " + e.getMessage(), e);
            logger.debug("처리하려던 JSON: " + jsonResponse);
            return jsonResponse; // 오류 발생 시 원본 응답 반환
        }
    }

    private String get(String apiUrl, Map<String, String> requestHeaders) {
        HttpURLConnection con = connect(apiUrl);
        try {
            con.setRequestMethod("GET");
            for (Map.Entry<String, String> header : requestHeaders.entrySet()) {
                con.setRequestProperty(header.getKey(), header.getValue());
            }

            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                return readBody(con.getInputStream());
            } else {
                String errorResponse = readBody(con.getErrorStream());
                logger.error("API 요청 실패. 응답 코드: " + responseCode + ", 에러 메시지: " + errorResponse);
                return errorResponse;
            }
        } catch (IOException e) {
            logger.error("API 요청과 응답 실패", e);
            throw new RuntimeException("API 요청과 응답 실패", e);
        } finally {
            con.disconnect();
        }
    }

    private HttpURLConnection connect(String apiUrl) {
        try {
            URL url = new URL(apiUrl);
            return (HttpURLConnection) url.openConnection();
        } catch (MalformedURLException e) {
            logger.error("API URL이 잘못되었습니다: " + apiUrl, e);
            throw new RuntimeException("API URL이 잘못되었습니다: " + apiUrl, e);
        } catch (IOException e) {
            logger.error("연결이 실패했습니다: " + apiUrl, e);
            throw new RuntimeException("연결이 실패했습니다: " + apiUrl, e);
        }
    }

    private String readBody(InputStream body) {
        InputStreamReader streamReader = new InputStreamReader(body);

        try (BufferedReader lineReader = new BufferedReader(streamReader)) {
            StringBuilder responseBody = new StringBuilder();

            String line;
            while ((line = lineReader.readLine()) != null) {
                responseBody.append(line);
            }

            return responseBody.toString();
        } catch (IOException e) {
            logger.error("API 응답을 읽는 데 실패했습니다.", e);
            throw new RuntimeException("API 응답을 읽는 데 실패했습니다.", e);
        }
    }
}