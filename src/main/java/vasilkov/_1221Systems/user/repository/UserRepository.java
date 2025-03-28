package vasilkov._1221Systems.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vasilkov._1221Systems.user.domain.User;

/**
 * Репозиторий для управления пользователями.
 * Расширяет {@link JpaRepository} с типом {@link User} и ID {@link Long}.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Проверяет существование пользователя по email.
     *
     * @param email email для проверки
     * @return true если email уже занят, иначе false
     */
    boolean existsByEmail(String email);
}
