package org.comic_social.user_api.user;

import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface UserRepository extends ReactiveCrudRepository<User, Long> {

    @Query(" SELECT su.external_id as id, su.username, n.first_name as first_name, n.last_name as last_name " +
            "FROM social_user su, name n " +
            "WHERE su.name_id = n.id " +
            "  AND su.username = :username")
    Mono<UserDto> findByUsername(String username);

    @Query(" SELECT su.external_id as id, su.username, n.first_name as first_name, n.last_name as last_name " +
            "FROM social_user su, name n " +
            "WHERE su.name_id = n.id " +
            "  AND su.external_id = :externalId")
    Mono<UserDto> findByExternalId(UUID externalId);

    @Query(" SELECT su.external_id as id, su.username, n.first_name as first_name, n.last_name as last_name " +
            "FROM social_user su, name n " +
            "WHERE su.name_id = n.id " +
            "ORDER BY su.id asc")
    Flux<UserDto> findAllWithName(Pageable pageable);
}
