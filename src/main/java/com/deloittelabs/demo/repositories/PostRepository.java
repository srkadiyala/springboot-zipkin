package com.deloittelabs.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.deloittelabs.demo.entities.Post;


public interface PostRepository extends JpaRepository<Post, Integer>
{

}
