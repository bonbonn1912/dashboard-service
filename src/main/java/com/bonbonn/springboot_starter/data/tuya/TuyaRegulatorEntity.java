package com.bonbonn.springboot_starter.data.tuya;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "TUYA_REGULATOR")
@Getter
@Setter
public class TuyaRegulatorEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "DEVICE_ID", nullable = false)
  private TuyaDeviceEntity device;

  @Column(name = "T1", nullable = false)
  private Integer t1;

  @Column(name = "T2", nullable = false)
  private Integer t2;

  @Column(name = "T3", nullable = false)
  private Integer t3;

  @Column(name = "LAST_UPDATE", nullable = false)
  private LocalDateTime lastUpdate;
}