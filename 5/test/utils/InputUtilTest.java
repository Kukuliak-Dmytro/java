package utils;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;

import static org.junit.jupiter.api.Assertions.*;

public class InputUtilTest {

    @AfterEach
    void tearDown() {
        // Reset to System.in after each test
        InputUtil.setInputStream(System.in);
    }

    @Test
    void testReadInt() {
        InputUtil.setInputStream(new ByteArrayInputStream("42\n".getBytes()));
        int result = InputUtil.readInt("Enter number: ");
        assertEquals(42, result);
    }

    @Test
    void testReadIntWithInvalidInput() {
        InputUtil.setInputStream(new ByteArrayInputStream("abc\n".getBytes()));
        int result = InputUtil.readInt("Enter number: ");
        assertEquals(-1, result);
    }

    @Test
    void testReadIntWithRange() {
        InputUtil.setInputStream(new ByteArrayInputStream("5\n".getBytes()));
        int result = InputUtil.readInt("Enter number (1-10): ", 1, 10);
        assertEquals(5, result);
    }

    @Test
    void testReadIntWithRangeOutOfBounds() {
        InputUtil.setInputStream(new ByteArrayInputStream("15\n".getBytes()));
        int result = InputUtil.readInt("Enter number (1-10): ", 1, 10);
        assertEquals(-1, result);
    }

    @Test
    void testReadIntWithRangeBelowMin() {
        InputUtil.setInputStream(new ByteArrayInputStream("0\n".getBytes()));
        int result = InputUtil.readInt("Enter number (1-10): ", 1, 10);
        assertEquals(-1, result);
    }

    @Test
    void testReadString() {
        InputUtil.setInputStream(new ByteArrayInputStream("Hello World\n".getBytes()));
        String result = InputUtil.readString("Enter text: ");
        assertEquals("Hello World", result);
    }

    @Test
    void testReadFloat() {
        InputUtil.setInputStream(new ByteArrayInputStream("3.14\n".getBytes()));
        float result = InputUtil.readFloat("Enter float: ");
        assertEquals(3.14f, result, 0.001f);
    }

    @Test
    void testReadFloatWithInvalidInput() {
        InputUtil.setInputStream(new ByteArrayInputStream("abc\n".getBytes()));
        float result = InputUtil.readFloat("Enter float: ");
        assertEquals(-1.0f, result);
    }

    @Test
    void testReadFloatWithRange() {
        InputUtil.setInputStream(new ByteArrayInputStream("5.5\n".getBytes()));
        float result = InputUtil.readFloat("Enter float (1-10): ", 1.0f, 10.0f);
        assertEquals(5.5f, result, 0.001f);
    }

    @Test
    void testReadFloatWithRangeOutOfBounds() {
        InputUtil.setInputStream(new ByteArrayInputStream("15.5\n".getBytes()));
        float result = InputUtil.readFloat("Enter float (1-10): ", 1.0f, 10.0f);
        assertEquals(-1.0f, result);
    }

    @Test
    void testReadFloatWithRangeBelowMin() {
        InputUtil.setInputStream(new ByteArrayInputStream("0.5\n".getBytes()));
        float result = InputUtil.readFloat("Enter float (1-10): ", 1.0f, 10.0f);
        assertEquals(-1.0f, result);
    }

    @Test
    void testReadLine() {
        InputUtil.setInputStream(new ByteArrayInputStream("  test line  \n".getBytes()));
        String result = InputUtil.readLine();
        assertEquals("test line", result); // Should be trimmed
    }
}
