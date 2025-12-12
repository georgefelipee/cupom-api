package com.example.cupom_api.dto;

import com.example.cupom_api.shared.CodeSanitizer;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;

public class CouponRequest {

    @NotBlank
    @Size(min = 1, max = 20)
    private String code;

    @NotBlank
    @Size(min = 1, max = 255)
    private String description;

    @NotNull
    @DecimalMin(value = "0.5")
    private BigDecimal discountValue;

    @NotNull
    @FutureOrPresent
    private LocalDate expirationDate;

    private boolean published;

    public CouponRequest() {
    }

    public CouponRequest(
            String code,
            String description,
            BigDecimal discountValue,
            LocalDate expirationDate,
            boolean published
    ) {
        this.code = code;
        this.description = description;
        this.discountValue = discountValue;
        this.expirationDate = expirationDate;
        this.published = published;
    }

    @AssertTrue(message = "Código do cupom deve conter exatamente 6 caracteres alfanuméricos após remover caracteres especiais")
    public boolean isSanitizedCodeValid() {
        return CodeSanitizer.isValid(getSanitizedCode());
    }

    public String getSanitizedCode() {
        return CodeSanitizer.sanitize(this.code);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getDiscountValue() {
        return discountValue;
    }

    public void setDiscountValue(BigDecimal discountValue) {
        this.discountValue = discountValue;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public boolean isPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }
}
