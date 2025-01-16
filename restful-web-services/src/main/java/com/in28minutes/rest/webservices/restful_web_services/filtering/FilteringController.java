package com.in28minutes.rest.webservices.restful_web_services.filtering;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FilteringController {
	
	@GetMapping("/filtering")
	public SomeBean filtering() {
		return new SomeBean("value1", "value2", "value3");
	}
	
	@GetMapping("/filtering-list")
	public List<SomeBean> filteringList() {
		return Arrays.asList(new SomeBean("value1", "value2", "value3"), new SomeBean("value4", "value5", "value6"));  //Bu şekilde liste dahi döndürsek 'field2'ler yine gelmez. Yani çıktı olarak birinci 'SomeBean'den "field1:value1", "field3:value3" gelir. İkinci 'SomeBean'den ise "field1:value4", "field3:value6" gelir. Sonuç olarak 'field2'ler(değerleri, birinci listede 'value2' ve ikinci listede 'value5'tir) çıktı olarak verilmez!
	}
	

}
