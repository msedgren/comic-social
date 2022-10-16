FROM ghcr.io/graalvm/jdk:ol8-java17-22.2.0-b1 as setup
#install xargs... needed by gradlew
RUN microdnf install findutils
#run as non-root user
ENV USER jdk_user
ENV HOME /home/$USER
RUN groupadd -g 1000 $USER && useradd -m -u 1000 -g $USER $USER
USER $USER
WORKDIR $HOME

FROM setup as gradle
#Disable gradle daemon
RUN mkdir $HOME/.gradle && printf 'org.gradle.daemon=false\n' > $HOME/.gradle/gradle.properties
COPY --chown=$USER build.gradle gradlew settings.gradle lombok.config ./
COPY --chown=$USER gradle ./gradle
#force a download of gradle at an earlier stage so that it is cached.
RUN ./gradlew -v

FROM gradle as tdd
ENTRYPOINT ["./gradlew", "-t", "test", "jacocoTestReport"]

FROM gradle as debug-tdd
ENTRYPOINT ["./gradlew", "-t", "test", "-PjvmArgs=-agentlib:jdwp=transport=dt_socket,server=y,suspend=y,address=*:5005"]

FROM gradle as development
ENTRYPOINT ["./gradlew", "bootRun"]

FROM gradle as debug
ENTRYPOINT ["./gradlew", "bootRun", "-PjvmArgs=-agentlib:jdwp=transport=dt_socket,server=y,suspend=y,address=*:5005"]

### build jar ###
FROM gradle as build
COPY --chown=$USER src ./src
RUN ./gradlew clean bootJar

FROM build as coverage
RUN ./gradlew clean test jacocoTestCoverageVerification

### generate the production image ###
## If using a native image, which is the hope, then need to figure out how to run this part.
#FROM ???  as app
#COPY --chown=root --from=coverage /home/jdk_user/explode/ ./
#CMD ["java", "-cp", "BOOT-INF/classes:BOOT-INF/lib/*", "org.comic_social.user_api.UserApplication"]
#EXPOSE 8080
