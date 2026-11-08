package com.roaddefects.repository;

import com.roaddefects.entity.DefectType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DefectTypeRepository extends JpaRepository<DefectType, String> {

    List<DefectType> findAllByOrderByCodeAsc();
}
