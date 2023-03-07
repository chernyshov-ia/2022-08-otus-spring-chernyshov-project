package ru.chia2k.vnp.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.chia2k.vnp.domain.PackVolume;

import java.math.BigDecimal;

@Getter
@RequiredArgsConstructor
@Builder
public class PackVolumeRequestDto {
    @NotBlank(message = "Название типового объема должно быть указано")
    private final String name;

    @NotNull(message = "Объем должен быть указан")
    @DecimalMin(value = "0.1", message = "Объем не может быть меньше 0.1 литра")
    @DecimalMax(value = "1000", message = "Объем не может быть больше 1000 литров")
    private final BigDecimal volume;

    @Column(name = "height")
    @Max(value = 100, message = "Максимальная высота 100 см")
    @Min(value = 1, message = "Минимальная высота 100 см")
    private final Integer height;

    @Max(value = 100, message = "Максимальная длина 100 см")
    @Min(value = 1, message = "Минимальная длина 100 см")
    @Column(name = "length")
    private final Integer length;

    @Max(value = 100, message = "Максимальная ширина 100 см")
    @Min(value = 1, message = "Минимальная ширина 100 см")
    @Column(name = "width")
    private final Integer width;

    public PackVolume toDomainObject() {
        PackVolume obj = new PackVolume();
        obj.setId(null);
        obj.setName(name);
        obj.setVolume(volume);
        obj.setHeight(height);
        obj.setWidth(width);
        obj.setLength(length);
        return obj;
    }
}
