package com.myblog.web.admin;

import com.myblog.po.Tag;
import com.myblog.service.TagService;
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
public class TagController {

    @Autowired
    private TagService tagService;

    /*  展示所有的标签  */
    @GetMapping("/tags")
    public String tags(
            @PageableDefault(size = 5, sort = {"id"},
            direction = Sort.Direction.DESC) Pageable pageable,
                       Model model) {
        /*  分页查找标签  */
        model.addAttribute("page", tagService.listTag(pageable));
        return "admin/tags";
    }

    /*  跳转到新增页面  */
    @GetMapping("/tags/input")
    public String input(Model model) {
        model.addAttribute("tag", new Tag());
        return "admin/tags-input";
    }

    /*  按照id进入编辑页面  */
    @GetMapping("/tags/{id}/input")
    public String editInput(@PathVariable Long id, Model model) {
        model.addAttribute("tag", tagService.getTag(id));
        return "admin/tags-input";
    }

    /*  新增标签  */
    @PostMapping("/tags")
    public String post(@Valid Tag tag, BindingResult result, RedirectAttributes attributes) {
        /*  验证输入的名称是否重复  */
        Tag tag1 = tagService.getTagByName(tag.getName());
        if(tag1 != null) {
            result.rejectValue("name", "nameError", "不能重复添加标签");
        }

        /*  验证是否成功  */
        if(result.hasErrors()) {
            return "admin/tags-input";
        }


        Tag t = tagService.saveTag(tag);
        if(t == null) {
            attributes.addAttribute("message", "新增失败");
        } else {
            attributes.addAttribute("message", "新增成功");
        }
        return "redirect:/admin/tags";
    }

    /*  添加编辑信息  */
    @PostMapping("/tags/{id}")
    public String editPost(@Valid Tag tag, @PathVariable Long id, BindingResult result, RedirectAttributes attributes) {
        /*  验证重新输入的名称是否重复  */
        Tag tag1 = tagService.getTagByName(tag.getName());
        if(tag1 != null) {
            result.rejectValue("name", "nameError", "不能重复添加标签");
        }

        /*  验证是否成功  */
        if(result.hasErrors()) {
            return "admin/tags-input";
        }


        Tag t = tagService.updateTag(id, tag);
        if(t == null) {
            attributes.addAttribute("message", "编辑失败");
        } else {
            attributes.addAttribute("message", "编辑成功");
        }
        return "redirect:/admin/tags";
    }


    /*  删除标签  */
    @PostMapping("/tags/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes attributes) {
        attributes.addFlashAttribute("message", "删除成功");
        tagService.deleteTag(id);
        return "redirect:/admin/tags";
    }
}
