package org.comic_social.user_api.user;

import lombok.AllArgsConstructor;
import org.comic_social.user_api.name.Name;
import org.comic_social.user_api.name.NameDto;
import org.comic_social.user_api.name.NameRepository;
import org.comic_social.user_api.user.User;
import org.comic_social.user_api.user.UserDto;
import org.comic_social.user_api.user.UserRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final NameRepository nameRepository;

    private final TransactionalOperator rxtx;

    public Mono<UserDto> findByExternalId(UUID id) {
        return userRepository
                .findByExternalId(id);
    }

    public Mono<UserDto> findByUsername(String username) {
        return userRepository
                .findByUsername(username);
    }

    public Flux<UserDto> findAll(Pageable pageable) {
        return userRepository.findAllWithName(pageable);
    }

    public Mono<Long> count() {
        return userRepository.count();
    }

    public Mono<UserDto> create(UserDto userDto) {
        return nameRepository
                .save(new Name(null, userDto.name().firstName(), userDto.name().lastName()))
                .flatMap(savedName -> create(userDto, savedName))
                .as(rxtx::transactional);
    }

    private Mono<UserDto> create(UserDto userDto, Name name) {
        var user = new User(null, userDto.id(), userDto.username(), name.id());
        return userRepository
                .save(user)
                // force loading of other auto generated fields besides ID.
                .flatMap(u -> userRepository.findById(u.id()))
                .map(saved -> this.map(saved, name));
    }

    public Mono<Void> deleteAll() {
        return this.userRepository
                .deleteAll()
                .then(this.nameRepository.deleteAll());
    }

    private UserDto map(User user, Name name) {
        var nameDto =  new NameDto(name.firstName(), name.lastName());
        return new UserDto(user.externalId(), user.username(), nameDto);
    }

}
