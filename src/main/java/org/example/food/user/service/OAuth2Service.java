package org.example.food.user.service;

import org.springframework.stereotype.Service;

@Service
public class OAuth2Service {

    private final GoogleOAuth2Service googleOAuth2Service;
    private final UserRepository userRepository;
    private final JwtAuthenticationService jwtAuthenticationService;

    public OAuth2Service(
            GoogleOAuth2Service googleOAuth2Service,
            UserRepository userRepository,
            JwtAuthenticationService jwtAuthenticationService
    ) {
        this.googleOAuth2Service = googleOAuth2Service;
        this.userRepository = userRepository;
        this.jwtAuthenticationService = jwtAuthenticationService;
    }

    public UserLoginResponseDto oAuth2Login(String code, String registrationId) {
        String accessToken = getAccessToken(code, registrationId);
        JsonNode userResourceNode = getUserResource(accessToken, registrationId);
        assert userResourceNode != null;

        String uid = userResourceNode.get("id").asText();
        String nickname = userResourceNode.get("name").asText();
        String profile = userResourceNode.get("picture").asText();

        User user = userRepository.findByUidAndProvider(uid, registrationId);
        if (user == null) {
            user = userRepository.save(new User(nickname, uid, registrationId, profile));
        } else {
            if (!user.getName().equals(nickname) || !user.getProfile().equals(profile)) {
                user.setName(nickname);
                user.setProfile(profile);
                user = userRepository.save(user);
            }
        }

        var tokenPair = jwtAuthenticationService.generateTokenPair(
                user.getId().toString(),
                user.getProvider(),
                user.getUid()
        );

        return new UserLoginResponseDto(
                new SimpleUserResponseDto(user),
                tokenPair.getFirst(),
                tokenPair.getSecond()
        );
    }

    public HttpHeaders oAuth2Redirect(String registrationId) {
        switch (registrationId) {
            case "google":
                return googleOAuth2Service.oAuth2Redirect();
            default:
                throw new CustomException(ErrorCode.OAUTH2_PROVIDER_ERROR);
        }
    }

    public String getAccessToken(String code, String registrationId) {
        switch (registrationId) {
            case "google":
                return googleOAuth2Service.getAccessToken(code, registrationId);
            default:
                throw new CustomException(ErrorCode.OAUTH2_PROVIDER_ERROR);
        }
    }

    public JsonNode getUserResource(String accessToken, String registrationId) {
        switch (registrationId) {
            case "google":
                return googleOAuth2Service.getUserResource(accessToken, registrationId);
            default:
                throw new CustomException(ErrorCode.OAUTH2_PROVIDER_ERROR);
        }
    }
}
