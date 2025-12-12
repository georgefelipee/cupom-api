package com.example.cupom_api.service;

import com.example.cupom_api.dto.CouponRequest;
import com.example.cupom_api.dto.CupomDTO;
import com.example.cupom_api.entity.Coupon;
import com.example.cupom_api.entity.CouponStatus;
import com.example.cupom_api.repository.CouponRepository;
import jakarta.validation.Valid;
import java.math.RoundingMode;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
public class CouponService {

    private final CouponRepository couponRepository;

    public CouponService(CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }

    @Transactional
    public CupomDTO create(@Valid CouponRequest request) {
        String sanitizedCode = request.getSanitizedCode();

        Coupon coupon = new Coupon();
        coupon.setCode(sanitizedCode);
        coupon.setDescription(request.getDescription());
        coupon.setDiscountValue(request.getDiscountValue().setScale(2, RoundingMode.HALF_UP));
        coupon.setExpirationDate(request.getExpirationDate());
        coupon.setPublished(request.isPublished());
        coupon.setStatus(CouponStatus.ACTIVE);

        return CupomDTO.fromEntity(couponRepository.save(coupon));
    }


    @Transactional
    public void deleteById(Long id) {
        Coupon coupon = couponRepository
                .findByIdAndStatusNot(id, CouponStatus.DELETED)
                .orElseThrow(() -> new IllegalArgumentException("Cupom nao encontrado com id: " + id));

        coupon.setStatus(CouponStatus.DELETED);
        couponRepository.save(coupon);
    }
}
