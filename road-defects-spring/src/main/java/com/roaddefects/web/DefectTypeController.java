package com.roaddefects.web;

import com.roaddefects.dto.DefectTypeDto;
import com.roaddefects.service.BusinessException;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/types")
public class DefectTypeController {

    private final DefectTypeService typeService;

    public DefectTypeController(DefectTypeService typeService) {
        this.typeService = typeService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("types", typeService.findAll());
        return "types/list";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        return showForm(new DefectTypeDto(), true, model);
    }

    @GetMapping("/{code}/edit")
    public String editForm(@PathVariable("code") String code, Model model) {
        return typeService.findByCode(code)
                .map(type -> showForm(type, false, model))
                .orElse("redirect:/types");
    }

    @PostMapping("/create")
    public String create(@Valid @ModelAttribute("form") DefectTypeDto form, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return showForm(form, true, model);
        }
        try {
            typeService.create(form);
        } catch (BusinessException e) {
            model.addAttribute("error", e.getMessage());
            return showForm(form, true, model);
        }
        return "redirect:/types";
    }

    @PostMapping("/update")
    public String update(@Valid @ModelAttribute("form") DefectTypeDto form, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return showForm(form, false, model);
        }
        try {
            typeService.update(form);
        } catch (BusinessException e) {
            model.addAttribute("error", e.getMessage());
            return showForm(form, false, model);
        }
        return "redirect:/types";
    }

    @PostMapping("/{code}/delete")
    public String delete(@PathVariable("code") String code, RedirectAttributes redirect) {
        try {
            typeService.delete(code);
        } catch (BusinessException e) {
            redirect.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/types";
    }

    private String showForm(DefectTypeDto form, boolean isNew, Model model) {
        model.addAttribute("form", form);
        model.addAttribute("isNew", isNew);
        return "types/form";
    }
}
