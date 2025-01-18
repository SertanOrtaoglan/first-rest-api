package com.in28minutes.rest.webservices.restful_web_services.user;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.in28minutes.rest.webservices.restful_web_services.jpa.PostRepository;
import com.in28minutes.rest.webservices.restful_web_services.jpa.UserRepository;

import jakarta.validation.Valid;

@RestController
public class UserJpaResource {
	
	private UserRepository repository;
	
	private PostRepository postRepository;

	public UserJpaResource(UserRepository repository, PostRepository postRepository) {
		this.repository = repository;
		this.postRepository = postRepository;
	}
	
	
	@GetMapping("/jpa/users")
	public List<User> retrieveAllUsers() {
		return repository.findAll();
	}
	
	
	@GetMapping("/jpa/users/{id}")
	public EntityModel<User> retrieveUser(@PathVariable int id) {
		
		Optional<User> user = repository.findById(id);
		
		if(user.isEmpty()) {
			throw new UserNotFoundException("id:"+id); 
		}
		
		EntityModel<User> entityModel = EntityModel.of(user.get());   //'user.get()' yazıp kullanıcıyı yukarıdaki Optional'dan aldık.
		
		WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).retrieveAllUsers()); 
		
		entityModel.add(link.withRel("all-users"));
		
		return entityModel;
	}
	
	
	@PostMapping("/jpa/users")
	public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
		User savedUser = repository.save(user);
		  
		//URI location -> /jap/users/4   [Buradaki "/users" zaten mevcut kullandığımız URI'dır. Sadece sonuna 'id' ekleyeceğiz. Sonuç olarak "/jpa/users/{id}" olması gerekir. Buradaki 'id' değerini ise 'user.getId()' ile değiştireceğiz. 
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedUser.getId()).toUri();
		return ResponseEntity.created(location).build();
	}
	
	
	@DeleteMapping("/jpa/users/{id}")
	public void deleteUser(@PathVariable int id) {
		repository.deleteById(id);
	}
	
	
	//Belirli bir User'ın tüm Post'larını alma işlemi
	@GetMapping("/jpa/users/{id}/posts")
	public List<Post> retrievePostsForUser(@PathVariable int id) {
		Optional<User> user = repository.findById(id);
		
		if(user.isEmpty()) {
			throw new UserNotFoundException("id:"+id); 
		}
		
		return user.get().getPosts();  //Böylece belirli bir 'User'ın, 'Post'larının listesini(yani tüm 'Post'larını) döndürürüz. 
		
	}
	
	
	//Belirli bir User için yeni bir 'Post' oluşturma işlemi
	@PostMapping("/jpa/users/{id}/posts")
	public ResponseEntity<Object> createPostForUser(@PathVariable int id, @Valid @RequestBody Post post) {
		Optional<User> user = repository.findById(id);
		
		if(user.isEmpty()) {
			throw new UserNotFoundException("id:"+id); 
		}
		
		post.setUser(user.get());   //Böylece path'den id'si girilen 'user'ı buluyoruz ve ardından o 'user'ı, 'Post'un içine set ediyoruz.
		
		Post savedPost = postRepository.save(post);   //Daha sonra RequestBody'den girilen 'post'u, 'postRepository'ye kaydediyoruz.
		
		//Yeni girilen 'post'u kaydettikten sonra, bunun için bir URI oluşturabiliriz;
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{postId}").buildAndExpand(savedPost.getId()).toUri();
		return ResponseEntity.created(location).build();
		
	}
	
	
	//Kendi eklediklerim
	//Belirli bir User için belirli bir 'Post'un ayrıntılarını alma işlemi
	@GetMapping("/jpa/users/{id}/posts/{postId}")
	public EntityModel<Post> retrieveOnePostForUser(@PathVariable int id, @PathVariable int postId) {
		
		Optional<Post> post = postRepository.findByIdAndUserId(postId, id);
		
		if(post.isEmpty()) {
			throw new PostNotFoundException("Post id: " + postId + " not found for User id: " + id); 
		}
		
		EntityModel<Post> entityModel = EntityModel.of(post.get());
		
		WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).retrievePostsForUser(id)); 
		
		entityModel.add(link.withRel("all-posts"));
		
		return entityModel;
	}
	
	
	
	
	
	
	
	
	
	
	
}

