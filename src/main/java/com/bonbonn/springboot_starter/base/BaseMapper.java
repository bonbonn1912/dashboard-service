package com.bonbonn.springboot_starter.base;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import org.mapstruct.Mapper;

@Mapper
public class BaseMapper {

  public LocalDateTime mapToLocalDateTime(OffsetDateTime offsetDateTime){
    return offsetDateTime != null ? offsetDateTime.toLocalDateTime() : null;
  }

  public OffsetDateTime mapToOffsetDateTime(LocalDateTime localDateTime){
    return localDateTime != null ? localDateTime.atZone(ZoneId.systemDefault()).toOffsetDateTime() : null;
  }

}
