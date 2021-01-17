package com.myblog.web.admin;

import com.myblog.po.Type;
import com.myblog.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class TypeController {

    @Autowired
    private TypeService typeService;

    @GetMapping("/types")
    public String type(@PageableDefault(
            size = 5, sort = {"id"},
            direction = Sort.Direction.DESC) Pageable pageable,
                       Model model) {

        model.addAttribute("page", typeService.listType(pageable));
        return "admin/types";
    }

    @GetMapping("/types/input")
    public String input(Model model) {
        model.addAttribute("type", new Type());
        return "admin/types-input";
    }

    @GetMapping("/types/{id}/input")
    public String editInput(@PathVariable Long id, Model model) {
        model.addAttribute("type", typeService.getType(id));
        return "admin/types-input";
    }

    /**
     * @param type
     * @param result
     * @param attributes
     * @return
     */
    @PostMapping("/types")
    public String post(@Valid Type type, BindingResult result, RedirectAttributes attributes) {
        // 验证name类型是否存在
        Type type1 = typeService.getTypeByName(type.getName());
        if(type1 != null) {
            result.rejectValue("name", "nameError", "不能重复添加分类");
        }

        // ?? 问题：为什么检验属性是否为空需要放在前面  ??

        // 验证输入的属性是否正确
        if(result.hasErrors()) {
            return "admin/types-input";
        }

        // 保存提交的类型
        Type t = typeService.saveType(type);
        if(t == null) {
            attributes.addAttribute("message", "新增失败");
        } else {
            attributes.addAttribute("message", "新增成功");
        }
        return "redirect:/admin/types";
    }

    // 按照id编辑
    @PostMapping("/types/{id}")
    public String editPost(@Valid Type type, @PathVariable Long id, BindingResult result, RedirectAttributes attributes) {
        // 验证name类型是否存在
        Type type1 = typeService.getTypeByName(type.getName());
        if(type1 != null) {
            result.rejectValue("name", "nameError", "不能重复添加分类");
        }

        // 验证输入的属性是否正确
        if(result.hasErrors()) {
            return "admin/types-input";
        }

        // 保存提交的类型
        Type t = typeService.updateType(id, type);
        if(t == null) {
            attributes.addAttribute("message", "编辑失败");
        } else {
            attributes.addAttribute("message", "编辑成功");
        }
        return "redirect:/admin/types";
    }

    /*  删除类型  */
    @PostMapping("/types/{id}/delete")
    public String deleteType(@PathVariable Long id,
                             RedirectAttributes attributes) {
        attributes.addFlashAttribute("message", "删除成功");
        typeService.delete(id);
        return "redirect:/admin/types";
    }
}
