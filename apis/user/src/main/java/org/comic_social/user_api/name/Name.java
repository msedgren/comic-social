package org.comic_social.user_api.name;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("name")
public record Name(
        // The internal ID used by the datastore. This is never exposed to clients.
        @Id Long id,
        @Column("first_name") String firstName,
        @Column("last_name") String lastName) {
}
