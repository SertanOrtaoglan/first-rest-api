package com.in28minutes.rest.webservices.restful_web_services.security;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SpringSecurityConfiguration {
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
		//1-> All requests should be authenticated
		http.authorizeHttpRequests(
				auth -> auth.anyRequest().authenticated()
				);
		
		//SecurityFilterChain'i override etmeden önce bir request gönderdiğimizde eğer o request 'not authenticated'sa default olarak bir 'login web page' gösteriliyordu. Şimdi bu REST API'miz içinde 'basic aunthentication'ı etkinleştirmek istiyoruz. Böylelikle bir request geldiğinde küçük bir 'login web page'(açılır pencere) gösterilecektir.
		//2-> If a request is not authenticated, a web page is shown
		http.httpBasic(withDefaults());   //Bir 'login web page'(açılır pencere) gösterebilmek için 'httpBasic()' methodu içerisinde static bir method olan 'withDefaults()'u kullanırız. Yani 'httpBasic'i default değerlerle ayarlamış oluruz. NOT-> 'withDefaults()' static methodunu 'Customizer(org.springframework.security.config)' interface'i içerisinden import ederiz. Bu işlemi en tepeye 'import static org.springframework.security.config.Customizer.withDefaults;' yazarak gerçekleştiririz. 
		
		//3-> CSRF disable for 'POST and PUT' 
		http.csrf(csrf -> csrf.disable());  //NOT-> Burada csrf'i "lamba expression" kullanarak devre dışı bıraktık. Bu işlemi 'method reference" ile "http.csrf(AbstractHttpConfigurer::disable)" şeklinde de yapabiliriz.
		
		
		return http.build();
	}
	

}
