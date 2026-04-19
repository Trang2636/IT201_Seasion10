package org.example.session10_btth.controller;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.example.session10_btth.model.ResourceItem;
import org.example.session10_btth.model.ResourceType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final StudentController studentController;

    public AdminController(StudentController studentController) {
        this.studentController = studentController;
    }

    // REQ-A02
    @GetMapping("/requests")
    public String viewRequests(Model model) {
        model.addAttribute("requests", studentController.getRequestStore());
        model.addAttribute("resourceStore", studentController.getResourceStore());
        return "admin/requests";
    }

    // REQ-A01 - Danh sách thiết bị
    @GetMapping("/resources")
    public String resourceList(Model model) {
        model.addAttribute("items", studentController.getResourceStore().values());
        return "admin/resource-list";
    }

    // Form thêm mới
    @GetMapping("/resources/new")
    public String createForm(Model model) {
        model.addAttribute("resourceForm", new ResourceForm());
        model.addAttribute("types", ResourceType.values());
        return "admin/resource-form";
    }

    @PostMapping("/resources/new")
    public String create(@Valid @ModelAttribute("resourceForm") ResourceForm form,
                         BindingResult result,
                         Model model) {
        if (result.hasErrors()) {
            model.addAttribute("types", ResourceType.values());
            return "admin/resource-form";
        }

        long newId = studentController.getResourceStore().keySet().stream().mapToLong(Long::longValue).max().orElse(0L) + 1;
        ResourceItem item = new ResourceItem(newId, form.getName(), form.getImageUrl(), form.getAvailableQuantity(), form.getType());
        studentController.getResourceStore().put(newId, item);
        return "redirect:/admin/resources";
    }

    // Form sửa
    @GetMapping("/resources/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        ResourceItem item = studentController.getResourceStore().get(id);
        if (item == null) return "redirect:/admin/resources";

        ResourceForm form = new ResourceForm();
        form.setName(item.getName());
        form.setImageUrl(item.getImageUrl());
        form.setAvailableQuantity(item.getAvailableQuantity());
        form.setType(item.getType());

        model.addAttribute("id", id);
        model.addAttribute("resourceForm", form);
        model.addAttribute("types", ResourceType.values());
        return "admin/resource-form";
    }

    @PostMapping("/resources/edit/{id}")
    public String edit(@PathVariable Long id,
                       @Valid @ModelAttribute("resourceForm") ResourceForm form,
                       BindingResult result,
                       Model model) {
        if (result.hasErrors()) {
            model.addAttribute("id", id);
            model.addAttribute("types", ResourceType.values());
            return "admin/resource-form";
        }

        ResourceItem old = studentController.getResourceStore().get(id);
        if (old != null) {
            old.setName(form.getName());
            old.setImageUrl(form.getImageUrl());
            old.setAvailableQuantity(form.getAvailableQuantity());
            old.setType(form.getType());
        }
        return "redirect:/admin/resources";
    }

    // Xóa
    @PostMapping("/resources/delete/{id}")
    public String delete(@PathVariable Long id) {
        studentController.getResourceStore().remove(id);
        return "redirect:/admin/resources";
    }

    public static class ResourceForm {
        @NotBlank(message = "Tên thiết bị/phòng không được để trống")
        private String name;

        @NotBlank(message = "Ảnh minh họa không được để trống")
        private String imageUrl;

        @Min(value = 1, message = "Số lượng tồn kho phải >= 1")
        private int availableQuantity;

        private ResourceType type;

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }

        public String getImageUrl() { return imageUrl; }
        public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

        public int getAvailableQuantity() { return availableQuantity; }
        public void setAvailableQuantity(int availableQuantity) { this.availableQuantity = availableQuantity; }

        public ResourceType getType() { return type; }
        public void setType(ResourceType type) { this.type = type; }
    }
}