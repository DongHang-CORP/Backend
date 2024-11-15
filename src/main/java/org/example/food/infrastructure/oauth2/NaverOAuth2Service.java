package org.example.food.infrastructure.oauth2;

import com.fasterxml.jackson.databind.JsonNode;
import io.lettuce.core.dynamic.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Service
public class NaverOAuth2Service {

    private final String clientId = "${spring.security.oauth2.client.registration.naver.client-id}";
    private final String clientSecret = "${spring.security.oauth2.client.registration.naver.client-secret}";
    private final String authenticationUri = "${spring.security.oauth2.client.provider.naver.authorization-uri}";
    private final String redirectUri = "${spring.security.oauth2.client.registration.naver.redirect-uri}";
    private final String tokenUri = "${spring.security.oauth2.client.provider.naver.token-uri}";
    private final String resourceUri = "${spring.security.oauth2.client.provider.naver.user-info-uri}";

    public HttpHeaders oAuth2Redirect() {
        LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("response_type", "code");
        params.add("client_id", clientId);
        params.add("redirect_uri", redirectUri);
        params.add("state", "state_parameter_passthrough_value"); // CSRF 방지를 위한 state 값

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(authenticationUri)
                .queryParams(params);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(uriBuilder.build().toUri());
        return headers;
    }

    public String getAccessToken(String code, String registrationId) {
        LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("code", code);
        params.add("client_id", clientId);
        params.add("client_secret", clientSecret);
        params.add("redirect_uri", redirectUri);
        params.add("grant_type", "authorization_code");
        params.add("state", "state_parameter_passthrough_value"); // 인증 요청 시 사용한 state 값과 동일해야 함

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<LinkedMultiValueMap<String, String>> entity = new HttpEntity<>(params, headers);

        RestTemplate restTemplate = new RestTemplate();
        JsonNode response = restTemplate.exchange(tokenUri, HttpMethod.POST, entity, JsonNode.class).getBody();

        if (response == null || !response.has("access_token")) {
            throw new RuntimeException("Failed to retrieve access token from Naver.");
        }

        return response.get("access_token").asText();
    }

    public JsonNode getUserResource(String accessToken, String registrationId) {
        RestTemplate restTemplate = new RestTemplate();
        // restTemplate.setErrorHandler(oAuth2ErrorHandler); // 에러 핸들러 설정

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);

        HttpEntity<Void> entity = new HttpEntity<>(null, headers);

        return restTemplate.exchange(resourceUri, HttpMethod.GET, entity, JsonNode.class).getBody();
    }
}