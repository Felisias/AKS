package com.roaddefects.repository;

import com.roaddefects.entity.Defect;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DefectRepository extends JpaRepository<Defect, Long> {

    // join fetch: тип подгружается одним запросом вместе с дефектами
    @Query("select d from Defect d join fetch d.type order by d.id")
    List<Defect> findAllWithType();

    long countByType_Code(String code);
}
