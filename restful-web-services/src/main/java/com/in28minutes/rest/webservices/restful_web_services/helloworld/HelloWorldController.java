package com.in28minutes.rest.webservices.restful_web_services.helloworld;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

//REST API
@RestController
public class HelloWorldController {
	
	//URL: /hello-world olacak ve "Hello World" yazısını ekrana yazdıracağız.
	//@RequestMapping(method = RequestMethod.GET, path = "/hello-world")
	@GetMapping(path = "/hello-world")
	public String helloWorld() {
		return "Hello World";
	}
	
	
	@GetMapping(path = "/hello-world-bean")
	public HelloWorldBean helloWorldBean() {
		return new HelloWorldBean("Hello World");
	}
	
	
	//Path Parameters  => URL'lerimizdeki değişken değerlere 'path parameters' denir.
	//URL: /users/{id}/todos/{id}  ==> /users/1/todos/101  (Yani bu URL, belirli bir kullanıcının(1) belirli bir todo'suna(101) erişmek için kullanılır.) 
	
	//Şimdi yapacağımız örnek => URL: /hello-world/path-variable/{name} olacaktır. Örneğin "/hello-world/path-variable/Ranga" şeklinde istek atacağız. 
	@GetMapping(path = "/hello-world/path-variable/{name}")
	public HelloWorldBean helloWorldPathVariable(@PathVariable String name) {
		return new HelloWorldBean(String.format("Hello World, %s", name));    //Burada yaptığımız şey; kullanıcı tarafından girilen String türündeki 'name'i almak ve bu method içerisinde yer alan '%s' ifadesini, girilen 'name' değeri ile değiştirmektir. Yani diyelim ki kullanıcı isteği "/hello-world/path-variable/Ranga" şeklinde yaptı.(Kullanıcı 'name' değerini path'ten 'Ranga' olarak göndermiş oldu.) Çıktı olarak kullanıcıya "messsage : Hello World, Ranga" ifadesini geri döndürürüz. 
	}
	
	
	
	
	
	
}

