package com.roaddefects.service;

import com.roaddefects.dto.DefectTypeDto;
import com.roaddefects.entity.DefectType;
import com.roaddefects.repository.DefectRepository;
import com.roaddefects.repository.DefectTypeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class DefectTypeService {

    private final DefectTypeRepository typeRepository;
    private final DefectRepository defectRepository;

    public DefectTypeService(DefectTypeRepository typeRepository, DefectRepository defectRepository) {
        this.typeRepository = typeRepository;
        this.defectRepository = defectRepository;
    }

    public List<DefectTypeDto> findAll() {
        return typeRepository.findAllByOrderByCodeAsc().stream()
                .map(DefectTypeService::toDto)
                .toList();
    }

    public Optional<DefectTypeDto> findByCode(String code) {
        return typeRepository.findById(code).map(DefectTypeService::toDto);
    }

    @Transactional
    public void create(DefectTypeDto dto) {
        if (typeRepository.existsById(dto.getCode())) {
            throw new BusinessException("Тип с кодом «" + dto.getCode() + "» уже существует.");
        }
        typeRepository.save(new DefectType(dto.getCode(), dto.getName()));
    }

    @Transactional
    public void update(DefectTypeDto dto) {
        DefectType type = typeRepository.findById(dto.getCode())
                .orElseThrow(() -> new BusinessException("Тип «" + dto.getCode() + "» не найден."));
        type.setName(dto.getName()); // UPDATE выполнит Hibernate при коммите транзакции
    }

    @Transactional
    public void delete(String code) {
        long used = defectRepository.countByType_Code(code);
        if (used > 0) {
            throw new BusinessException("Нельзя удалить тип «" + code + "»: к нему относится дефектов: " + used + ".");
        }
        typeRepository.deleteById(code);
    }

    private static DefectTypeDto toDto(DefectType type) {
        return new DefectTypeDto(type.getCode(), type.getName());
    }
}
