package com.byuen.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "Bid")
public class Bid implements Comparable {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", columnDefinition = "VARBINARY(16)")
    private UUID id;

    BigDecimal amount;

    @OneToOne
    @JoinColumn(name = "job_id")
    @JsonBackReference
    Job job;

    @OneToOne
    @JoinColumn(name = "bidder_id")
    Person bidder;
    LocalDateTime bidTimestamp;


    public Bid(BigDecimal bidAmount, Job job, Person bidder) {
        this.amount = bidAmount;
        this.job = job;
        this.bidder = bidder;

    }


    @Override
    public int compareTo(Object o) {
        Bid bid = (Bid) o;
        return this.amount.compareTo(bid.amount);
    }
}
