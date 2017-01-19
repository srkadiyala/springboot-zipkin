package com.deloittelabs.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.deloittelabs.demo.entities.EndUser;


public interface EndUserRepository extends JpaRepository<EndUser, Integer>
{

}
