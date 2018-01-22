package io.pivotal.pal.tracker;

import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.boot.actuate.metrics.GaugeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TimeEntryController {
    private TimeEntryRepository timeEntryRepository;
    private final CounterService counter;
    private final GaugeService gauge;

    public TimeEntryController(
            CounterService counter,
            GaugeService gauge,
            TimeEntryRepository timeEntryRepository) {
        this.timeEntryRepository = timeEntryRepository;
        this.counter = counter;
        this.gauge = gauge;
    }

    @PostMapping("/time-entries")
    @ResponseBody
    public ResponseEntity create(@RequestBody TimeEntry timeEntry) {
        counter.increment("TimeEntry.created");
        gauge.submit("timeEntries.count", timeEntryRepository.list().size());
        ResponseEntity response =  new ResponseEntity(timeEntryRepository.create(timeEntry), HttpStatus.CREATED);
        return response;
    }

    @GetMapping("/time-entries/{id}")
    @ResponseBody
    public ResponseEntity<TimeEntry> read(@PathVariable long id) {
        TimeEntry existingTimeEntry = timeEntryRepository.find(id);
        ResponseEntity response;
        if (existingTimeEntry != null) {
            counter.increment("TimeEntry.read");
            response = new ResponseEntity(existingTimeEntry, HttpStatus.OK);
        } else {
            response = new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return response;
    }

    @GetMapping("/time-entries")
    @ResponseBody
    public ResponseEntity<List<TimeEntry>> list() {
        counter.increment("TimeEntry.listed");
        ResponseEntity<List<TimeEntry>> responseEntity = new ResponseEntity(timeEntryRepository.list(), HttpStatus.OK);
        return responseEntity;
    }

    @PutMapping("/time-entries/{id}")
    @ResponseBody
    public ResponseEntity update(@PathVariable long id, @RequestBody TimeEntry timeEntry) {
        TimeEntry existingTimeEntry = timeEntryRepository.update(id, timeEntry);
        ResponseEntity response;
        if (existingTimeEntry != null) {
            counter.increment("TimeEntry.updated");
            response = new ResponseEntity(existingTimeEntry, HttpStatus.OK);
        } else {
            response = new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return response;
    }

    @DeleteMapping("/time-entries/{id}")
    @ResponseBody
    public ResponseEntity<TimeEntry> delete(@PathVariable long id) {
        counter.increment("TimeEntry.deleted");
        gauge.submit("timeEntries.count", timeEntryRepository.list().size());
        timeEntryRepository.delete(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
