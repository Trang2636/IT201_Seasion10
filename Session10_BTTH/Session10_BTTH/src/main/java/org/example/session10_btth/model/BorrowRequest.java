package org.example.session10_btth.model;
// MODEL CHO PHIẾU ĐĂNG KÝ


import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class BorrowRequest {
    @NotBlank(message = "Họ và tên không được để trống")
    private String fullName;

    @NotBlank(message = "Mã sinh viên không được để trống")
    @Pattern(regexp = "^[A-Za-z]{2}\\d{4,}$", message = "Mã SV phải bắt đầu bằng 2 chữ cái và theo sau là các chữ số")
    private String studentCode;

    @NotBlank(message = "Email không được để trống")
    @Email(message = "Email không đúng định dạng")
    private String email;

    @NotNull(message = "Số lượng không được để trống")
    @Min(value = 1, message = "Số lượng phải là số nguyên dương")
    private Integer quantity;

    @NotNull(message = "Ngày nhận không được để trống")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate borrowDate;

    @NotNull(message = "Ngày trả không được để trống")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate returnDate;

    @NotBlank(message = "Lý do mượn không được để trống")
    private String reason;

    @NotNull(message = "Thiết bị/Phòng không hợp lệ")
    private Long resourceId;

    // Getters/setters
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getStudentCode() { return studentCode; }
    public void setStudentCode(String studentCode) { this.studentCode = studentCode; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }

    public LocalDate getBorrowDate() { return borrowDate; }
    public void setBorrowDate(LocalDate borrowDate) { this.borrowDate = borrowDate; }

    public LocalDate getReturnDate() { return returnDate; }
    public void setReturnDate(LocalDate returnDate) { this.returnDate = returnDate; }

    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }

    public Long getResourceId() { return resourceId; }
    public void setResourceId(Long resourceId) { this.resourceId = resourceId; }
}