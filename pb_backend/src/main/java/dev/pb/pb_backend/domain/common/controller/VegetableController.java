package dev.pb.pb_backend.domain.common.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.pb.pb_backend.domain.common.entity.Vegetable;
import dev.pb.pb_backend.domain.common.service.VegetableService;

@RestController
@RequestMapping("vegetables")

public class VegetableController {
	
	@Autowired
	private VegetableService vegetableService;
	
	// 'GET' http://localhost:8090/vegetables
	@GetMapping
	public List<Vegetable.Response> findAllVegetables() {
		System.out.println("\nGET: findAllVegetables() of VegetableController called\n");		

		return vegetableService.findAllVegetables();
	}
	
	// 'GET' http://localhost:8090/vegetables/:vegetableCode
	@GetMapping("/{vegetableCode}")
	public Vegetable.Response findVegetableByCode(@PathVariable int vegetableCode) {
		System.out.println("\nGET: findVegetableByCode() of VegetableController called\n");		

		return vegetableService.findVegetableByCode(vegetableCode);
	}
	
	// 'POST' http://localhost:8090/vegetables
	@PostMapping
	public Vegetable.Response createVegetable(@RequestBody @Valid Vegetable.Request request) {
		System.out.println("POST: createVegetable() of VegetableController called");		

		return vegetableService.createVegetable(request);
	}

	// 'GET' http://localhost:8090/vegetables/location/:locationId
	@GetMapping("/location/{locationId}")
	public List<Vegetable.Response> findVegetablesByLocationId(@PathVariable int locationId) {
		System.out.println("\nGET: findVegetablesByLocationId() of VegetableController called\n");		

		return vegetableService.findVegetablesByLocationId(locationId);
	}
	
	// 'GET' http://localhost:8090/vegetables/list/harvest
	@GetMapping("/list/harvest")
	public List<Vegetable.Response> findVegetablesByHarvest() {
		System.out.println("\nGET: findVegetablesByHarvest() of VegetableController called\n");

		return vegetableService.findVegetablesByHarvest();
	}

	// 'PUT' http://localhost:8090/vegetables
	@PutMapping
	public Vegetable.Response updateVegetable(@RequestBody Vegetable.Request request) {

		return vegetableService.updateVegetable(request);
	}
	
	// 'GET' http://localhost:8090/vegetables/localEngName/:localEngName
	@GetMapping("/localEngName/{localEngName}")
	public List<Vegetable.Response> findVegetablesByLocalEngName(@PathVariable String localEngName) {
		System.out.println(String.format("\nGET: findVegetablesByLocalEngName() of VegetableController called: %s\n", localEngName));		
		
		return vegetableService.findVegetablesByLocalEngName(localEngName);
	}	

	// 'GET' http://localhost:8090/vegetables/itemNameAndLocalEngName/:itemName/:localEngName
	@GetMapping("/itemNameAndLocalEngName/{itemName}/{localEngName}")
	public Vegetable.Response findVegetablesByItemNameLocalEngName(@PathVariable String itemName, @PathVariable String localEngName) {
		System.out.println("\nGET: findVegetablesByItemNameLocalEngName() of VegetableController called\n");		

		return vegetableService.findVegetablesByItemNameLocalEngName(itemName, localEngName);
	}	
	
}
