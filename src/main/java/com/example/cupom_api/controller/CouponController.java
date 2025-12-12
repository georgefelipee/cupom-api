package com.example.cupom_api.controller;

import com.example.cupom_api.dto.CouponRequest;
import com.example.cupom_api.entity.Coupon;
import com.example.cupom_api.service.CouponService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/coupons")
public class CouponController {

    private final CouponService couponService;

    public CouponController(CouponService couponService) {
        this.couponService = couponService;
    }

    @PostMapping
    public ResponseEntity<Coupon> create(@Valid @RequestBody CouponRequest request) {
        Coupon saved = couponService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBydId(@PathVariable Long id)  {
        couponService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
