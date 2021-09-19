package nl.hgrsd.when;

import org.dmfs.rfc5545.DateTime;
import org.dmfs.rfc5545.recur.InvalidRecurrenceRuleException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;

@ApplicationScoped
public class NaiveOccurrencesRepo implements OccurrencesRepo {
    @Inject
    OccurrenceGenerator generator;

    @Override
    public List<String> getOccurrences(String rrule, String from, String to) throws InvalidRecurrenceRuleException {
        return generator.generateOccurrences(rrule, DateTime.parse(from), DateTime.parse(to))
                .stream()
                .map(DateTime::toString)
                .toList();
    }
}
