package io.pivotal.pal.tracker;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TimeEntryController {
    private TimeEntryRepository timeEntryRepository;

    public TimeEntryController(TimeEntryRepository timeEntryRepository) {
        this.timeEntryRepository = timeEntryRepository;
    }

    @PostMapping("/time-entries")
    @ResponseBody
    public ResponseEntity create(@RequestBody TimeEntry timeEntry) {
        System.out.println("TIME ENTRY Controller: " + timeEntry);
        ResponseEntity response =  new ResponseEntity(timeEntryRepository.create(timeEntry), HttpStatus.CREATED);
        return response;
    }

    @GetMapping("/time-entries/{id}")
    @ResponseBody
    public ResponseEntity<TimeEntry> read(@PathVariable long id) {
        TimeEntry existingTimeEntry = timeEntryRepository.find(id);
        ResponseEntity response;
        if (existingTimeEntry != null) {
            response = new ResponseEntity(existingTimeEntry, HttpStatus.OK);
        } else {
            response = new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return response;
    }

    @GetMapping("/time-entries")
    @ResponseBody
    public ResponseEntity<List<TimeEntry>> list() {
        ResponseEntity<List<TimeEntry>> responseEntity = new ResponseEntity(timeEntryRepository.list(), HttpStatus.OK);
        return responseEntity;
    }

    @PutMapping("/time-entries/{id}")
    @ResponseBody
    public ResponseEntity update(@PathVariable long id, @RequestBody TimeEntry timeEntry) {
        TimeEntry existingTimeEntry = timeEntryRepository.update(id, timeEntry);
        ResponseEntity response;
        if (existingTimeEntry != null) {
            response = new ResponseEntity(existingTimeEntry, HttpStatus.OK);
        } else {
            response = new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return response;
    }

    @DeleteMapping("/time-entries/{id}")
    @ResponseBody
    public ResponseEntity<TimeEntry> delete(@PathVariable long id) {
        timeEntryRepository.delete(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
