package ru.chia2k.vnp.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.chia2k.vnp.domain.PackVolume;

import java.math.BigDecimal;

@Getter
@RequiredArgsConstructor
@Builder
public class PackVolumeDto {
    private final Integer id;
    private final String name;
    private final BigDecimal volume;
    private final Integer height;
    private final Integer length;
    private final Integer width;

    public static PackVolumeDto fromDomainObject(PackVolume object) {
        return PackVolumeDto.builder()
                .id(object.getId())
                .name(object.getName())
                .volume(object.getVolume())
                .height(object.getHeight())
                .length(object.getLength())
                .width(object.getWidth())
                .build();
    }
}
