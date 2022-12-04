package org.comic_social.user_api.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.comic_social.user_api.name.NameDto;

import java.util.UUID;

public record UserDto(UUID id,
                      @NotBlank String username,
                      @NotNull NameDto name) {
}
