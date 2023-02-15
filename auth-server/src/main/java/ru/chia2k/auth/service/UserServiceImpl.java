package ru.chia2k.auth.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.chia2k.auth.domain.UserEntity;
import ru.chia2k.auth.repository.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public Optional<UserEntity> getByAccountNumber(@NonNull Integer accountNumber) {
        return userRepository.findWorkingUserByAccountNumber(accountNumber);
    }
}
