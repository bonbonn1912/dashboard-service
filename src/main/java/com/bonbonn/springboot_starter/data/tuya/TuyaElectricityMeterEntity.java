package com.bonbonn.springboot_starter.data.tuya;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "TUYA_ELECTRICITY_METER")
@Getter
@Setter
public class TuyaElectricityMeterEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "DEVICE_ID", nullable = false)
  private TuyaDeviceEntity device;

  @Column(name = "USAGE_VALUE", nullable = false)
  private Integer value;

  @Column(name = "LAST_UPDATE", nullable = false)
  private LocalDateTime lastUpdate;
}
