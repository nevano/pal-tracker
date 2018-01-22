package io.pivotal.pal.tracker;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class TimeEntryHealthIndicator implements HealthIndicator {
    private TimeEntryRepository timeEntryRepository;

    public TimeEntryHealthIndicator(TimeEntryRepository timeEntryRepository) {
        this.timeEntryRepository = timeEntryRepository;
    }

    @Override
    public Health health() {
        int currentTimeEntries = timeEntryRepository.list().size();
        Health.Builder builder = new Health.Builder();
        if (currentTimeEntries < 5) {
            builder.up();
        } else {
            builder.down();
        }
        return builder.build();
    }
}
