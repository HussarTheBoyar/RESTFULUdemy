package com.microService.web.restfulwebService.Filtering;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.PropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FilteringController {
  @GetMapping("/filtering")
  public  MappingJacksonValue filtering(){
    SomeBean someBean = new SomeBean("value1", "value2", "value3");
    //Dynamic filtering
    MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(someBean);
    PropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("field1", "field3");
    FilterProvider filterProvider = new SimpleFilterProvider().addFilter("SomeBeanFilter", filter);
    mappingJacksonValue.setFilters(filterProvider);

    return mappingJacksonValue;
  }

  @GetMapping("/filteringList")
  public MappingJacksonValue filteringAsList(){
    List<SomeBean> list = Arrays.asList(new SomeBean("value1", "value2", "value3"),
        new SomeBean("value4", "value5", "value6"));

    //Dynamic filtering
    //We define just one filter name in model, and at each method, we define other list data we want to show
    MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(list);
    PropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("field2", "field3");
    FilterProvider filterProvider = new SimpleFilterProvider().addFilter("SomeBeanFilter", filter);
    mappingJacksonValue.setFilters(filterProvider);

    return mappingJacksonValue;

  }

}
