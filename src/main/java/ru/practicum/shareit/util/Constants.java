package ru.practicum.shareit.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class Constants {
    public static final String HEADER_USER_ID = "X-Sharer-User-Id";
    public static final String DATE_TIME_PATTERN = "yyyy-MM-dd hh:mm:ss";
    public static final Sort ORDER_BY_ID_ASC = Sort.by(Sort.Direction.ASC, "id");
    public static final Sort ORDER_BY_DATE_DESC = Sort.by(Sort.Direction.DESC, "startDate");
    public static final Sort ORDER_BY_CREATED_DESC = Sort.by(Sort.Direction.DESC, "created");
    public static final Sort ORDER_BY_DATE_ASC = Sort.by(Sort.Direction.ASC, "startDate");
}