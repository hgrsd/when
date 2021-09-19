package nl.hgrsd.when;

import org.dmfs.rfc5545.DateTime;
import org.dmfs.rfc5545.recur.InvalidRecurrenceRuleException;
import org.dmfs.rfc5545.recur.RecurrenceRule;
import org.dmfs.rfc5545.recur.RecurrenceRuleIterator;

import javax.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class OccurrenceGeneratorImpl implements OccurrenceGenerator {
    public List<DateTime> generateOccurrences(String rrule, DateTime from, DateTime to) throws InvalidRecurrenceRuleException {
        RecurrenceRule parsedRrule = new RecurrenceRule(rrule);
        RecurrenceRuleIterator iter = parsedRrule.iterator(from);

        List<DateTime> occurrences = new ArrayList<>();
        while (iter.hasNext()) {
            DateTime next = iter.nextDateTime();
            if (next.after(to)) {
                return occurrences;
            }
            occurrences.add(next);
        }

        return occurrences;
    }

}
