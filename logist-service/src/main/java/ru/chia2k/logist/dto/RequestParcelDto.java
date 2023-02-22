package ru.chia2k.logist.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@Getter
@RequiredArgsConstructor
public class RequestParcelDto {
    @NotBlank(message = "Номер пломбы или номер сейф-пакета должен быть указан")
    @Size(min = 6, max = 13, message = "Размер пломбы ограничен от 6 до 13 символов")
    private final String seal;

    @NotBlank(message = "Отправитель у посылки должен быть указан")
    @Size(min = 4, max = 10, message = "Код адреса может быть длинной 4-10 символов")
    private final String senderId;

    @NotBlank(message = "Получатель у посылки должен быть указан")
    @Size(min = 4, max = 10, message = "Код адреса может быть длинной 4-10 символов")
    private final String recipientId;

    @NotNull(message = "Объем посылки должен быть указан")
    @DecimalMax(value = "1000.0", message = "Объем не может быть больше 1000 литров(~ 1 паллета)")
    @DecimalMin(value = "0.1", message = "Объем не может быть меньше 0.1 литра")
    private final BigDecimal volume;

    @NotNull(message = "Вес посылки долежн быть указан")
    @DecimalMax(value = "100.0", message = "Вес посылки не может быть больше 30 кг")
    @DecimalMin(value = "0.05", message = "Вес посылки не может быть меньше 50 грамм")
    private final BigDecimal weight;

    @NotBlank(message = "Для посылки требуется описание(содержимое, получатель, кабинет, контактная информация)")
    private final String description;
}
