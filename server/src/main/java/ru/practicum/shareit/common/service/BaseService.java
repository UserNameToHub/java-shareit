package ru.practicum.shareit.common.service;

import java.lang.reflect.Field;
import java.util.Objects;

public interface BaseService<T, ID> {
    default <T1, T2> void updateField(T1 dto, T2 entity) throws IllegalAccessException, NoSuchFieldException {
        Field[] declaredFieldsUserTo = dto.getClass().getDeclaredFields();

        for (Field field : declaredFieldsUserTo) {
            field.setAccessible(true);
            if (Objects.nonNull(field.get(dto))) {
                Field declaredFieldEntity = entity.getClass().getDeclaredField(field.getName());
                declaredFieldEntity.setAccessible(true);
                declaredFieldEntity.set(entity, field.get(dto));
            }
        }
    }
}