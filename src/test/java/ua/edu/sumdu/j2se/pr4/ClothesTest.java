package ua.edu.sumdu.j2se.pr4;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ClothesTest {

    @Test
    void shouldThrowInvalidFieldValueExceptionWhenPriceIsNegative() {
        // Тепер використовуємо конкретний клас Pants замість абстрактного Clothes
        assertThrows(InvalidFieldValueException.class, () -> {
            new Pants("Nike", Size.M, -500, 100);
        }, "Повинно кидати InvalidFieldValueException при від'ємній ціні");
    }

    @Test
    void shouldThrowInvalidFieldValueExceptionWhenBrandIsEmpty() {
        Pants p = new Pants("Nike", Size.M, 500, 100);
        
        assertThrows(InvalidFieldValueException.class, () -> {
            p.setBrand("");
        }, "Повинно кидати InvalidFieldValueException при порожньому бренді");
    }
}
