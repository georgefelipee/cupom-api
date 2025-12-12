package com.example.cupom_api.controller;

import com.example.cupom_api.dto.CouponRequest;
import com.example.cupom_api.dto.CupomDTO;
import com.example.cupom_api.entity.Coupon;
import com.example.cupom_api.service.CouponService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/coupons")
@Tag(name = "Coupons", description = "Operacoes de gerenciamento de cupons")
public class CouponController {

    private final CouponService couponService;

    public CouponController(CouponService couponService) {
        this.couponService = couponService;
    }

    @Operation(summary = "Cria um novo cupom", description = "Aplica saneamento no codigo e valida as regras de negocio antes de persistir")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Cupom criado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Coupon.class))),
            @ApiResponse(responseCode = "400", description = "Requisicao invalida (erros de validacao ou negocio)",
                    content = @Content(mediaType = "application/json"))
    })
    @PostMapping
    public CupomDTO create(@Valid @RequestBody CouponRequest request) {
        return couponService.create(request);
    }

    @Operation(summary = "Remove logicamente um cupom", description = "Marca o cupom como DELETED (soft delete)")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Cupom deletado"),
            @ApiResponse(responseCode = "400", description = "Cupom inexistente ou invalido",
                    content = @Content(mediaType = "application/json"))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBydId(@PathVariable Long id)  {
        couponService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
