package ru.chia2k.auth.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.chia2k.auth.entity.RefreshTokenEntity;

import java.util.Date;
import java.util.List;

@Repository
public interface RefreshTokenRepository extends CrudRepository<RefreshTokenEntity, String> {
    void deleteAllBySubjectAndApp(String subject, String app);
    Integer countAllBySubjectAndApp(String subject, String app);
    void deleteAllByExpirationBefore(Date date);
    void deleteAllByExpirationBeforeAndAppAndSubject(Date date, String app, String subject);
    List<RefreshTokenEntity> findAllBySubjectAndApp(String subject, String app);
}
