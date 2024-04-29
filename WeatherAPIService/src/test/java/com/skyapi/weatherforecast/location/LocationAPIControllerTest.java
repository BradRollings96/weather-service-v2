package com.skyapi.weatherforecast.location;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.skyapi.weatherforecast.common.Location;

//WebMvcTest is used to load spring MVC related components for testing
@WebMvcTest(LocationAPIController.class)
public class LocationAPIControllerTest {
	
	private static final String ENDPOINT_PATH = "/v1/locations";
	
	//MockMvc is used to test your controller by calling HTTP requests to your API server, it can also perform assertions on the response
	@Autowired MockMvc mockMvc;
	
	//ObjectMapper is provided by Jackson dependency which enables you to serialise Java objects to JSON and vice versa
	@Autowired ObjectMapper mapper;
	
	//We need to use the @MockBean annotation provided by Mockito to mock the service component of the LocationAPIController, this is because the 
	//@WebMvcTest annotation only mocks the spring MVC components, if we look at our LocationAPIController, we have a dependency on the service layer. So MockBean can 
	//provide a fake service object for us
	@MockBean LocationService service;
	
	
	@Test
	public void testAddShouldReturn400BadRequestWhenRequestContainsInvalidData() throws Exception {
		Location location = new Location();
		
		String bodyContent = mapper.writeValueAsString(location);
		
		//400 stands for BAD_REQUUEST, meaning this is a client error and the client has created an invalid request
		mockMvc.perform(post(ENDPOINT_PATH).contentType("application/json").content(bodyContent))
			.andExpect(status().isBadRequest())
			.andDo(print());
	}
	
	@Test
	public void testAddShouldReturn201Created() throws Exception {
		Location location = new Location();
		location.setCode("NYC_USA");
		location.setCityName("New York City");
		location.setRegionName("New York");
		location.setCountryCode("US");
		location.setCountryName("United States of America");
		location.setEnabled(true);
		
		Mockito.when(service.add(location)).thenReturn(location);
		
		//We can see that because we used the @json_property annotation, when we look at the logs we can see the property names are returned in snake_case
		String bodyContent = mapper.writeValueAsString(location);
		
		//400 stands for BAD_REQUUEST, meaning this is a client error and the client has created an invalid request
		mockMvc.perform(post(ENDPOINT_PATH).contentType("application/json").content(bodyContent))
			.andExpect(status().isCreated())
			.andExpect(content().contentType("application/json"))
			.andExpect(jsonPath("$.code", is("NYC_USA1")))
			.andDo(print());
		
	}
	

}
