package com.byuen.controller;

import com.byuen.model.Job;
import com.byuen.model.request.BidRequest;
import com.byuen.model.request.JobRequest;
import com.byuen.service.BidService;
import com.byuen.service.JobService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Optional;

@RestController
@RequestMapping("/jobs")
@Api(value = "Jobs Controller", produces = MediaType.APPLICATION_JSON_VALUE, tags = {"Job API"})
public class JobController {

    private static final Logger log = LoggerFactory.getLogger(JobController.class);

    @Autowired
    private JobService jobService;

    @Autowired
    private BidService bidService;

    @RequestMapping(method = RequestMethod.GET, produces = {"application/json"})
    @ApiOperation(value = "Get list of Jobs", response = Iterable.class, tags = "Jobs API")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ok", response = Job.class),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Unexpected Error")})
    public ResponseEntity<?> getJobs(@RequestParam(required = false, defaultValue = "10") String limit,
                                     @RequestParam(required = false, defaultValue = "false") boolean active,
                                     @RequestParam(required = false, defaultValue = "") String sortBy) {

        Iterable<Job> jobs = jobService.getJobs(limit, active, sortBy);

        if (jobs.iterator().next() != null) {
            return ResponseEntity.ok(jobs);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @RequestMapping(method = RequestMethod.GET, path = "/{id}", produces = {"application/json"})
    @ApiOperation(value = "Get list of Jobs", response = Iterable.class, tags = "Jobs API")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ok", response = Job.class),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Unexpected Error")})
    public ResponseEntity<?> getJob(@PathVariable("id") String id) {

        Optional job = jobService.getJob(id);

        if (job.isPresent()) {
            return ResponseEntity.ok(job.get());
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @RequestMapping(method = RequestMethod.POST, consumes = {"application/json"}, produces = {"application/json"})
    @ApiOperation(value = "Create job", response = Iterable.class, tags = "Jobs API")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ok", response = Job.class),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Unexpected Error")})
    public ResponseEntity<Job> createJob(@RequestBody JobRequest jobRequest) {

        Job job = jobService.createJob(jobRequest);
        return new ResponseEntity<Job>(job, HttpStatus.CREATED);
    }


    @RequestMapping(method = RequestMethod.POST, path = "/{id}/bid", consumes = {"application/json"}, produces = {"application/json"})
    @ApiOperation(value = "Create job", response = Iterable.class, tags = "Jobs API")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ok", response = Job.class),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Unexpected Error")})
    public ResponseEntity<?> createBid(@PathVariable("id") String id, @RequestBody BidRequest bidRequest) {

        Optional optionalJob = jobService.getJob(id);


        if (optionalJob.isPresent()) {

            Job job = (Job) optionalJob.get();

            if(!job.isActive()){
                return ResponseEntity.status(HttpStatus.CONFLICT).body("job is expired");
            }

            if((new BigDecimal(bidRequest.getBid())).compareTo(job.getLastBid().getAmount()) > 0 ){
                return ResponseEntity.status(HttpStatus.CONFLICT).body("bid to higher than lowest bid");
            }

            return new ResponseEntity<Job>(bidService.createBid((Job) job, bidRequest), HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


}
