package nl.hgrsd.when;

import io.quarkus.test.junit.QuarkusTest;
import org.dmfs.rfc5545.recur.InvalidRecurrenceRuleException;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class CachedOccurrencesRepoTest {

    @Cached
    @Inject
    OccurrencesRepo repo;

    @Test
    void itGeneratesOccurrencesIfUncached() throws InvalidRecurrenceRuleException {
        var result = repo.getOccurrences(
                "FREQ=DAILY;BYHOUR=8",
                "20210401T080000",
                "20210405T080000"
        );
        assertEquals(
                Arrays.asList(
                        "20210401T080000",
                        "20210402T080000",
                        "20210403T080000",
                        "20210404T080000",
                        "20210405T080000"
                ),
                result
        );
    }

    @Test
    void itGeneratesOccurrencesIfCached() throws InvalidRecurrenceRuleException {
        var result = repo.getOccurrences(
                "FREQ=DAILY;BYHOUR=8",
                "20210401T080000",
                "20210405T080000"
        );
        var result2 = repo.getOccurrences(
                "FREQ=DAILY;BYHOUR=8",
                "20210401T080000",
                "20210405T080000"
        );
        assertEquals(
                Arrays.asList(
                        "20210401T080000",
                        "20210402T080000",
                        "20210403T080000",
                        "20210404T080000",
                        "20210405T080000"
                ),
                result
        );
        assertEquals(result2, result);
    }

}