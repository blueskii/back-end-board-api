# Base image로 OpenJDK 17 사용 
FROM openjdk:17

# 빌드시 매개값으로 전달된 버전 정보 (ARG는 컨테이너 런타임에는 사용 불가)
ARG VERSION

# 애플리케이션 JAR 파일을 컨테이너 내부로 복사
COPY build/libs/back-end-board-api-0.0.1-SNAPSHOT.jar /app/back-end-board-api.jar

# 이미지 메타데이터 추가
# Docker Hub에서는 볼 수 없으며, 이미지를 pull 한 후에,
# sudo docker inspect <IMAGE ID>를 통해 볼 수 있음
LABEL maintainer="YongKwon Shin<blueskii@naver.com>" \
      title="back-end-board-api" \
      version="$VERSION" \
      description="This image is back-end-board-api service"

# 컨테이너 내부에서 사용할 환경 변수 설정
ENV APP_HOME /app

# 컨테이너 내부에서 사용할 포트 지정 (하지만 포트를 개방하지는 않음)
# 실행 시 docker run -p 80:80 옵션을 사용해야 실제로 외부 접근 가능
EXPOSE 80

# 컨테이너 내부의 작업 디렉터리 설정 (이후 명령어 실행 위치)
WORKDIR $APP_HOME

# 컨테이너 내부의 자동 실행 명령어 설정 (Spring Boot 애플리케이션 실행)
ENTRYPOINT ["java"]
CMD ["-jar", "back-end-board-api.jar"]
