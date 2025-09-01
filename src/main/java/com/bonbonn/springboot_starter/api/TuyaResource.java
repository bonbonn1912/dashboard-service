package com.bonbonn.springboot_starter.api;

import com.bonbonn.springboot_starter.model.TuyaMeasurementRequest;
import com.bonbonn.springboot_starter.model.TuyaMeasurementResponse;
import com.bonbonn.springboot_starter.service.tuya.TuyaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TuyaResource implements TuyaApi{

  private final TuyaService tuyaService;

  @Override
  public ResponseEntity<Void> addTuyaMeasurement(@Valid @RequestBody TuyaMeasurementRequest measurementRequest) {
    this.tuyaService.addTuyaMeasurement(measurementRequest);
    return ResponseEntity.ok().build();
  }

  @Override
  public ResponseEntity<TuyaMeasurementResponse> getTuyaMeasurementsByDevice(String deviceName, Integer limit) {
    return ResponseEntity.ok().body(this.tuyaService.getTuyaMeasurementsResponseByDevice(deviceName, limit));
  }
}

