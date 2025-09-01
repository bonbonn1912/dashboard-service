package com.bonbonn.springboot_starter.service.tuya;

import com.bonbonn.springboot_starter.data.tuya.TuyaDeviceEntity;
import com.bonbonn.springboot_starter.data.tuya.TuyaDeviceEntity.TuyaDeviceName;
import com.bonbonn.springboot_starter.data.tuya.TuyaElectricityMeterEntity;
import com.bonbonn.springboot_starter.data.tuya.TuyaRegulatorEntity;
import com.bonbonn.springboot_starter.model.DeviceType;
import com.bonbonn.springboot_starter.model.SensorDataEntry;
import com.bonbonn.springboot_starter.model.TuyaMeasurementRequest;
import com.bonbonn.springboot_starter.model.TuyaMeasurementResponse;
import com.bonbonn.springboot_starter.repository.tuya.TuyaDeviceRepository;
import com.bonbonn.springboot_starter.repository.tuya.TuyaElectricityMeterRepository;
import com.bonbonn.springboot_starter.repository.tuya.TuyaRegulatorRepository;
import com.bonbonn.springboot_starter.service.mapper.TuyaMapper;
import com.bonbonn.springboot_starter.service.mapper.TuyaMapper.MeasurementResonseRecord;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TuyaService {

  private final TuyaDeviceRepository deviceRepository;
  private final TuyaElectricityMeterRepository meterRepository;
  private final TuyaRegulatorRepository regulatorRepository;
  private final TuyaMapper tuyaMapper;

  @Transactional
  public void addTuyaMeasurement(TuyaMeasurementRequest tuyaMeasurementRequest) {
    tuyaMeasurementRequest.getSensorData().forEach(
        sensorDataEntry -> {
          if(sensorDataEntry.getType().equals(DeviceType.ELECTRICITY_METER)){
            addElectricityMeter(sensorDataEntry);
          }
          if(sensorDataEntry.getType().equals(DeviceType.REGULATOR)){
            addRegulator(sensorDataEntry);
          }
        }
    );
  }

  public TuyaMeasurementResponse getTuyaMeasurementsResponseByDevice(String deviceName, Integer limit) {
    TuyaDeviceEntity device =  getDeviceEntityOrThrow(TuyaDeviceName.fromDbValue(deviceName));
    DeviceType deviceType = tuyaMapper.toDeviceType(device.getType());
    List<SensorDataEntry> sensorDataEntries = switch(deviceType){
          case REGULATOR -> getTuyaMeasurementsForRegulators(device, limit);
          case ELECTRICITY_METER -> getTuyaMeasurementsForElectricityMeters(device, limit);
    };

    return this.tuyaMapper.toTuyaMeasurementResponse(new MeasurementResonseRecord(sensorDataEntries, deviceName, deviceType));

  }

  private List<SensorDataEntry> getTuyaMeasurementsForRegulators(TuyaDeviceEntity device, Integer limit) {
    return this.tuyaMapper.toSensorDataEntry(device.getRegulators());
  }

  public List<SensorDataEntry> getTuyaMeasurementsForElectricityMeters(TuyaDeviceEntity device, Integer limit) {
    return this.tuyaMapper.toSensorDataEntryE(device.getElectricityMeters());
  }

  private void addRegulator(SensorDataEntry sensorDataEntry) {
    TuyaDeviceName deviceName = TuyaDeviceName.fromDbValue(sensorDataEntry.getDevice());
    TuyaDeviceEntity deviceEntity = this.getDeviceEntityOrThrow(deviceName);
    TuyaRegulatorEntity regulatorEntity = tuyaMapper.toRegulatorEtntiy(sensorDataEntry);
    regulatorEntity.setDevice(deviceEntity);
    this.regulatorRepository.save(regulatorEntity);
  }

  private void addElectricityMeter(SensorDataEntry sensorDataEntry) {
    TuyaDeviceName deviceName = TuyaDeviceName.fromDbValue(sensorDataEntry.getDevice());
    TuyaDeviceEntity deviceEntity = this.getDeviceEntityOrThrow(deviceName);
    TuyaElectricityMeterEntity electricityMeterEntity = tuyaMapper.toElectricityMeterEntity(sensorDataEntry);
    electricityMeterEntity.setDevice(deviceEntity);
    this.meterRepository.save(electricityMeterEntity);
  }

  public TuyaDeviceEntity getDeviceEntityOrThrow(TuyaDeviceName deviceName){
    return this.deviceRepository
        .findByName(deviceName)
        .orElseThrow(() -> new IllegalArgumentException("Device not found: " + deviceName));
  }

}
