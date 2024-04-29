package com.skyapi.weatherforecast.location;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skyapi.weatherforecast.common.Location;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/v1/locations")
public class LocationAPIController {
	
	private LocationService service;
	
	@Autowired
	public LocationAPIController(LocationService service) {
		this.service = service;
	}
	
	//The @Valid annotation comes from 'spring-boot-starter-validaion' and it asserts against the annotations we put against the location object
	//such as @NotBlank and @NotNull, this test fails because we create a location object with null properties
	@PostMapping
	public ResponseEntity<Location> addLocation(@Valid @RequestBody Location location) {
		Location addedLocation = service.add(location);
		URI uri = URI.create("/v1/locations" + addedLocation.getCode());
		
		return ResponseEntity.created(uri).body(addedLocation);
	}
	

}
