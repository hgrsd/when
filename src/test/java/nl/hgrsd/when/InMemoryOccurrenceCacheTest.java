package nl.hgrsd.when;

import org.dmfs.rfc5545.DateTime;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryOccurrenceCacheTest {

    @Test
    void itReturnsEmptyOptionalForEmptyCache() {
        var cache = new InMemoryOccurrenceCache();
        var result = cache.getOccurrences(
                "FREQ:DAILY;BYHOUR=8",
                DateTime.parse("20210404T080000"),
                DateTime.parse("20210409T080000")
        );
        assertTrue(result.isEmpty());
    }

    @Test
    void itReturnsEmptyOptionalForCacheWithoutRrule() {
        var cache = new InMemoryOccurrenceCache();
        cache.storeOccurrences(
                "FREQ:WEEKLY;BYHOUR=8",
                DateTime.parse("20210404T080000"),
                DateTime.parse("20210409T080000"),
                List.of(DateTime.parse("20210406T080000"))
        );
        var result = cache.getOccurrences(
                "FREQ:DAILY;BYHOUR=8",
                DateTime.parse("20210404T080000"),
                DateTime.parse("20210409T080000")
        );
        assertTrue(result.isEmpty());
    }

    @Test
    void itReturnsEmptyOptionalForCacheWithSameRruleButPeriodNotFullyCovered() {
        var cache = new InMemoryOccurrenceCache();
        cache.storeOccurrences(
                "FREQ:DAILY;BYHOUR=8",
                DateTime.parse("20210404T080000"),
                DateTime.parse("20210409T080000"),
                Arrays.asList(
                        DateTime.parse("20210404T080000"),
                        DateTime.parse("20210405T080000"),
                        DateTime.parse("20210406T080000"),
                        DateTime.parse("20210407T080000"),
                        DateTime.parse("20210408T080000"),
                        DateTime.parse("20210409T080000")
                )
        );
        var result = cache.getOccurrences(
                "FREQ:DAILY;BYHOUR=8",
                DateTime.parse("20210403T080000"),
                DateTime.parse("20210409T080000")
        );
        assertTrue(result.isEmpty());
    }

    @Test
    void itReturnsOccurrencesWhenRruleAndPeriodMatch() {
        var cache = new InMemoryOccurrenceCache();
        cache.storeOccurrences(
                "FREQ:DAILY;BYHOUR=8",
                DateTime.parse("20210404T080000"),
                DateTime.parse("20210409T080000"),
                Arrays.asList(
                        DateTime.parse("20210404T080000"),
                        DateTime.parse("20210405T080000"),
                        DateTime.parse("20210406T080000"),
                        DateTime.parse("20210407T080000"),
                        DateTime.parse("20210408T080000"),
                        DateTime.parse("20210409T080000")
                )
        );
        var result = cache.getOccurrences(
                "FREQ:DAILY;BYHOUR=8",
                DateTime.parse("20210404T080000"),
                DateTime.parse("20210409T080000")
        );
        assertEquals(
                Arrays.asList(
                        DateTime.parse("20210404T080000"),
                        DateTime.parse("20210405T080000"),
                        DateTime.parse("20210406T080000"),
                        DateTime.parse("20210407T080000"),
                        DateTime.parse("20210408T080000"),
                        DateTime.parse("20210409T080000")
                ),
                result.get()
        );
    }

    @Test
    void itReturnsCorrectOccurrencesWhenRequestingContainedPeriod() {
        var cache = new InMemoryOccurrenceCache();
        cache.storeOccurrences(
                "FREQ:DAILY;BYHOUR=8",
                DateTime.parse("20210404T080000"),
                DateTime.parse("20210409T080000"),
                Arrays.asList(
                        DateTime.parse("20210404T080000"),
                        DateTime.parse("20210405T080000"),
                        DateTime.parse("20210406T080000"),
                        DateTime.parse("20210407T080000"),
                        DateTime.parse("20210408T080000"),
                        DateTime.parse("20210409T080000")
                )
        );
        var result = cache.getOccurrences(
                "FREQ:DAILY;BYHOUR=8",
                DateTime.parse("20210406T080000"),
                DateTime.parse("20210409T075959")
        );
        assertEquals(
                Arrays.asList(
                        DateTime.parse("20210406T080000"),
                        DateTime.parse("20210407T080000"),
                        DateTime.parse("20210408T080000")
                ),
                result.get()
        );
    }

}