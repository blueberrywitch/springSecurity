package dika.spring.security.repository;

import dika.spring.security.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByExternalId(UUID externalId);

    Optional<User> findByUsername(String username);

    @Query("select distinct u from User u left join fetch u.linksEntity left join fetch u.role")
    List<User> findAllWithLinksAndRoles();
}
