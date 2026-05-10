package ua.edu.sumdu.j2se.pr4;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CustomExceptionsTest {

    @Test
    void shouldThrowInvalidFieldValueExceptionWhenPriceIsNegative() {
        // Перевіряємо кидання нашого власного винятку при від'ємній ціні
        assertThrows(InvalidFieldValueException.class, () -> {
            new Pants("Nike", Size.M, -500, 100);
        });
    }

    @Test
    void shouldThrowObjectNotFoundExceptionWhenDeletingNonExistingObject() {
        Store store = new Store();
        Clothes cl = new Shirts("Zara", Size.S, 500, "Short");
        StoreItem item = new StoreItem(cl, 1);

        // Перевіряємо кидання нашого власного винятку при видаленні товару з порожнього магазину
        assertThrows(ObjectNotFoundException.class, () -> {
            store.delete(item);
        });
    }
}
