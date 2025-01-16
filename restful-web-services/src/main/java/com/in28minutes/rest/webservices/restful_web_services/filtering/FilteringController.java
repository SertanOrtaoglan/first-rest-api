package com.in28minutes.rest.webservices.restful_web_services.filtering;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

@RestController
public class FilteringController {
	/*(STATIC FILTERING)
	//İki farklı REST API(filtering/filterin-list)'de, bir bean için(SomeBean) aynı filtreleme yapacağız. Buna 'Static Filtering'de denir.
	//Yani 'SomeBean'de, '@JsonIgnore' kullanarak 'field2'yi filtereleriz ve böylelikle hem '/filtering'de hem de '/filtering-list'de 'field2'nin değerleri('value2' ve 'value5') ekranda gözükmez! Bu işleme 'statik filtreleme' denir.
	@GetMapping("/filtering")
	public SomeBean filtering() {
		return new SomeBean("value1", "value2", "value3");
	}
	
	@GetMapping("/filtering-list")
	public List<SomeBean> filteringList() {
		return Arrays.asList(new SomeBean("value1", "value2", "value3"), new SomeBean("value4", "value5", "value6"));  //Bu şekilde liste dahi döndürsek 'field2'ler yine gelmez. Yani çıktı olarak birinci 'SomeBean'den "field1:value1", "field3:value3" gelir. İkinci 'SomeBean'den ise "field1:value4", "field3:value6" gelir. Sonuç olarak 'field2'ler(değerleri, birinci listede 'value2' ve ikinci listede 'value5'tir) çıktı olarak verilmez!
	}
	*/
	
	
	//DYNAMIC FILTERING
	//Farklı REST API'lerinde aynı bean için farklı attribute'lar döndürmek isteyebileceğimiz durumlar olabilir. Böyle bir durumda bu işlemi gerçekleştirmek için 'dinamik filtreleme'den yararlanırız. 
	//Yani 'dinamik filtreleme'de, belirli bir REST API için bir bean'in filtrelemesini özelleştirmek istiyoruz. Örneğin iki REST API'miz("filtering" ve "filtering-list") olduğunu düşünelim. "filtering"de 'field2'yi filtreleyip göstermek istemiyoruz('field1' ve 'field3' gözükecek). "filtering-list"de ise 'field1'i filtreleyip göstermek istemiyoruz('field2' ve 'field3' gözükecek). Dolayısıyla, aynı bean için(SomeBean) farklı REST API'lerinde('filtering' ve 'filtering-list') farklı filtreleme mantığına sahip olacağız. Bu işlemi 'dinamik filtreleme' ile halledeceğiz. Nasıl yapacağımızı öğrenelim.
	
	/*
	@GetMapping("/filtering")  //"field2" gözükmeyecek.
	public MappingJacksonValue filtering() {
		SomeBean someBean = new SomeBean("value1", "value2", "value3");
		
		MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(someBean);   //'MappingJacksonValue' class'ı filtreleri ayarlamamıza olanak tanır.
		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("field1", "field3");
		FilterProvider filters = new SimpleFilterProvider().addFilter("SomeBeanFilter", filter);   //Buradaki 'SomeBeanFilter' ifadesi, bean'imiz(SomeBean) üzerine eklediğimiz '@JsonFilter("SomeBeanFilter")' ile eşleşmelidir. Yani annotation'da parantez içerisine yazılan isimle buradaki isim aynı olmalıdır. 
		mappingJacksonValue.setFilters(filters);
		
		return mappingJacksonValue;
	}
	
	@GetMapping("/filtering-list")  //"field1" gözükmeyecek.
	public MappingJacksonValue filteringList() {
		List<SomeBean> list = Arrays.asList(new SomeBean("value1", "value2", "value3"), new SomeBean("value4", "value5", "value6"));
		
		MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(list);
		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("field2", "field3");
		FilterProvider filters = new SimpleFilterProvider().addFilter("SomeBeanFilter", filter);
		mappingJacksonValue.setFilters(filters);
		
		return mappingJacksonValue;
		
	}
	*/
	
	
	//Yukarıda yazdığımız kodlara bakarsak tekrar eden birçok kodumuz var bu yüzden refactor yapıp kodu düzenlersek;
	
	private MappingJacksonValue getFilteredResponse(Object data, String... fields) {
        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(data);

        SimpleBeanPropertyFilter filter = 
                SimpleBeanPropertyFilter.filterOutAllExcept(fields);

        FilterProvider filters = 
                new SimpleFilterProvider().addFilter("SomeBeanFilter", filter);

        mappingJacksonValue.setFilters(filters);

        return mappingJacksonValue;
    }

    @GetMapping("/filtering")
    public MappingJacksonValue filtering() {
        SomeBean someBean = new SomeBean("value1", "value2", "value3");
        return getFilteredResponse(someBean, "field1", "field3");
    }

    @GetMapping("/filtering-list")
    public MappingJacksonValue filteringList() {
        List<SomeBean> list = Arrays.asList(
                new SomeBean("value1", "value2", "value3"),
                new SomeBean("value4", "value5", "value6")
        );
        return getFilteredResponse(list, "field2", "field3");
    }
	
	
	

}
