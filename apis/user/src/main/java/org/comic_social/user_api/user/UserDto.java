package org.comic_social.user_api.user;

import org.comic_social.user_api.name.NameDto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

public record UserDto(UUID id,
                      @NotBlank String username,
                      @NotNull NameDto name) {
}
