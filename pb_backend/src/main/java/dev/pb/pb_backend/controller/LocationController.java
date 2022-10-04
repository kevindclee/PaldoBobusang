package dev.pb.pb_backend.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.pb.pb_backend.entity.Location;
import dev.pb.pb_backend.projection.LocationCountryCodeProjection;
import dev.pb.pb_backend.service.LocationService;

@RestController
@RequestMapping("locations")
@CrossOrigin(origins = "http://localhost:3000")
public class LocationController {
	
	@Autowired
	private LocationService locationService;
	
	// 'GET' http://localhost:8090/locations
	@GetMapping
	public List<Location.Response> findAllLocations() {
		System.out.println("GET: findAllLocations() of LocationController called");		
		List<Location> locationList = locationService.findAllLocations();
		return Location.Response.toResponseList(locationList);
	}
	
	// 'GET' http://localhost:8090/locations/:locationId
	@GetMapping("/{locationId}")
	public Location.Response findLocationByLocationId(@PathVariable int locationId) {
		System.out.println("GET: findLocationByLocationId() of LocationController called");		
		Location foundLocation = locationService.findLocationByLocationId(locationId);
		return Location.Response.toResponse(foundLocation);
	}
	
	// 'POST' http://localhost:8090/locations
	@PostMapping
	public ResponseEntity<Location.Response> createLocation(@RequestBody @Valid Location.Request request) {
		System.out.println("POST: createLocation() of LocationController called");		
		Location newLocation = Location.Request.toEntity(request);
		//Address address = request.getAddress();
		Location savedLocation = locationService.createLocation(newLocation);
		//address.setUser(savedUser);
		//addressRepository.save(address);
		Location.Response response = Location.Response.toResponse(savedLocation);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	// 'GET' http://localhost:8090/locations/localName/:localName
	@GetMapping("/localName/{localName}")
	public List<Location.ResponseCountryCode> findLocationsByLocalName(@PathVariable String localName) {
		System.out.println("GET: findLocationsByLocalName() of LocationController called");		
		List<LocationCountryCodeProjection> locationList = locationService.findByLocalName(localName);
		return Location.ResponseCountryCode.toResponseList(locationList);
	}
	
}
