package nl.hgrsd.when;

import org.dmfs.rfc5545.DateTime;

import javax.enterprise.context.ApplicationScoped;
import java.util.*;

@ApplicationScoped
public class InMemoryOccurrenceCache implements OccurrenceCache {

    private final HashMap<String, List<OccurrenceContainer>> cache;

    public InMemoryOccurrenceCache() {
        this.cache = new HashMap<>();
    }

    @Override
    public void storeOccurrences(String rrule, DateTime from, DateTime to, List<DateTime> occurrences) {
        var toInsert = new OccurrenceContainer(from, to, occurrences);
        var existing = this.cache.get(rrule);
        if (existing == null) {
            this.cache.put(rrule, List.of(toInsert));
        } else {
            var newElement = this.cache.get(rrule)
                    .stream()
                    .filter(occ -> !occ.containsPeriod(from, to))
                    .toList();
            newElement.add(toInsert);
            this.cache.replace(rrule, newElement);
        }
    }

    @Override
    public Optional<List<DateTime>> getOccurrences(String rrule, DateTime from, DateTime to) {
        return Optional.ofNullable(this.cache.get(rrule))
                .flatMap(occurrenceContainers ->
                        occurrenceContainers
                                .stream()
                                .filter(c -> c.containsPeriod(from, to))
                                .findAny()
                )
                .map(c -> c.getForPeriod(from, to));
    }
}
