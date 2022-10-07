package org.comic_social.user_api.name;

import jakarta.validation.constraints.NotBlank;

public record NameDto(
        @NotBlank String firstName,
        @NotBlank String lastName) {
}
