package com.in28minutes.rest.webservices.restful_web_services.user;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.net.URI;
import java.util.List;

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
	
	//HATEOAS'ın uygulanması 
	//Yapmak istediğimiz şey 'retrieveUser' methodu içerisine 'retrieveAllUsers' methodunun linkini(/users) yani "http://localhost:8085/users"ı eklemektir.
	//Verileri ve bağlantıları içeren bir response oluşturabilmek için çok çok önemli birkaç HATEOAS konseptinden yararlanacağız. Bunlar "EntityModel" ve "WebMvcLinkBuilder"dır.
	//Kullandığımız her bean'in yapısında değişiklik yapmak istemiyoruz bu yüzden "EntityModel"i kullanacağız. Sonuç olarak 'HATEOAS'ı kullanmak ve bağlantılar eklemek istediğimizde yapacağımız ilk şey 'User'ı, 'EntityModel'e sarmak olmalıdır.
	//Şimdi kod üzerinde düzenlemeleri yapalım;
	@GetMapping("/users/{id}")
	public EntityModel<User> retrieveUser(@PathVariable int id) {
		//Değer 'null' olduğunda beyaz bir sayfa almak yerine kendi hata mesajımızı fırlatmak isteriz. Bunun için şu kodu yazarız;
		User user = service.findOne(id);
		
		if(user == null) {
			throw new UserNotFoundException("id:"+id);  //Kendi yazdığımız bu 'UserNotFoundException'ın "404 Not Found" ile sonuçlanması için oluşturduğumuz 'UserNotFoundException' class'ı içerisine gidip '@ResponseStatus(code = HttpStatus.NOT_FOUND)' annotation'ını eklememiz gerekir.
		}
		
		EntityModel<User> entityModel = EntityModel.of(user);  //'EntityModel' oluşturma işlemi bu şekilde yapılır. Şimdi bu oluşturduğumuz 'EntityModel'e bağlantı ekleyip döndürmek istiyoruz. Bu işlemi şu şekilde yaparız;
		
		WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).retrieveAllUsers());   //'WebMvcLinkBuilder' class'î içerisinde yer alan 'linkTo()' ve 'methodOn()' static yöntemlerini kullanabilmek için en tepeye 'import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;' ifadesini ekledik.
		//Sonuç olarak yukarıda yaptığımız şey 'WebMvcLinkBuilder' class'ı içerisinde yer alan 'linkTo()' yöntemini kullanarak controller methoduna işaret eden bir bağlantı(link) oluşturmaktır. İşaret ettiğimiz controller methodu ise bu class'taki 'retrieveAllUsers()' methodu'dur. Bu işlemi de 'methodOn()' yöntemi ile gerçekleştirdik.
		
		//Şimdi bağlantıya(linke) sahip olduğumuza göre onu 'EntityModel'e ekleyebiliriz;
		entityModel.add(link.withRel("all-users"));
		
		return entityModel;
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

