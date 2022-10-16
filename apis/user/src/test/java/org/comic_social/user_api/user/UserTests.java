package org.comic_social.user_api.user;

import org.comic_social.user_api.UserTest;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@UserTest
public class UserTests {

    //silly but code coverage wants it and exempting would probably be a bad idea...
    @Test
    void itCanGenerateAUser() {
        //given a user
        var user = new User(1L, UUID.randomUUID(), "bob", 2L);

        assertEquals(1L, user.id());
        assertEquals(2L, user.nameId());
        assertNotNull(user.externalId());
        assertEquals("bob", user.username());
    }
}
