package nl.hgrsd.when;

import io.quarkus.test.junit.QuarkusTest;
import org.dmfs.rfc5545.recur.InvalidRecurrenceRuleException;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class NaiveOccurrencesRepoTest {

    @Inject
    OccurrencesRepo repo;

    @Test
    void itReturnsOccurrences() throws InvalidRecurrenceRuleException {
        var result = repo.getOccurrences(
                "FREQ=DAILY;BYHOUR=8\n",
                "20210401T080000",
                "20210403T070000"
        );
        assertEquals(Arrays.asList("20210401T080000", "20210402T080000"), result);
    }
}