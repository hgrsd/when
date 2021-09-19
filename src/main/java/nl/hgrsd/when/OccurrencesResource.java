package nl.hgrsd.when;

import org.dmfs.rfc5545.recur.InvalidRecurrenceRuleException;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/occurrences")
public class OccurrencesResource {

    @Inject
    @Cached
    OccurrencesRepo repo;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<String> getOccurrences(@QueryParam("rrule") String rrule,
                                       @QueryParam("from") String from,
                                       @QueryParam("to") String to)
            throws InvalidRecurrenceRuleException {
        return repo.getOccurrences(rrule, from, to);
    }
}