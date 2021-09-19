package nl.hgrsd.when;

import org.dmfs.rfc5545.DateTime;
import org.dmfs.rfc5545.recur.InvalidRecurrenceRuleException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;

@ApplicationScoped
@Cached
public class CachedOccurrencesRepo implements OccurrencesRepo {

    @Inject
    OccurrenceCache cache;

    @Inject
    OccurrenceGenerator generator;


    @Override
    public List<String> getOccurrences(String rrule, String from, String to) throws InvalidRecurrenceRuleException {
        var parsedFrom = DateTime.parse(from);
        var parsedTo = DateTime.parse(to);
        var cachedOccurrences = cache.getOccurrences(rrule, parsedFrom, parsedTo);

        List<DateTime> occurrences;
        if (cachedOccurrences.isPresent()) {
            occurrences = cachedOccurrences.get();
        } else {
            var generatedOccurrences = generator.generateOccurrences(rrule, parsedFrom, parsedTo);
            cache.storeOccurrences(rrule, parsedFrom, parsedTo, generatedOccurrences);
            occurrences = generatedOccurrences;
        }

        return occurrences
                .stream()
                .map(DateTime::toString)
                .toList();
    }
}
