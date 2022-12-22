package hr.fer.oprpp1.hw05.crypto;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static hr.fer.oprpp1.hw05.crypto.Util.bytetohex;
import static hr.fer.oprpp1.hw05.crypto.Util.hextobyte;
import static org.junit.jupiter.api.Assertions.*;

public class UtilTest {

    @Test
    void hextobyteTest() {
        assertArrayEquals(new byte[]{1, -82, 34}, hextobyte("01aE22"));
        assertThrows(IllegalArgumentException.class, () -> hextobyte("01aE222"));
        assertThrows(IllegalArgumentException.class, () -> hextobyte("01ag22"));
    }

    @Test
    void bytetohexTest() {
        assertEquals("01ae22", bytetohex(new byte[]{1, -82, 34}));
    }
}