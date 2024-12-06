package org.example.food.user.service;

import com.fasterxml.jackson.databind.JsonNode;
import org.example.food.infrastructure.oauth2.KakaoOAuth2Service;
import org.example.food.infrastructure.oauth2.NaverOAuth2Service;
import org.example.food.user.dto.OAuth2LoginDto;
import org.example.food.user.entity.User;
import org.example.food.user.exception.OAuth2Exception;
import org.example.food.user.exception.OAuth2ExceptionType;
import org.example.food.user.repository.UserRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

@Service
public class OAuth2Service {

    private final NaverOAuth2Service naverOAuth2Service;
    private final KakaoOAuth2Service kakaoOAuth2Service;
    private final UserRepository userRepository;
    // private final JwtAuthenticationService jwtAuthenticationService;

    public OAuth2Service(
            NaverOAuth2Service naverOAuth2Service,
            KakaoOAuth2Service kakaoOAuth2Service,
            UserRepository userRepository

    ) {
        this.naverOAuth2Service = naverOAuth2Service;
        this.kakaoOAuth2Service = kakaoOAuth2Service;
        this.userRepository = userRepository;
    }

//    서비스 로그인을 위한 유저 로그인 로직 처리 ~ custom user details를 활용하자
    public OAuth2LoginDto oAuth2Login(String code, String providerId) {
        String accessToken = getAccessToken(code, providerId);
        JsonNode userResourceNode = getUserResource(accessToken, providerId);
        assert userResourceNode != null;

        // id만 가져오고, 닉네임이랑 프로필 이미지는 가져 올 필요 없음
        Long uid = Long.parseLong(userResourceNode.get("id").asText());
//        String nickname = userResourceNode.get("name").asText();
//        String profile = userResourceNode.get("picture").asText();

        // 프로바이더 기준 유저 검색 쿼리 구현해야하고
        User user = userRepository.findByUidAndProvider(uid, providerId);
        // 새 유저 등록
        if (user == null) {
            user = userRepository.save(new User(uid, providerId));
        }

        var tokenPair = jwtAuthenticationService.generateTokenPair(
                user.getId().toString(),
                user.getProviderId(),
                user.getId()
        );

        return new OAuth2LoginDto(
                new SimpleUserResponseDto(user),
                tokenPair.getFirst(),
                tokenPair.getSecond()
        );
    }

    // 프로바이더 검증
    public HttpHeaders oAuth2Redirect(String registrationId) {
        switch (registrationId) {
            case "naver":
                return naverOAuth2Service.oAuth2Redirect();

            default:
                throw new OAuth2Exception(OAuth2ExceptionType.OAUTH2_PROVIDER_ERROR);
        }
    }

    public String getAccessToken(String code, String registrationId) {
        switch (registrationId) {
            case "naver":
                return naverOAuth2Service.getAccessToken(code, registrationId);

            default:
                throw new OAuth2Exception(OAuth2ExceptionType.OAUTH2_PROVIDER_ERROR);
        }
    }

    public JsonNode getUserResource(String accessToken, String registrationId) {
        switch (registrationId) {
            case "naver":
                return naverOAuth2Service.getUserResource(accessToken, registrationId);

            default:
                throw new OAuth2Exception(OAuth2ExceptionType.OAUTH2_PROVIDER_ERROR);
        }
    }
}
