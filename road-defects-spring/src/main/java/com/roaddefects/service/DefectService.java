package com.roaddefects.service;

import com.roaddefects.dto.DefectDto;
import com.roaddefects.dto.DefectForm;
import com.roaddefects.entity.Defect;
import com.roaddefects.entity.DefectType;
import com.roaddefects.repository.DefectRepository;
import com.roaddefects.repository.DefectTypeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class DefectService {

    private final DefectRepository defectRepository;
    private final DefectTypeRepository typeRepository;

    public DefectService(DefectRepository defectRepository, DefectTypeRepository typeRepository) {
        this.defectRepository = defectRepository;
        this.typeRepository = typeRepository;
    }

    public List<DefectDto> findAll() {
        return defectRepository.findAllWithType().stream()
                .map(DefectService::toDto)
                .toList();
    }

    public Optional<DefectForm> findForm(Long id) {
        return defectRepository.findById(id).map(DefectService::toForm);
    }

    @Transactional
    public void save(DefectForm form) {
        DefectType type = typeRepository.findById(form.getTypeCode())
                .orElseThrow(() -> new BusinessException("Выберите существующий тип дефекта."));

        Defect defect;
        if (form.getId() == null) {
            defect = new Defect();
        } else {
            defect = defectRepository.findById(form.getId())
                    .orElseThrow(() -> new BusinessException("Дефект с id " + form.getId() + " не найден."));
        }

        defect.setType(type);
        defect.setLatitude(form.getLatitude());
        defect.setLongitude(form.getLongitude());
        defect.setDetectedAt(form.getDetectedAt());
        defectRepository.save(defect);
    }

    @Transactional
    public void delete(Long id) {
        defectRepository.deleteById(id);
    }

    private static DefectDto toDto(Defect d) {
        return new DefectDto(d.getId(), d.getType().getCode(), d.getType().getName(),
                d.getLatitude(), d.getLongitude(), d.getDetectedAt());
    }

    private static DefectForm toForm(Defect d) {
        DefectForm form = new DefectForm();
        form.setId(d.getId());
        form.setTypeCode(d.getType().getCode());
        form.setLatitude(d.getLatitude());
        form.setLongitude(d.getLongitude());
        form.setDetectedAt(d.getDetectedAt());
        return form;
    }
}
