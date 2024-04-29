package com.skyapi.weatherforecast.location;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.skyapi.weatherforecast.common.Location;


//By Default, Spring Data JPA tests use H2 in-memory DB and they rollback data, in this project we'll run the tests against a
//real database (TODO: figure out if this is normal convention for unit testing the repository layer
//Because we are using Spring Data JPA, we need to use @DataJpaTest

@DataJpaTest
@AutoConfigureTestDatabase( replace = Replace.NONE)
@Rollback(false) //This ensures the data is kept in the database permanently
public class LocationRepositoryTest {
	
	@Autowired
	private LocationRepository repository;
	
	@Test
	public void testAddSuccess() {
		Location location = new Location();
		location.setCode("NYC_USA");
		location.setCityName("New York City");
		location.setRegionName("New York");
		location.setCountryCode("US");
		location.setCountryName("United States of America");
		location.setEnabled(true);
		
		Location savedLocation = repository.save(location);
		
		assertThat(savedLocation).isNotNull();
		assertThat(savedLocation.getCode()).isEqualTo("NYC_USA");
	}
	

}
