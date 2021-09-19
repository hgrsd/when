package nl.hgrsd.when;

import org.dmfs.rfc5545.DateTime;
import org.dmfs.rfc5545.recur.InvalidRecurrenceRuleException;

import java.util.List;

public interface OccurrenceGenerator {
        List<DateTime> generateOccurrences(String rrule, DateTime from, DateTime to) throws InvalidRecurrenceRuleException;
}
