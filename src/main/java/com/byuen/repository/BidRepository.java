package com.byuen.repository;

import com.byuen.model.Bid;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BidRepository extends CrudRepository<Bid, UUID> {

}
