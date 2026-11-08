package com.roaddefects.web;

import com.roaddefects.dto.DefectForm;
import com.roaddefects.service.BusinessException;
import com.roaddefects.service.DefectService;
import com.roaddefects.service.DefectTypeService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/defects")
public class DefectController {

    private final DefectService defectService;
    private final DefectTypeService typeService;

    public DefectController(DefectService defectService, DefectTypeService typeService) {
        this.defectService = defectService;
        this.typeService = typeService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("defects", defectService.findAll());
        return "defects/list";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        DefectForm form = new DefectForm();
        form.setDetectedAt(LocalDateTime.now().withNano(0));
        return showForm(form, model);
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable("id") Long id, Model model) {
        return defectService.findForm(id)
                .map(form -> showForm(form, model))
                .orElse("redirect:/defects");
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("form") DefectForm form, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return showForm(form, model);
        }
        try {
            defectService.save(form);
        } catch (BusinessException e) {
            model.addAttribute("error", e.getMessage());
            return showForm(form, model);
        }
        return "redirect:/defects";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") Long id) {
        defectService.delete(id);
        return "redirect:/defects";
    }

    private String showForm(DefectForm form, Model model) {
        model.addAttribute("form", form);
        model.addAttribute("types", typeService.findAll());
        return "defects/form";
    }
}
