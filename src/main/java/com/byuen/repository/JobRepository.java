package com.byuen.repository;

import com.byuen.model.Job;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface JobRepository extends PagingAndSortingRepository<Job, UUID> {

    Page<Job> findAll(Pageable pageable);

    @Query("SELECT j FROM Job j WHERE CURRENT_DATE < j.expireTimestamp")
    List findAllValid();


}
