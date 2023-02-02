package dev.pb.pb_backend.domain.common.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.pb.pb_backend.domain.common.entity.Location;
import dev.pb.pb_backend.domain.common.service.LocationService;

@RestController
@RequestMapping("locations")

public class LocationController {

	@Autowired
	private LocationService locationService;
	
	// 'GET' http://localhost:8090/locations
	@GetMapping
	public List<Location.Response> findAllLocations() {
		System.out.println("\nGET: findAllLocations() of LocationController called\n");		
		
		return locationService.findAllLocations();
	}
	
	// 'GET' http://localhost:8090/locations/:locationId
	@GetMapping("/{locationId}")
	public Location.Response findLocationByLocationId(@PathVariable int locationId) {
		System.out.println("\nGET: findLocationByLocationId() of LocationController called\n");		
		
		return locationService.findLocationByLocationId(locationId);
	}
	// 'POST' http://localhost:8090/locations
	@PostMapping
	public Location.OnlyLocation createLocation(@RequestBody @Valid Location.Request request) {
		System.out.println("\nPOST: createLocation() of LocationController called\n");		

		return locationService.createLocation(request);
	}

	// 'GET' http://localhost:8090/locations/localName/:localName
	@GetMapping("/localName/{localName}")
	public List<Location.Response> findLocationsByLocalName(@PathVariable String localName) {
		System.out.println(String.format("\nGET: findLocationsByLocalName() of LocationController called %s\n", localName));		

		return locationService.findByLocalName(localName);
	}
	
	// 'GET' http://localhost:8090/locations/localEngName/:localEngName
	@GetMapping("/localEngName/{localEngName}")
	public List<Location.Response> findByLocalEngName(@PathVariable String localEngName) {
		System.out.println("\nGET: findByLocalEngName() of LocationController called\n");		

		return locationService.findByLocalEngName(localEngName);
	}
	
	// 'GET' http://localhost:8090/locations/cityEngName/:cityEngName
	@GetMapping("/cityEngName/{cityEngName}")
	public List<Location.Response> findByCityEngName(@PathVariable String cityEngName) {
		System.out.println("GET: findByCityEngName() of LocationController called");		

		return locationService.findByCityEngName(cityEngName);
	}
	
}
