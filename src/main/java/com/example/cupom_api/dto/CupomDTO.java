package com.example.cupom_api.dto;

import com.example.cupom_api.entity.Coupon;
import com.example.cupom_api.entity.CouponStatus;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CupomDTO(
    Long id,
    String code,
    String description,
    BigDecimal discountValue,
    LocalDate expirationDate,
    boolean published,
    CouponStatus status
){
    public static CupomDTO fromEntity(Coupon entity) {
        return new CupomDTO(
                entity.getId(),
                entity.getCode(),
                entity.getDescription(),
                entity.getDiscountValue(),
                entity.getExpirationDate(),
                entity.isPublished(),
                entity.getStatus()
        );
    }
}
