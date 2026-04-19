package org.example.session10_btth.controller;

import jakarta.validation.Valid;
import org.example.session10_btth.model.BorrowRequest;
import org.example.session10_btth.model.ResourceItem;
import org.example.session10_btth.model.ResourceType;
import org.example.session10_btth.validation.BorrowRequestValidator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;

@Controller
@RequestMapping("/student")
public class StudentController {

    private final BorrowRequestValidator borrowRequestValidator;

    // Dữ liệu giả lập trong bộ nhớ
    private final Map<Long, ResourceItem> resourceStore = new LinkedHashMap<>();
    private final List<BorrowRequest> requestStore = new ArrayList<>();

    public StudentController(BorrowRequestValidator borrowRequestValidator) {
        this.borrowRequestValidator = borrowRequestValidator;

        resourceStore.put(1L, new ResourceItem(1L, "Màn hình rời Dell 24\"", "DELL_24_ICNH.jpg", 10, ResourceType.DEVICE));
        resourceStore.put(2L, new ResourceItem(2L, "Cáp HDMI", "CAP_HDMI.jpg", 30, ResourceType.DEVICE));
        resourceStore.put(3L, new ResourceItem(3L, "Phòng Lab A1_PTIT", "LAB_ROOM.jpg", 1, ResourceType.LAB_ROOM));

    }

    @GetMapping("/resources")
    public String listResources(Model model, @ModelAttribute("successMessage") String successMessage) {
        model.addAttribute("items", resourceStore.values());
        return "student/list";
    }

    @GetMapping("/borrow/{id}")
    public String showForm(@PathVariable("id") Long id, Model model) {

        ResourceItem item = resourceStore.get(id);
        if (item == null) return "redirect:/student/resources";

        BorrowRequest form = new BorrowRequest();
        form.setResourceId(id);
        form.setQuantity(1);

        model.addAttribute("item", item);
        model.addAttribute("borrowRequest", form);
        return "student/form";
    }

    @PostMapping("/borrow")
    public String submitForm(@Valid @ModelAttribute("borrowRequest") BorrowRequest form,
                             BindingResult bindingResult,
                             Model model,
                             RedirectAttributes redirectAttributes) {

        ResourceItem item = resourceStore.get(form.getResourceId());
        borrowRequestValidator.validateBusiness(form, bindingResult, resourceStore);

        if (bindingResult.hasErrors()) {
            model.addAttribute("item", item);
            return "student/form";
        }

        requestStore.add(form);

        // Trừ tồn kho cho thiết bị
        if (item != null && item.getType() == ResourceType.DEVICE) {
            item.setAvailableQuantity(item.getAvailableQuantity() - form.getQuantity());
        }

        redirectAttributes.addFlashAttribute("successMessage", "Đăng ký thành công!");
        return "redirect:/student/resources";
    }

    // Cho Admin đọc danh sách request từ cùng store
    public List<BorrowRequest> getRequestStore() {
        return requestStore;
    }

    public Map<Long, ResourceItem> getResourceStore() {
        return resourceStore;
    }
}