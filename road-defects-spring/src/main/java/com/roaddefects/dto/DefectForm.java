package com.roaddefects.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/** Данные формы добавления/редактирования дефекта (id == null — новый). */
public class DefectForm {

    private Long id;

    @NotBlank(message = "Выберите тип")
    private String typeCode;

    @NotNull(message = "Укажите широту")
    @DecimalMin(value = "-90", message = "Широта от -90 до 90")
    @DecimalMax(value = "90", message = "Широта от -90 до 90")
    private Double latitude;

    @NotNull(message = "Укажите долготу")
    @DecimalMin(value = "-180", message = "Долгота от -180 до 180")
    @DecimalMax(value = "180", message = "Долгота от -180 до 180")
    private Double longitude;

    @NotNull(message = "Укажите дату и время")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime detectedAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public LocalDateTime getDetectedAt() {
        return detectedAt;
    }

    public void setDetectedAt(LocalDateTime detectedAt) {
        this.detectedAt = detectedAt;
    }
}
