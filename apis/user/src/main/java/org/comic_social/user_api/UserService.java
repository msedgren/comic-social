package org.comic_social.user_api;

import lombok.AllArgsConstructor;
import org.comic_social.user_api.name.Name;
import org.comic_social.user_api.name.NameDto;
import org.comic_social.user_api.name.NameRepository;
import org.comic_social.user_api.user.User;
import org.comic_social.user_api.user.UserDto;
import org.comic_social.user_api.user.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@Transactional
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final NameRepository nameRepository;

    public Mono<UserDto> findByExternalId(UUID id) {
        return userRepository
                .findByExternalId(id)
                .flatMap(this::withName);
    }

    public Mono<UserDto> search(String username) {
        return userRepository
                .findByUsername(username)
                .flatMap(this::withName);
    }

    private Mono<UserDto> withName(User user) {
        return nameRepository
                .findById(user.getNameId())
                .map(n -> map(user, n));
    }

    public Mono<UserDto> create(UserDto userDto) {
        return nameRepository
                .save(new Name(null, userDto.name().firstName(), userDto.name().lastName()))
                .flatMap(savedName -> create(userDto, savedName));
    }

    private Mono<UserDto> create(UserDto userDto, Name name) {
        var user = new User(null, userDto.id(), userDto.username(), name.getId());
        return userRepository.save(user)
                .map(saved -> this.map(saved, name));
    }

    public Mono<Void> deleteAll() {
        return this.userRepository
                .deleteAll()
                .flatMap(v -> this.nameRepository.deleteAll());
    }

    private UserDto map(User user, Name name) {
        var nameDto =  new NameDto(name.getFirstName(), name.getLastName());
        return new UserDto(user.getExternalId(), user.getUsername(), nameDto);
    }

}
