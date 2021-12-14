package com.byuen.service;

import com.byuen.model.Bid;
import com.byuen.model.Job;
import com.byuen.model.Person;
import com.byuen.model.request.BidRequest;
import com.byuen.repository.BidRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class BidService {

    @Autowired
    private BidRepository bidRepository;

    @Autowired
    private PersonService personService;

    @Autowired
    private JobService jobService;

    @Autowired
    private BidService bidService;


    public Bid createBid(Bid bidRequest) {
        bidRequest.setBidTimestamp(LocalDateTime.now());
        return bidRepository.save(bidRequest);
    }


    public Job createBid(Job job, BidRequest bidRequest) {

        //TODO: throw error
        if(!job.isActive()){
            return null;
        }

        //TODO: throw error
        if((new BigDecimal(bidRequest.getBid())).compareTo(job.getLastBid().getAmount()) > 0 ){
            return null;

        }
        Person bidder = personService.createPerson(new Person(bidRequest.getFirstName(), bidRequest.getLastName(), bidRequest.getEmail(), bidRequest.getPhone()));
        Bid bid = new Bid(new BigDecimal(bidRequest.getBid()), job, bidder);
        bid.setBidTimestamp(LocalDateTime.now());
        bidRepository.save(bid);
        return job;
    }
}
