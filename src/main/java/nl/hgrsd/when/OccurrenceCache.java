package nl.hgrsd.when;

import org.dmfs.rfc5545.DateTime;

import java.util.List;
import java.util.Optional;

public interface OccurrenceCache {
    void storeOccurrences(String rrule, DateTime from, DateTime to, List<DateTime> occurrences);
    Optional<List<DateTime>> getOccurrences(String rrule, DateTime from, DateTime to);
}
