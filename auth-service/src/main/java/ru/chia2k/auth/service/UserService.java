package ru.chia2k.auth.service;

import lombok.NonNull;
import ru.chia2k.auth.entity.UserEntity;

import java.util.Optional;

public interface UserService {
    Optional<UserEntity> getByAccountNumber(@NonNull Integer accountNumber);
}
