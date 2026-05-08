package ua.edu.sumdu.j2se.pr4;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ClothesTest {

    @Test
    void shouldThrowExceptionWhenInvalidPriceInConstructor() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Clothes("Футболка", "Nike", "M", -10.0);
        }, "Конструктор повинен кидати виняток при від'ємній ціні");
    }

    @Test
    void shouldThrowExceptionWhenTypeIsEmpty() {
        Clothes item = new Clothes("Тип", "Бренд", "L", 100.0);
        assertThrows(IllegalArgumentException.class, () -> {
            item.setType("");
        }, "Сетер повинен кидати виняток при порожньому рядку");
    }
}
