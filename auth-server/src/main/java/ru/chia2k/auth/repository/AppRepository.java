package ru.chia2k.auth.repository;

import org.springframework.data.repository.CrudRepository;
import ru.chia2k.auth.domain.AppEntity;

import java.util.List;

public interface AppRepository extends CrudRepository<AppEntity, String> {
    @Override
    List<AppEntity> findAll();
}
