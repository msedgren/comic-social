package org.comic_social.user_api;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.Charset;

@Configuration
@Slf4j
public class TestDatabaseConfiguration {

    private final DatabaseClient client;

    private final String ddl;

    public TestDatabaseConfiguration(DatabaseClient client, @Value("classpath:h2_ddl.sql") Resource ddlResource) throws IOException {
        this.client = client;
        this.ddl = StreamUtils.copyToString(ddlResource.getInputStream(), Charset.defaultCharset());
    }

    @PostConstruct
    public void init() {
        log.info("Attempting to create the DB...");
        client.sql(ddl)
                .then()
                .block();
    }
}
