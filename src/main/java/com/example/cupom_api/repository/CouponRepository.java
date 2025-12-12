package com.example.cupom_api.repository;

import com.example.cupom_api.entity.Coupon;
import java.util.Optional;

import com.example.cupom_api.entity.CouponStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponRepository extends JpaRepository<Coupon, Long> {
    Optional<Coupon> findByCode(String code);
    Optional<Coupon> findByIdAndStatusNot(Long id, CouponStatus status);
}
