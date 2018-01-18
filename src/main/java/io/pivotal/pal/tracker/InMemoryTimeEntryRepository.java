package io.pivotal.pal.tracker;

import java.util.*;

public class InMemoryTimeEntryRepository extends TimeEntryRepository {
    private Map<Long, TimeEntry> timeEntryMap = new HashMap<>();
    private long highestId = 0L;

    @Override
    public TimeEntry create(TimeEntry timeEntry) {
        timeEntry.setId(++highestId);
        timeEntryMap.put(timeEntry.getId(), timeEntry);
        return timeEntry;
    }

    @Override
    public TimeEntry find(long id){
        return timeEntryMap.get(id);
    }

    @Override
    public void delete(long id) {
        timeEntryMap.remove(id);
    }

    @Override
    public List<TimeEntry> list() {
        return new ArrayList<>(timeEntryMap.values());
    }

    @Override
    public TimeEntry update(long id, TimeEntry timeEntry) {
        timeEntry.setId(id);
        timeEntryMap.put(id, timeEntry);
        return timeEntry;
    }
}
