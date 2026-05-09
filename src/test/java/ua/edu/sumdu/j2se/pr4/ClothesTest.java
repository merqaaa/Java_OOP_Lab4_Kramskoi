package ua.edu.sumdu.j2se.pr4;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ClothesTest {
    @Test
    void testInvalidPrice() {
        assertThrows(IllegalArgumentException.class, () -> new Clothes("T", "B", Size.M, -5.0));
    }

    @Test
    void testEmptyType() {
        Clothes c = new Clothes("Тип", "Бренд", Size.L, 100);
        assertThrows(IllegalArgumentException.class, () -> c.setType(""));
    }

    @Test
    void testCopyConstructor() {
        Clothes original = new Clothes("Футболка", "Nike", Size.M, 500);
        Clothes copy = new Clothes(original);
        
        assertEquals(original, copy, "Копія повинна бути рівною оригіналу");
        assertNotSame(original, copy, "Копія повинна бути іншим об'єктом у пам'яті");
    }
}
