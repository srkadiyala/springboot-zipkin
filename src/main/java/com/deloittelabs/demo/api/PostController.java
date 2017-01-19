package com.deloittelabs.demo.api;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.sleuth.Sampler;
import org.springframework.cloud.sleuth.sampler.AlwaysSampler;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.deloittelabs.demo.entities.Comment;
import com.deloittelabs.demo.entities.Post;
import com.deloittelabs.demo.repositories.CommentRepository;
import com.deloittelabs.demo.repositories.PostRepository;

@RestController
@RequestMapping(value="/posts")
public class PostController
{
    @Autowired
    PostRepository postRepository;
    
    @Autowired
    CommentRepository commentRepository;
    
    /** Sleuth automatically adds trace interceptors when in the classpath */
    @Bean
    public RestTemplate restTemplate() {
      return new RestTemplate();
    }

    /** Sleuth won't start a trace unless a sampler bean is configured */
    @Bean
    public Sampler defaultSampler() {
      return new AlwaysSampler();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value="", method=RequestMethod.POST)
    public Post createPost(@RequestBody Post post)
    {
        return postRepository.save(post);
    }
    
    /*@RequestMapping(value="", method=RequestMethod.POST)
    public ResponseEntity<Post> createPost(@RequestBody @Valid Post post, BindingResult result)
    {
        if(result.hasErrors()){
            return new ResponseEntity<>(post, HttpStatus.BAD_REQUEST);
        }
        Post savedPost = postRepository.save(post);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("MyResponseHeader", "MyValue");
        return new ResponseEntity<>(savedPost, responseHeaders, HttpStatus.CREATED);
    }*/
    
    @RequestMapping(value="", method=RequestMethod.GET)
    public List<Post> listPosts()
    {
      
    	return postRepository.findAll();
    }
    
    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    public Post getPost(@PathVariable("id") Integer id)
    {
        Post post = postRepository.findOne(id);
        if(post == null){
            throw new ResourceNotFoundException("No post found with id="+id);
        }
        return post;
    }
    
    @RequestMapping(value="/{id}", method=RequestMethod.PUT)
    public Post updatePost(@PathVariable("id") Integer id, @RequestBody @Valid Post post)
    {
        Post oldPost = postRepository.findOne(id);
        if(oldPost == null){
            throw new ResourceNotFoundException("No post found with id="+id);
        }
        return  postRepository.save(post);
    }
    
    @RequestMapping(value="/{id}", method=RequestMethod.DELETE)
    public void deletePost(@PathVariable("id") Integer id)
    {
        Post post = postRepository.findOne(id);
        if(post == null){
            throw new ResourceNotFoundException("No post found with id="+id);
        }
        postRepository.delete(id);        
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value="/{id}/comments", method=RequestMethod.POST)
    public void createPostComment(@PathVariable("id") Integer id, @RequestBody Comment comment)
    {
        Post post = postRepository.findOne(id);
        if(post == null){
            throw new ResourceNotFoundException("No post found with id="+id);
        }
        post.getComments().add(comment);
        //postRepository.save(post);
    }
    
    @RequestMapping(value="/{postId}/comments/{commentId}", method=RequestMethod.GET)
    public Comment getPostComment(@PathVariable("postId") Integer postId, 
            @PathVariable("commentId") Integer commentId)
    {
    	Comment comment = commentRepository.findOne(commentId);
        if(comment == null){
            throw new ResourceNotFoundException("No comment found with id="+commentId);
        }
        return comment;
    }
    
    @RequestMapping(value="/{postId}/comments/{commentId}", method=RequestMethod.POST)
    public void deletePostComment(@PathVariable("postId") Integer postId, 
                                  @PathVariable("commentId") Integer commentId)
    {
        commentRepository.delete(commentId);
    }
    
    
}
