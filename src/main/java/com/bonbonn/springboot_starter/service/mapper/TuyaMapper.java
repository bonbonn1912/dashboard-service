package com.bonbonn.springboot_starter.service.mapper;

import com.bonbonn.springboot_starter.base.BaseMapper;
import com.bonbonn.springboot_starter.data.tuya.TuyaDeviceEntity.TuyaDeviceType;
import com.bonbonn.springboot_starter.data.tuya.TuyaElectricityMeterEntity;
import com.bonbonn.springboot_starter.data.tuya.TuyaRegulatorEntity;
import com.bonbonn.springboot_starter.model.DeviceType;
import com.bonbonn.springboot_starter.model.SensorDataEntry;
import com.bonbonn.springboot_starter.model.TuyaMeasurementResponse;
import java.util.List;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper( uses = BaseMapper.class)
public interface TuyaMapper {

  @BeanMapping(ignoreByDefault = true)
  @Mapping(target = "lastUpdate", source = "lastUpdate")
  @Mapping(target = "value", source = "value")
  TuyaElectricityMeterEntity toElectricityMeterEntity(SensorDataEntry sensorDataEntry);

  @BeanMapping(ignoreByDefault = true)
  @Mapping(target = "lastUpdate", source = "lastUpdate")
  @Mapping(target = "t1", source = "t1")
  @Mapping(target = "t2", source = "t2")
  @Mapping(target = "t3", source = "t3")
  TuyaRegulatorEntity toRegulatorEtntiy(SensorDataEntry sensorDataEntry);

  @BeanMapping(ignoreByDefault = true)
  @Mapping(target = "lastUpdate", source = "lastUpdate")
  @Mapping(target = "t1", source = "t1")
  @Mapping(target = "t2", source = "t2")
  @Mapping(target = "t3", source = "t3")
  SensorDataEntry toSensorDataEntry(TuyaRegulatorEntity tuyaRegulatorEntity);


  @BeanMapping(ignoreByDefault = true)
  @Mapping(target = "lastUpdate", source = "lastUpdate")
  @Mapping(target = "value", source = "value")
  SensorDataEntry toSensorDataEntry(TuyaElectricityMeterEntity tuyaElectricityMeterEntity);
  List<SensorDataEntry> toSensorDataEntryE(List<TuyaElectricityMeterEntity> tuyaElectricityMeterEntities);


  DeviceType toDeviceType(TuyaDeviceType tuyaDeviceType);

  List<SensorDataEntry> toSensorDataEntry(List<TuyaRegulatorEntity> tuyaRegulatorEntities);

  @BeanMapping(ignoreByDefault = true)
  @Mapping(target = "deviceName", source = "deviceName")
  @Mapping(target = "deviceType", source = "deviceType")
  @Mapping(target = "sensorData", source = "sensorData")
  TuyaMeasurementResponse toTuyaMeasurementResponse(MeasurementResonseRecord measurementResonseRecord);

  record MeasurementResonseRecord(List<SensorDataEntry> sensorData, String deviceName, DeviceType deviceType){}

}
