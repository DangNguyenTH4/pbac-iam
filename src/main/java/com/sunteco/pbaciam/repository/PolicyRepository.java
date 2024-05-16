package com.sunteco.pbaciam.repository;

import com.sunteco.pbaciam.model.Policy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PolicyRepository extends JpaRepository<Policy,String> {
}
