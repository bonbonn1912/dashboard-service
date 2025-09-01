package com.bonbonn.springboot_starter.repository.tuya;

import com.bonbonn.springboot_starter.data.tuya.TuyaDeviceEntity;
import com.bonbonn.springboot_starter.data.tuya.TuyaDeviceEntity.TuyaDeviceName;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TuyaDeviceRepository extends JpaRepository<TuyaDeviceEntity, Long> {
  Optional<TuyaDeviceEntity> findByName(TuyaDeviceName name);
}
