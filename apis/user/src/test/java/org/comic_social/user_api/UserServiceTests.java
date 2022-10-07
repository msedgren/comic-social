package org.comic_social.user_api;

import org.comic_social.user_api.name.NameDto;
import org.comic_social.user_api.user.UserDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

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
    @DisplayName("It can search for users")
    void itCanSearchForUsers() {
        // expect it finds nothing if users are not yet defined.
        assertTrue(userService.search("foo").blockOptional().isEmpty());
        // but when a user is created
        userService.create(new UserDto(null, "foo", new NameDto("a", "b"))).block();
        // then we should be able to find them
        var user = userService.search("foo").block();
        assertNotNull(user);
        assertEquals("foo", user.username());
        assertEquals("a", user.name().firstName());
        assertEquals("b", user.name().lastName());
    }
}
