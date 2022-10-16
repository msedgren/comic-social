package org.comic_social.user_api.user;

import org.comic_social.user_api.UserTest;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

import static org.comic_social.user_api.user.UserController.PAGINATION_TOTAL_ELEMENTS_HEADER;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@UserTest
public class UserControllerTests {

    @InjectMocks
    private UserController userController;
    @Mock
    private UserService userService;

    @Test
    void itCanFetchByUsername() {
        // given there is a user with username bob
        when(userService.findByUsername(eq("bob"))).thenReturn(Mono.just(new UserDto(UUID.randomUUID(), "", null)));
        // when we lookup by username
        var found = userController.findByUsername("bob").block();
        // then it received a 200 and has the value.
        assertNotNull(found);
        assertTrue(found.getStatusCode().is2xxSuccessful());
        assertNotNull(found.getBody());
    }

    @Test
    void itCanFetchById() {
        // given there is a user with an external ID
        var id = UUID.randomUUID();
        when(userService.findByExternalId(eq(id))).thenReturn(Mono.just(new UserDto(id, "", null)));
        // when we lookup by id
        var found = userController.findById(id).block();
        // then it received a 200 and has the value.
        assertNotNull(found);
        assertTrue(found.getStatusCode().is2xxSuccessful());
        assertNotNull(found.getBody());
    }

    @Test
    void itCanFetchAll() {
        // given there is are two users
        when(userService.count()).thenReturn(Mono.just(2L));
        when(userService.findAll(any()))
                .thenReturn(Flux.just(
                        new UserDto(UUID.randomUUID(), "", null),
                        new UserDto(UUID.randomUUID(), "", null)));
        // when we find all of them
        var found = userController.findAll(Pageable.ofSize(10)).block();
        // then it received a 200.
        assertNotNull(found);
        assertTrue(found.getStatusCode().is2xxSuccessful());
        // it has the pagination header
        var header = found.getHeaders().get(PAGINATION_TOTAL_ELEMENTS_HEADER);
        assertNotNull(header);
        assertEquals(1, header.size());
        assertEquals(2, Integer.parseInt(header.get(0)));
        // and it has both elements in the body
        var body = found.getBody();
        assertNotNull(body);
        assertEquals(2, body.count().block());
    }

    @Test
    void itCanCreate() {
        // given the service can create a user
        when(userService.create(any())).thenReturn(Mono.just(new UserDto(UUID.randomUUID(), "", null)));
        // when we attempt to create a user
        var created = userController.create(new UserDto(null, "fred", null)).block();
        // then it returns success
        assertNotNull(created);
        assertTrue(created.getStatusCode().is2xxSuccessful());
        // and has the created user
        assertNotNull(created.getBody());
    }

    @Test
    void itCannotCreateExistingUsers() {
        // when we attempt to create with an existing user
        var created = userController.create(new UserDto(UUID.randomUUID(), "fred", null)).block();
        // then it returns bad request
        assertNotNull(created);
        assertTrue(created.getStatusCode().is4xxClientError());
        // and has not user
        assertNull(created.getBody());
    }
}
