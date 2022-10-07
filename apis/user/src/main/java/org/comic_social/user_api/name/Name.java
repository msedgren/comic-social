package org.comic_social.user_api.name;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Name {

    /**
     * The internal ID used by the datastore. This is never exposed to clients.
     */
    @Id
    @EqualsAndHashCode.Include
    private Long id;

    @Column("first_name")
    private String firstName;

    @Column("last_name")
    private String lastName;
}
