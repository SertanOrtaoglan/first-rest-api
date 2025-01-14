package com.in28minutes.rest.webservices.restful_web_services.user;

import java.net.URI;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import jakarta.validation.Valid;

@RestController
public class UserResource {
	
	private UserDaoService service;

	public UserResource(UserDaoService service) {
		super();
		this.service = service;
	}
	
	@GetMapping("/users")
	public List<User> retrieveAllUsers() {
		return service.findAll();
	}
	
	@GetMapping("/users/{id}")
	public User retrieveUser(@PathVariable int id) {
		//Değer 'null' olduğunda beyaz bir sayfa almak yerine kendi hata mesajımızı fırlatmak isteriz. Bunun için şu kodu yazarız;
		User user = service.findOne(id);
		
		if(user == null) {
			throw new UserNotFoundException("id:"+id);  //Kendi yazdığımız bu 'UserNotFoundException'ın "404 Not Found" ile sonuçlanması için oluşturduğumuz 'UserNotFoundException' class'ı içerisine gidip '@ResponseStatus(code = HttpStatus.NOT_FOUND)' annotation'ını eklememiz gerekir.
		}
		
		return user;
	}
	
	@PostMapping("/users")
	public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
		User savedUser = service.save(user);
		
		//Genellikle yeni oluşturulan bir kaynağın URL'sini döndürmek istediğimizde(mesela "/users/4"), kullanmamız gereken belirli bir HTTP header'i vardır. Bu header'e(yani başlığa) "location header" denir. Ve aşağıda yazdığımız ResponseEntity içerisinde yer alan 'created()' methodu parametre olarak bir 'URL location' kabul eder.   
		//URI location -> /users/4   [Buradaki "/users" zaten mevcut kullandığımız URI'dır. Sadece sonuna 'id' ekleyeceğiz. Sonuç olarak "/users/{id}" olması gerekir. Buradaki 'id' değerini ise 'user.getId()' ile değiştireceğiz.
		//  /users/4 => /users/{id}    We will get "created user id" with "user.getId()", then replace with {id} 
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedUser.getId()).toUri();
		return ResponseEntity.created(location).build();
	}
	
	@DeleteMapping("/users/{id}")
	public void deleteUser(@PathVariable int id) {
		service.deleteById(id);
	}
	
	
	
	
	
}

