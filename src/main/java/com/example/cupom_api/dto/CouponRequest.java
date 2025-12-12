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

    @NotBlank(message = "Codigo do cupom e obrigatorio")
    @Size(min = 1, max = 20, message = "Codigo do cupom deve ter entre 1 e 20 caracteres antes do saneamento")
    private String code;

    @NotBlank(message = "Descricao e obrigatoria")
    @Size(min = 1, max = 255, message = "Descricao deve ter entre 1 e 255 caracteres")
    private String description;

    @NotNull(message = "Valor de desconto e obrigatorio")
    @DecimalMin(value = "0.5", message = "Valor de desconto deve ser no minimo 0.5")
    private BigDecimal discountValue;

    @NotNull(message = "Data de expiracao e obrigatoria")
    @FutureOrPresent(message = "Data de expiracao nao pode estar no passado")
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

    @AssertTrue(message = "Codigo do cupom deve conter exatamente 6 caracteres alfanumericos apos remover caracteres especiais")
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
