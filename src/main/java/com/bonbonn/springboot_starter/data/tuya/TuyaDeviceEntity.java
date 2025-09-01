package com.bonbonn.springboot_starter.data.tuya;

import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "TUYA_DEVICE")
@Getter
@Setter
public class TuyaDeviceEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Convert(converter = TuyaDeviceNameConverter.class)
  @Column(name = "NAME", nullable = false, length = 100)
  private TuyaDeviceName name;

  @Enumerated(EnumType.STRING)
  @Column(name = "TYPE", nullable = false, length = 50)
  private TuyaDeviceType type;

  @Column(name = "CREATED_AT", nullable = false, updatable = false,
      columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
  private LocalDateTime createdAt;

  @OneToMany(mappedBy = "device", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<TuyaRegulatorEntity> regulators = new ArrayList<>();

  @OneToMany(mappedBy = "device", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<TuyaElectricityMeterEntity> electricityMeters = new ArrayList<>();


  public enum TuyaDeviceType {
    REGULATOR,
    ELECTRICITY_METER
  }

  public enum TuyaDeviceName {
    LEDA("regulatorLeda"),
    ROTH("regulatorRoth"),
    ELECTRICITY_METER("electricityMeter");

    private final String dbValue;

    TuyaDeviceName(String dbValue) {
      this.dbValue = dbValue;
    }

    @JsonValue
    public String getDbValue() {
      return dbValue;
    }

    public static TuyaDeviceName fromDbValue(String dbValue) {
      for (TuyaDeviceName dn : values()) {
        if (dn.dbValue.equalsIgnoreCase(dbValue)) {
          return dn;
        }
      }
      throw new IllegalArgumentException("Unknown dbValue: " + dbValue);
    }
  }
}
