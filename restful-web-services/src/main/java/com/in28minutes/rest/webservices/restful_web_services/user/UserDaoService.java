package com.in28minutes.rest.webservices.restful_web_services.user;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import org.springframework.stereotype.Component;

@Component
public class UserDaoService {
	
	//Now: UserDaoService gets users from Static List 
	//Then: JPA/Hibernate > Database
	
	private static List<User> users = new ArrayList<>();
	private static int usersCount = 0;
	
	static {
		users.add(new User(++usersCount, "Adam", LocalDate.now().minusYears(30)));
		users.add(new User(++usersCount, "Eve", LocalDate.now().minusYears(25)));
		users.add(new User(++usersCount, "Jim", LocalDate.now().minusYears(20)));
	}
	
	
	public List<User> findAll() {
		return users;
	}
	
	
	public User findOne(int id) {
		Predicate<? super User> predicate = user -> user.getId().equals(id);
		//return users.stream().filter(predicate).findFirst().get();         //'findFirst()'den sonra 'get()'i ekledik. Bu kullanım pek uygun değildir çünkü 'get()' methodu 'NoSuchElementException' hatası fırlatabilir. Yani var olmayan bir id değeri ile istek atarsak(örneğin '/users/101' gibi) ekrana 'NoSuchElementException' hatasının fırlatıldığını görürüz.
		return users.stream().filter(predicate).findFirst().orElse(null);    //Yukarıdaki durumu önlemek için 'findFirst()'den sonra 'get()' değil 'orElse(null)' yazarız. 'orElse()' methodu bir değer mevcutsa değeri döndürür, aksi takdirde tanımladığımız 'other(diğer)' değeri döndürür. Mesela değer mevcut değilse 'null' döndürebiliriz. Bunun için 'orElse(null)' ifadesini yazarız.
	}
	
	
	public User save(User user) {
		user.setId(++usersCount);
		users.add(user);
		return user;
	}
	
	
	public void deleteById(int id) {
		Predicate<? super User> predicate = user -> user.getId().equals(id);
		users.removeIf(predicate);
	}
	
	
	

}

