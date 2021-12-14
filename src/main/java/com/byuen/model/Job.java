package com.byuen.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.time.DurationFormatUtils;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "Job")
public class Job implements Comparable {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", columnDefinition = "VARBINARY(16)")
    private UUID id;

    String description;
    String requirement;
    LocalDateTime createTimestamp;
    LocalDateTime expireTimestamp;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "contact_id")
    Person contact;

    @OneToMany(targetEntity = Bid.class,
            mappedBy = "job",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER)
    List<Bid> bids;


    public Job(String description, String requirement, Person contact) {
        this.description = description;
        this.requirement = requirement;
        this.contact = contact;
    }

    @Override
    public int compareTo(Object o) {
        Job job = (Job) o;
        int bids = job.bids.size();
        return (Integer.valueOf(bids)).compareTo(Integer.valueOf(this.bids.size()));
    }


    public boolean isActive() {
        return LocalDateTime.now().isBefore(this.expireTimestamp);
    }


    public String getTimeLeft() {
        if (isActive()) {
            Duration duration = Duration.between(LocalDateTime.now(), this.expireTimestamp);
            return DurationFormatUtils.formatDuration(duration.toMillis(), "H:mm:ss", true);
        }
        return null;
    }


    public Bid getLastBid() {
        if (this.bids != null) {
            if (this.bids.size() > 0) {
                return this.bids.get(this.bids.size() - 1);
            }
        }
        return null;
    }
}
