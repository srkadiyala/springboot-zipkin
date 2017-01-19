package com.deloittelabs.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.deloittelabs.demo.entities.Comment;


public interface CommentRepository extends JpaRepository<Comment, Integer>
{

}
