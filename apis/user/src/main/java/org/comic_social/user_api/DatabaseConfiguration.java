package org.comic_social.user_api;

import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import org.comic_social.user_api.user.UserDtoConverter;
import org.springframework.context.annotation.Configuration;

import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class DatabaseConfiguration extends AbstractR2dbcConfiguration {

    @Override
    public ConnectionFactory connectionFactory() {
        return ConnectionFactories.get("");
    }

    @Override
    public List<Object> getCustomConverters() {
        List<Object> converters = new ArrayList<>();
        converters.add(new UserDtoConverter());
        return converters;
    }
}
