package com.byuen.service;

import com.byuen.model.Job;
import com.byuen.model.Person;
import com.byuen.model.request.JobRequest;
import com.byuen.repository.JobRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class JobService {

    private static final Logger log = LoggerFactory.getLogger(JobService.class);

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private PersonService personService;

    public Iterable<Job> getJobs(String limit, boolean active, String sortBy) {
        int intLimit = Integer.parseInt(limit);
        Pageable pageable = PageRequest.of(0, intLimit, Sort.by(Sort.Direction.DESC, "createTimestamp"));

        if (active) {
            List<Job> jobs = jobRepository.findAllValid();
            if (sortBy.equals("bids")) {
                Collections.sort(jobs);
            }
            if (intLimit < jobs.size()) {
                return jobs.subList(0, intLimit);
            }
            return jobs;
        }
        return jobRepository.findAll(pageable).getContent();
    }


    public Job createJob(JobRequest jobRequest) {
        Person person = personService.createPerson(new Person(jobRequest.getFirstName(), jobRequest.getLastName(), jobRequest.getEmail(), jobRequest.getPhone()));
        Job job = new Job(jobRequest.getDescription(), jobRequest.getRequirement(), person);
        job.setCreateTimestamp(LocalDateTime.now());
        job.setExpireTimestamp(LocalDateTime.now().plusDays(1));
        return jobRepository.save(job);

    }


    public Optional getJob(String id) {
        return jobRepository.findById(UUID.fromString(id));
    }
}
