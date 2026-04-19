package org.example.session10_btth.validation;



import org.example.session10_btth.model.BorrowRequest;
import org.example.session10_btth.model.ResourceItem;
import org.example.session10_btth.model.ResourceType;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalDate;
import java.util.Map;

@Component
public class BorrowRequestValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return BorrowRequest.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        // Không dùng ở đây vì cần truyền tồn kho động theo resource
    }

    public void validateBusiness(BorrowRequest req, Errors errors, Map<Long, ResourceItem> resourceStore) {
        if (req.getBorrowDate() != null && !req.getBorrowDate().isAfter(LocalDate.now())) {
            errors.rejectValue("borrowDate", "borrowDate.future", "Ngày dự kiến nhận phải sau ngày hiện tại");
        }

        if (req.getBorrowDate() != null && req.getReturnDate() != null
                && !req.getReturnDate().isAfter(req.getBorrowDate())) {
            errors.rejectValue("returnDate", "returnDate.afterBorrow", "Ngày dự kiến trả phải sau ngày nhận");
        }

        if (req.getResourceId() != null && req.getQuantity() != null) {
            ResourceItem item = resourceStore.get(req.getResourceId());
            if (item == null) {
                errors.rejectValue("resourceId", "resource.invalid", "Thiết bị/Phòng không tồn tại");
                return;
            }

            if (item.getType() == ResourceType.LAB_ROOM && req.getQuantity() != 1) {
                errors.rejectValue("quantity", "quantity.lab", "Đăng ký phòng Lab thì số lượng phải là 1");
            }

            if (req.getQuantity() > item.getAvailableQuantity()) {
                errors.rejectValue("quantity", "quantity.stock", "Số lượng muốn mượn vượt quá số lượng tồn kho");
            }
        }
    }
}