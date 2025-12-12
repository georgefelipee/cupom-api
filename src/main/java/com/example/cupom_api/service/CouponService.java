package com.example.cupom_api.service;

import com.example.cupom_api.dto.CouponRequest;
import com.example.cupom_api.entity.Coupon;
import com.example.cupom_api.entity.CouponStatus;
import com.example.cupom_api.repository.CouponRepository;
import java.math.RoundingMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CouponService {

    private final CouponRepository couponRepository;

    public CouponService(CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }

    @Transactional
    public Coupon create(CouponRequest request) {
        String sanitizedCode = request.getSanitizedCode();

        Coupon coupon = new Coupon();
        coupon.setCode(sanitizedCode);
        coupon.setDescription(request.getDescription());
        coupon.setDiscountValue(request.getDiscountValue().setScale(2, RoundingMode.HALF_UP));
        coupon.setExpirationDate(request.getExpirationDate());
        coupon.setPublished(request.isPublished());
        coupon.setStatus(CouponStatus.ACTIVE);

        return couponRepository.save(coupon);
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
