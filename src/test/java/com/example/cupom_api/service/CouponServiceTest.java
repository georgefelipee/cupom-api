package com.example.cupom_api.service;

import com.example.cupom_api.dto.CouponRequest;
import com.example.cupom_api.entity.Coupon;
import com.example.cupom_api.entity.CouponStatus;
import com.example.cupom_api.repository.CouponRepository;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.example.cupom_api.shared.CodeSanitizer;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CouponServiceTest {

    @Mock
    private CouponRepository couponRepository;

    @InjectMocks
    private CouponService couponService;

    @Captor
    private ArgumentCaptor<Coupon> couponArgumentCaptor;

    private Validator validator;

    @BeforeEach
    void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Nested
    class CreateCouponTests {

        @Test
        @DisplayName("Deve criar um cupom com codigo valido")
        void deveCriarCupomComCodigoValido() {
            // arrange
            when(couponRepository.save(couponArgumentCaptor.capture()))
                    .thenAnswer(inv -> {
                        Coupon toSave = inv.getArgument(0, Coupon.class);
                        toSave.setId(1L);
                        return toSave;
                    });
            var input = new CouponRequest(
                    "ABC123",
                    "Cupom de teste",
                    BigDecimal.valueOf(10.0),
                    LocalDate.now().plusDays(1),
                    true
            );

            // act
            var output = couponService.create(input);

            // assert
            assertNotNull(output);
            assertEquals(input.getCode(), couponArgumentCaptor.getValue().getCode());
            assertEquals(input.getDescription(), couponArgumentCaptor.getValue().getDescription());
            assertEquals(BigDecimal.valueOf(10.0).setScale(2, RoundingMode.HALF_UP), couponArgumentCaptor.getValue().getDiscountValue());
            assertEquals(CouponStatus.ACTIVE, couponArgumentCaptor.getValue().getStatus());
        }

        @Test
        @DisplayName("Deve criar um cupom com codigo contendo caracteres especiais")
        void deveCriarCupomComCodigoCaracterEspecial(){
            when(couponRepository.save(couponArgumentCaptor.capture()))
                    .thenAnswer(inv -> {
                        Coupon toSave = inv.getArgument(0, Coupon.class);
                        toSave.setId(1L);
                        return toSave;
                    });

            var codeWithSpecialChars = "A@B#C$1%2^3&";
            var codigoSanitizado = CodeSanitizer.sanitize(codeWithSpecialChars);
            var input = new CouponRequest(
                    codeWithSpecialChars,
                    "Cupom de teste",
                    BigDecimal.valueOf(10.0),
                    LocalDate.now().plusDays(1),
                    true
            );

            // act
            var output = couponService.create(input);
            // assert
            assertNotNull(output);
            assertEquals(codigoSanitizado, output.getCode());
            assertEquals(codigoSanitizado, couponArgumentCaptor.getValue().getCode());
            assertEquals(BigDecimal.valueOf(10.0).setScale(2, RoundingMode.HALF_UP), couponArgumentCaptor.getValue().getDiscountValue());
        }

        @Test
        @DisplayName("Deve vim um erro no form ao criar um cupom com o atributo code com caracteres especiais e menos de 6 digitos validos")
        void deveVimErroAoCriarCupomComCodigoInvalido(){
            var codeWithSpecialChars = "A@B#1$";
            var input = new CouponRequest(
                    codeWithSpecialChars,
                    "Cupom de teste",
                    BigDecimal.valueOf(10.0),
                    LocalDate.now().plusDays(1),
                    true
            );

            var violations = validator.validate(input);

            assertFalse(violations.isEmpty());
            assertTrue(
                    violations.stream()
                            .anyMatch(v -> "Codigo do cupom deve conter exatamente 6 caracteres alfanumericos apos remover caracteres especiais"
                                    .equals(v.getMessage()))
            );
        }

        @Test
        @DisplayName("Deve vim um erro no form ao criar um cupom com o atributo discountValue com valor menor que 0.5")
        void deveVimErroAoCriarCupomComDiscontValuePequeno(){
            var input = new CouponRequest(
                    "ABC123",
                    "Cupom de teste",
                    BigDecimal.valueOf(0.49),
                    LocalDate.now().plusDays(1),
                    true
            );

            var violations = validator.validate(input);

            assertFalse(violations.isEmpty());
            assertTrue(
                    violations.stream()
                            .anyMatch(v -> "Valor de desconto deve ser no minimo 0.5"
                                    .equals(v.getMessage()))
            );
        }

        @Test
        @DisplayName("Deve vim um erro no form ao criar um cupom com o atributo expirationDate no passado")
        void deveVimErroAoCriarCupomComExpirationDateNoPassado(){
            var input = new CouponRequest(
                    "ABC123",
                    "Cupom de teste",
                    BigDecimal.valueOf(10.0),
                    LocalDate.now().minusDays(1),
                    true
            );

            var violations = validator.validate(input);

            assertFalse(violations.isEmpty());
            assertTrue(
                    violations.stream()
                            .anyMatch(v -> "Data de expiracao nao pode estar no passado"
                                    .equals(v.getMessage()))
            );
        }

    }


}
