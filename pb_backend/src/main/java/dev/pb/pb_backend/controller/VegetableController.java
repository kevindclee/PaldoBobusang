package dev.pb.pb_backend.controller;

import java.util.Date;
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

import dev.pb.pb_backend.entity.Vegetable;
import dev.pb.pb_backend.service.VegetableService;

@RestController
@RequestMapping("vegetables")
@CrossOrigin(origins = "http://localhost:3000")
public class VegetableController {
	
	@Autowired
	private VegetableService vegetableService;
	
	// 'GET' http://localhost:8090/vegetables
	@GetMapping
	public List<Vegetable.Response> findAllVegetables() {
		System.out.println("GET: findAllVegetables() of VegetableController called");		
		List<Vegetable> vegetableList = vegetableService.findAllVegetables();
		return Vegetable.Response.toResponseList(vegetableList);
	}
	
	// 'GET' http://localhost:8090/vegetables/:code
	@GetMapping("/{code}")
	public Vegetable.Response findVegetableByCode(@PathVariable int code) {
		System.out.println("GET: findVegetableByCode() of VegetableController called");		
		Vegetable foundVegetable = vegetableService.findVegetableByCode(code);
		return Vegetable.Response.toResponse(foundVegetable);
	}
	
	// 'POST' http://localhost:8090/vegetables
	@PostMapping
	public ResponseEntity<Vegetable.Response> createVegetable(@RequestBody @Valid Vegetable.Request request) {
		System.out.println("POST: createVegetable() of VegetableController called");		
		Vegetable newVegetable = Vegetable.Request.toEntity(request);
		//Address address = request.getAddress();
		Vegetable savedVegetable = vegetableService.createVegetable(newVegetable);
		//address.setUser(savedUser);
		//addressRepository.save(address);
		Vegetable.Response response = Vegetable.Response.toResponse(savedVegetable);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	// 'GET' http://localhost:8090/vegetables/location/:locationId
	@GetMapping("/location/{locationId}")
	public List<Vegetable.Response> findVegetablesByLocationId(@PathVariable int locationId) {
		System.out.println("GET: findVegetablesByLocationId() of VegetableController called");		
		List<Vegetable> vegetableList = vegetableService.findVegetablesByLocationId(locationId);
		return Vegetable.Response.toResponseList(vegetableList);
	}
	
	// 'GET' http://localhost:8090/vegetables/harvest
	@GetMapping("/harvest")
	public List<Vegetable.Response> findVegetablesByHarvest() {
		System.out.println("GET: findVegetablesByHarvest() of VegetableController called");
		Date curDate = new Date();
		List<Vegetable> vegetableList = vegetableService.findVegetablesByHarvest(curDate);
		return Vegetable.Response.toResponseList(vegetableList);
	}
	
}
