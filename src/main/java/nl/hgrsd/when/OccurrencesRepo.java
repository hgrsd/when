package nl.hgrsd.when;

import org.dmfs.rfc5545.recur.InvalidRecurrenceRuleException;

import java.util.List;

public interface OccurrencesRepo {
    List<String> getOccurrences(String rrule, String from, String to) throws InvalidRecurrenceRuleException;
}
