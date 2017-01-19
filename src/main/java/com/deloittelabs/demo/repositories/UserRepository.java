/**
 * 
 */
package com.deloittelabs.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.deloittelabs.demo.entities.User;


public interface UserRepository extends JpaRepository<User, Integer>
{

}
