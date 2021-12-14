package com.byuen.config;

import com.byuen.model.Bid;
import com.byuen.model.Job;
import com.byuen.model.Person;
import com.byuen.model.request.JobRequest;
import com.byuen.service.BidService;
import com.byuen.service.JobService;
import com.byuen.service.PersonService;
import com.github.javafaker.Faker;
import com.thedeanda.lorem.Lorem;
import com.thedeanda.lorem.LoremIpsum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.concurrent.ThreadLocalRandom;


@Component
public class StartupApplicationListener implements ApplicationListener<ContextRefreshedEvent> {

    private static final Logger log = LoggerFactory.getLogger(StartupApplicationListener.class);

    @Autowired
    private JobService jobService;

    @Autowired
    private PersonService personService;

    @Autowired
    private BidService bidService;


    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        int seeds = 100;
        Faker faker = new Faker();
        Lorem lorem = LoremIpsum.getInstance();

        for (int i = 0; i < seeds; i++) {
            Job job = createJob(faker.name().firstName(), faker.name().lastName(), faker.funnyName().name() + "@gmail.com", "555-555-1234",
                    lorem.getWords(5, 10), lorem.getWords(5, 10));
            createBids(job);
        }
    }


    private Job createJob(String firstName, String lastName, String email, String phone, String description, String requirement) {
        JobRequest jobRequest = new JobRequest(firstName, lastName, email, phone, description, requirement);
        return jobService.createJob(jobRequest);
    }


    private void createBids(Job job) {
        Faker faker = new Faker();
        int randomNum = ThreadLocalRandom.current().nextInt(5, 30);
        BigDecimal bidAmount = new BigDecimal(90.00);
        for (int i = 0; i < randomNum; i++) {
            Person bidder = personService.createPerson(new Person(faker.name().firstName(), faker.name().lastName(), faker.name().firstName() + "@gmail.com", "555-555-1234"));
            Bid bid = new Bid(bidAmount.subtract(new BigDecimal(i * .50)), job, bidder);
            bidService.createBid(bid);
        }
    }
}
