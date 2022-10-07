package org.comic_social.user_api;

import lombok.AllArgsConstructor;
import org.comic_social.user_api.user.UserDto;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/")
    public Mono<UserDto> search(@RequestParam String username) {
        return userService.search(username);
    }

    @GetMapping("/{id}")
    public Mono<UserDto> findById(@PathVariable UUID id) {
        return userService.findByExternalId(id);
    }

    @PostMapping("/") Mono<UserDto> create(@RequestBody UserDto userDto) {
        return userService.create(userDto);
    }
}
