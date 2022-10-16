package org.comic_social.user_api;

import org.comic_social.user_api.name.NameDto;
import org.comic_social.user_api.user.UserDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@UserTest
public class UserServiceTests {

    @Autowired
    private UserService userService;

    @AfterEach
    void cleanup() {
        userService.deleteAll().block();
    }

    @Test
    @DisplayName("It can find a user using their external ID")
    void itCanFindAUserByExternalId() {
        // expect it finds nothing if given a bogus UUID
        assertTrue(userService.findByExternalId(new UUID(0, 1)).blockOptional().isEmpty());
        // but when a user is created
        var created = userService.create(new UserDto(null, "foo", new NameDto("a", "b"))).block();
        assertNotNull(created);
        // then we should be able to find them by their external ID
        var user = userService.findByExternalId(created.id()).block();
        assertNotNull(user);
        assertEquals("foo", user.username());
        assertEquals("a", user.name().firstName());
        assertEquals("b", user.name().lastName());
    }

    @Test
    @DisplayName("It can create and search for users")
    void itCanCreateAndSearchForUsers() {
        // expect it finds nothing if users are not yet defined.
        assertTrue(userService.findByUsername("foo").blockOptional().isEmpty());
        // but when a user is created
        var created = userService.create(new UserDto(null, "foo", new NameDto("a", "b"))).block();
        assertNotNull(created);
        // then we should be able to find them
        var user = userService.findByUsername("foo").block();
        assertNotNull(user);
        assertNotNull(user.id());
        assertEquals("foo", user.username());
        assertEquals("a", user.name().firstName());
        assertEquals("b", user.name().lastName());
    }
}
