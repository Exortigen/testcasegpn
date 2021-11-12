package com.testcase.testcasegpn.repository;

import com.testcase.testcasegpn.entity.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestRepository extends JpaRepository<Request, String> {

}
