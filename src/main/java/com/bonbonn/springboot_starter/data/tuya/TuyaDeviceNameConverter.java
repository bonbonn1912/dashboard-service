package com.bonbonn.springboot_starter.data.tuya;


import com.bonbonn.springboot_starter.data.tuya.TuyaDeviceEntity.TuyaDeviceName;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class TuyaDeviceNameConverter implements AttributeConverter<TuyaDeviceName, String> {

  @Override
  public String convertToDatabaseColumn(TuyaDeviceName attribute) {
    return attribute != null ? attribute.getDbValue() : null;
  }

  @Override
  public TuyaDeviceName convertToEntityAttribute(String dbData) {
    return dbData != null ? TuyaDeviceName.fromDbValue(dbData) : null;
  }
}
