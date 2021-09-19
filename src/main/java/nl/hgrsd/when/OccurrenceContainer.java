package nl.hgrsd.when;

import org.dmfs.rfc5545.DateTime;

import java.util.List;

public class OccurrenceContainer {
    private final DateTime from;
    private final DateTime to;


    List<DateTime> occurrences;

    public OccurrenceContainer(DateTime from, DateTime to, List<DateTime> occurrences) {
        this.from = from;
        this.to = to;
        this.occurrences = occurrences;
    }

    public boolean containsPeriod(DateTime from, DateTime to) {
        return this.from.getTimestamp() <= from.getTimestamp()
                && this.to.getTimestamp() >= to.getTimestamp();
    }

    public List<DateTime> getForPeriod(DateTime from, DateTime to) {
        return occurrences.stream()
                .filter(occ -> occ.getTimestamp() >= from.getTimestamp() && occ.getTimestamp() <= to.getTimestamp())
                .toList();
    }
}
