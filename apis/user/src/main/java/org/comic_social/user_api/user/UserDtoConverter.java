package org.comic_social.user_api.user;

import io.r2dbc.spi.Row;
import org.comic_social.user_api.name.NameDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

import java.util.UUID;

@ReadingConverter
public class UserDtoConverter implements Converter<Row, UserDto> {

    @Override
    public UserDto convert(Row source) {
        var firstName = source.get("first_name", String.class);
        var lastName = source.get("last_name", String.class);
        var name = new NameDto(firstName, lastName);
        return new UserDto(source.get("id", UUID.class), source.get("username", String.class), name);
    }
}
