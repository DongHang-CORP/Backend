# 베이스 이미지 선택
FROM bellsoft/liberica-openjdk-alpine:17

# 애플리케이션 실행 디렉토리 설정
ARG JAR_FILE=build/libs/*.jar

# 프로젝트의 JAR 파일을 Docker 이미지에 복사
COPY ${JAR_FILE} app.jar

# 컨테이너 내부 포트 설정 (서비스 파일에서 targetPort는 8080이므로 동일하게 설정)
EXPOSE 8080

# 애플리케이션 실행 명령어
ENTRYPOINT ["java", "-jar", "app.jar"]