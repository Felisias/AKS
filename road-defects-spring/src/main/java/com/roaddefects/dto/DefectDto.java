package com.roaddefects.dto;

import java.time.LocalDateTime;

/** Дефект для вывода в списке. */
public class DefectDto {

    private final Long id;
    private final String typeCode;
    private final String typeName;
    private final double latitude;
    private final double longitude;
    private final LocalDateTime detectedAt;

    public DefectDto(Long id, String typeCode, String typeName, double latitude, double longitude,
                     LocalDateTime detectedAt) {
        this.id = id;
        this.typeCode = typeCode;
        this.typeName = typeName;
        this.latitude = latitude;
        this.longitude = longitude;
        this.detectedAt = detectedAt;
    }

    public Long getId() {
        return id;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public String getTypeName() {
        return typeName;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public LocalDateTime getDetectedAt() {
        return detectedAt;
    }
}
