package com.in28minutes.rest.webservices.restful_web_services.user;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Size;


@Entity
public class Post {
	
	@Id
	@GeneratedValue
	private Integer id;
	
	@Size(min = 10)
	private String description;
	
	@ManyToOne(fetch = FetchType.LAZY)  //'Post'u getirdiğimizde, 'Post' ile ilişkili 'User' detaylarını getirmek istemiyoruz. Dolayısıyla parantez içerisine 'fetch = FetchType.LAZY' yazdık.   NOT-> '@ManyToOne' annotation'ı default olarak "EAGER"dır.['fetch = FetchType.EAGER'dır.] Yani aynı sorguda(query) 'Post' ve 'User' detaylarını birlikte alırız. Sonuç olarak "EAGER" kullanıyorsak, 'Post' detaylarının yanı sıra 'User' detayları da getirilecektir. Fakat "LAZY" kullanıyorsak, 'Post' detaylarını getirdiğimizde 'Post' ile ilişkili 'User' detayları getirilmeyecektir! 
	@JsonIgnore             //User'ın, 'Post' bean'i için JSON response'larının bir parçası olmasını olmasını istemiyoruz. Bu yüzden buraya '@JsonIgnore'u ekleriz.
	private User user;
	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "Post [id=" + id + ", description=" + description + "]";
	}

	
	//Belirli bir User için yeni bir 'Post' oluşturmak istiyoruz dolayısıyla yukarıda yeni oluşturduğumuz field'ın(private User user) getter ve setter'larını oluşturmamız gerekir.
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	
	
	

}
