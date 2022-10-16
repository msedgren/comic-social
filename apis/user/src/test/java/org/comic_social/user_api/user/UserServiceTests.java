package org.comic_social.user_api.user;

import org.comic_social.user_api.UserTest;
import org.comic_social.user_api.name.NameDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.relational.core.mapping.Table;

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

    @Test
    void itCanCountAllUsers() {
        // expect it is 0 until users are added
        assertEquals(0, userService.count().block());
        // but when users are created
        userService.create(new UserDto(null, "foo", new NameDto("a", "b")))
                .then(userService.create(new UserDto(null, "bar", new NameDto("a", "b")))).block();
        // expect that it is able to pull the correct count
        assertEquals(2, userService.count().block());
    }

    @Test
    void itCanFindAllUsers() {
        // expect it finds nothing when none exist
        assertEquals(0, userService.findAll(Pageable.ofSize(10)).toStream().count());
        // but when users are created
        userService.create(new UserDto(null, "foo", new NameDto("a", "b")))
                .then(userService.create(new UserDto(null, "bar", new NameDto("a", "b")))).block();
        // expect that it is able to pull them
        assertEquals(2, userService.findAll(Pageable.ofSize(10)).toStream().count());
    }
}
