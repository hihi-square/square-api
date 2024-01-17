package com.hihi.square.domain.activity.repository;

import com.hihi.square.domain.activity.dto.SiggAddressDto;
import com.hihi.square.domain.activity.entity.SiggAddress;
import org.hibernate.type.descriptor.converter.spi.JpaAttributeConverter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SiggAddressRepository extends JpaRepository<SiggAddress, Long> {
}
