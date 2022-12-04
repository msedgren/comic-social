FROM debian:bullseye-slim as setup
ENV liberica_file bellsoft-liberica-vm-openjdk17.0.5+8-22.3.0+2-linux-aarch64.deb
ENV liberica_download_location https://download.bell-sw.com/vm/22.3.0/bellsoft-liberica-vm-openjdk17.0.5+8-22.3.0+2-linux-aarch64.deb
RUN apt update && \
    apt install -yq wget gnupg2 gcc libz-dev && \
    wget -q $liberica_download_location && \
    apt -yq install ./$liberica_file && \
    rm $liberica_file

ENV JAVA_HOME /opt/bellsoft/liberica-vm-22.3.0-openjdk17
ENV PATH $PATH:$JAVA_HOME/bin

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
RUN ./gradlew clean nativeCompile

FROM build as coverage
RUN ./gradlew test jacocoTestCoverageVerification

### generate the production image ###
FROM debian:bullseye-slim as app
COPY --chown=root --from=coverage /home/jdk_user/build/native/nativeCompile/app app

ENV USER jre_user
RUN groupadd -g 1000 $USER && useradd -u 1000 -g $USER $USER
USER $USER

CMD ["./app"]
EXPOSE 8080
