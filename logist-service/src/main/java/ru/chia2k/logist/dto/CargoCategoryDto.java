package ru.chia2k.logist.dto;

import ru.chia2k.logist.domain.CargoCategory;

public record CargoCategoryDto(int id, String name, String groupName) {
    public static CargoCategoryDto fromDomainObject(CargoCategory category) {
        return new CargoCategoryDto(category.getId(), category.getName(), category.getGroupName());
    }
}
