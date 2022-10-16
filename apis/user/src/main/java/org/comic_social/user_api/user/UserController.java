package org.comic_social.user_api.user;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

    final static String PAGINATION_TOTAL_ELEMENTS_HEADER = "PAGINATION-TOTAL-ELEMENTS";

    private final UserService userService;

    @GetMapping("/")
    public Mono<ResponseEntity<Flux<UserDto>>> findAll(Pageable pageable) {
        return userService.count()
                .map(c ->
                        ResponseEntity
                                .ok()
                                .header(PAGINATION_TOTAL_ELEMENTS_HEADER, String.valueOf(c))
                                .body(userService.findAll(pageable)));
    }

    @GetMapping(path = "/", params = "username")
    public Mono<ResponseEntity<UserDto>> findByUsername(@RequestParam String username) {
        return userService
                .findByUsername(username)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<UserDto>> findById(@PathVariable UUID id) {
        return userService
                .findByExternalId(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping("/") Mono<ResponseEntity<UserDto>> create(@RequestBody UserDto userDto) {
        // prevent updates explicitly. This method always forces a create...
        if(userDto.id() != null) {
            return Mono.just(ResponseEntity.badRequest().build());
        }

        return userService
                .create(userDto)
                .map(ResponseEntity::ok);
    }
}
