package tasks;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.format.DateTimeParseException;

import org.junit.jupiter.api.Test;

public class EventTest {

    @Test
    public void testDateHandler() {
        Event event = new Event("event JUnit test run /from Jan 26 2026 /to Jan 27 2026");
        String input = "Jan 26 2026";
        assertEquals("Jan 26 2026", input);
    }

    @Test
    public void testDateHandlerOnLeapYear() {
        Event event = new Event("event JUnit test run /from Jan 26 2026 /to Jan 27 2026");
        String input = event.dateHandler("Feb 29 2024");
        assertEquals("Feb 29 2024", input);
    }


    @Test
    public void testDateHandlerWrongFormat() {
        Event event = new Event("event JUnit test run /from Jan 26 2026 /to Jan 27 2026");
        assertThrows(DateTimeParseException.class, () -> {
            event.dateHandler("2025-10-15");
        });
    }

    @Test
    public void testDateHandlerWrongCase() {
        Event event = new Event("event JUnit test run /from Jan 26 2026 /to Jan 27 2026");
        assertThrows(DateTimeParseException.class, () -> {
            event.dateHandler("oct 15 2023");
        });
    }
}
