package nl.hgrsd.when;

import io.quarkus.test.junit.QuarkusTest;
import org.dmfs.rfc5545.DateTime;
import org.dmfs.rfc5545.recur.InvalidRecurrenceRuleException;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class OccurrenceGeneratorImplTest {
    @Inject
    OccurrenceGenerator generator;

    @Test
    void itGeneratesOccurrences() throws InvalidRecurrenceRuleException {
        var result = generator.generateOccurrences(
                "FREQ=DAILY;BYHOUR=8\n",
                DateTime.parse("20210401T080000"),
                DateTime.parse("20210403T070000")
        );
        assertEquals(Arrays.asList(DateTime.parse("20210401T080000"), DateTime.parse("20210402T080000")), result);
    }
}