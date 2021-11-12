package com.testcase.testcasegpn.repository;

import com.testcase.testcasegpn.entity.Request;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {
//    @Cacheable(value = "request", key = "#personalhash")
    Optional<Request> findByPersonalhash(long personalhash);
}
