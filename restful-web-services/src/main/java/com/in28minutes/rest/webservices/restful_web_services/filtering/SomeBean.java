package com.in28minutes.rest.webservices.restful_web_services.filtering;

import com.fasterxml.jackson.annotation.JsonIgnore;
//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

//@JsonIgnoreProperties({"field1","field2"})    //Bu annotation bir class üzerinde tanımlanabilir. Ve parantez içerisine ignore edilmesini istediğimiz field'ların adlarını yazarız.(örrneğin 'field1') Böylelikle aşağıda ignore edilmesini istediğimiz her field'ın üzerine tek tek '@JsonIgnore' yazmak yerine direkt olarak class üzerinde '@JsonIgnoreProperties("field1")' yazarak ve parantez içerisine ignore edilmesini istediğimiz tüm field'ların adını belirterek işi kısa yoldan halledebiliriz. Not: Tek bir field'ın ignore edilmesini istiyorsak '@JsonIgnoreProperties("field1")' şeklinde yazarız eğer birden fazla field'ın ignore edilmesini istiyorsak o zaman '@JsonIgnoreProperties({"field1","field2"})' şeklinde yazmamız gerekir.  
public class SomeBean {
	private String field1;
	
	@JsonIgnore    //Bu annotation sayesinde "static filtreleme" yapmış oluruz. Bu field(field2) artık JSON response'da gözükmez!
	private String field2;
	
	//@JsonIgnore
	private String field3;
	
	public SomeBean(String field1, String field2, String field3) {
		super();
		this.field1 = field1;
		this.field2 = field2;
		this.field3 = field3;
	}

	public String getField1() {
		return field1;
	}

	public String getField2() {
		return field2;
	}

	public String getField3() {
		return field3;
	}

	@Override
	public String toString() {
		return "SomeBean [field1=" + field1 + ", field2=" + field2 + ", field3=" + field3 + "]";
	}
	
	

}
