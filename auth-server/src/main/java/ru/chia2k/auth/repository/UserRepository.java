package ru.chia2k.auth.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.chia2k.auth.domain.UserEntity;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Integer> {
    @Query("select u from UserEntity u where u.accountNumber = :accountNumber and u.accountEnabled = true and u.accountLocked = false")
    Optional<UserEntity> findWorkingUserByAccountNumber(@Param("accountNumber") Integer accountNumber);
}
