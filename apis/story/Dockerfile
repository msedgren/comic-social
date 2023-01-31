FROM rust:1.66 as builder
WORKDIR /usr/src/app
COPY . .
RUN cargo install --path .

FROM debian:buster-slim
COPY --from=builder /usr/local/cargo/bin/story /usr/local/bin/story

ENV USER app_user
RUN groupadd -g 1000 $USER && useradd -u 1000 -g $USER $USER
USER $USER

EXPOSE 8080
CMD ["story"]
