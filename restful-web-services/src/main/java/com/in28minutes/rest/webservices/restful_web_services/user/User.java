package com.in28minutes.rest.webservices.restful_web_services.user;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

//import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;


@Entity(name = "user_details")
public class User {
	
	protected User() {
		
	}
	
	@Id
	@GeneratedValue
	private Integer id;
	
	@Size(min = 2, message = "Name should have atleast 2 characters")
	//@JsonProperty("user_name")  //Bu annotation sayesinde bize gelen JSON yanıtındaki "name" isimli field'ın değerini "user_name" olarak değiştirmiş olduk. 
	private String name;
	
	@Past(message = "Birth Date should be in the past")
	//@JsonProperty("birth_date")
	private LocalDate birthDate;
	
	@OneToMany(mappedBy = "user")  //Burada parantez içine yazılan "user" ifadesi 'Post' class'î içerisine yazdığımız "private User user;"dan gelir. Yani 'Post' class'ındaki "user" field'ı ile map ediyoruz.
	@JsonIgnore                    //Post'un, 'User' bean'i için JSON response'larının bir parçası olmasını olmasını istemiyoruz. Bu yüzden buraya '@JsonIgnore'u ekleriz.
	private List<Post> posts;
	
	public User(Integer id, String name, LocalDate birthDate) {
		super();
		this.id = id;
		this.name = name;
		this.birthDate = birthDate;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDate getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", birthDate=" + birthDate + "]";
	}

	//Belirli bir 'User'ın tüm 'Post'larını almak istiyorsak yukarıda yeni oluşturduğumuz field'ın(private List<Post> posts) getter ve setter'larını oluşturmamız gerekir.
	public List<Post> getPosts() {
		return posts;
	}

	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}
	
	
	
	
	
	

}

