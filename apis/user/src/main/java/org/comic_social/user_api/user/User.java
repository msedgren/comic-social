package org.comic_social.user_api.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Table("social_user")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class User {

    /**
     * The ID used as a foreign key in the DB. Possibly a bit excessive but allows both the username and the extrernalID
     * (the ID we expect the client to use when querying) to be changed. This should not be exposed to anything.
     */
    @Id
    @EqualsAndHashCode.Include
    private Long id;

    /**
     * The ID exposed to the client
     */
    @Column("external_id")
    private UUID externalId;

    /**
     * The name chosen by the user.
     */
    @Column()
    private String username;

    /**
     * Moved name to its own table to get an idea of relational mappings witn R2DBC. This also puts the application
     * toward allowing the user to have multiple names...
     */
    @Column("name_id")
    private Long nameId;
}
