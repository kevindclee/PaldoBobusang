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

import dev.pb.pb_backend.domain.common.entity.Fruit;
import dev.pb.pb_backend.domain.common.service.FruitService;

@RestController
@RequestMapping("fruits")

public class FruitController {
	
	@Autowired
	private FruitService fruitService;
	
	// 'GET' http://localhost:8090/fruits
	@GetMapping
	public List<Fruit.Response> findAllFruits() {
		System.out.println("GET: findAllFruits() of FruitController called");		

		return fruitService.findAllFruits();
	}
	
	// 'GET' http://localhost:8090/fruits/:fruitCode
	@GetMapping("/{fruitCode}")
	public Fruit.Response findFruitByCode(@PathVariable int fruitCode) {
		System.out.println("\nGET: findFruitByCode() of FruitController called\n");		

		return fruitService.findFruitByCode(fruitCode);
	}
	
	// 'POST' http://localhost:8090/fruits
	@PostMapping
	public Fruit.Response createFruit(@RequestBody @Valid Fruit.Request request) {
		System.out.println("POST: createFruit() of FruitController called");
		
		return fruitService.createFruit(request);
	}

	// 'GET' http://localhost:8090/fruits/location/:locationId
	@GetMapping("/location/{locationId}")
	public List<Fruit.Response> findFruitsByLocationId(@PathVariable int locationId) {
		System.out.println("GET: findFruitsByLocationId() of FruitController called");		

		return fruitService.findFruitsByLocationId(locationId);
	}
	
	// 'GET' http://localhost:8090/fruits/list/harvest
	@GetMapping("/list/harvest")
	public List<Fruit.Response> findFruitsByHarvest() {
		System.out.println("\nGET: findFruitsByHarvest() of FruitController called\n");
		
		return fruitService.findFruitsByHarvest();
	}
	
	// 'PUT' http://localhost:8090/fruits
	@PutMapping
	public Fruit.Response updateFruit(@RequestBody Fruit.Request request) {
		System.out.println("\nPUT: updateFruit() of FruitController called\n");		

		return fruitService.updateFruit(request);
	}
	
	// 'GET' http://localhost:8090/fruits/localEngName/:localEngName
	@GetMapping("/localEngName/{localEngName}")
	public List<Fruit.Response> findFruitsByLocalEngName(@PathVariable String localEngName) {
		System.out.println("\nGET: findFruitsByLocalEngName() of FruitController called\n");		

		return fruitService.findFruitsByLocalEngName(localEngName);
	}	

	// 'GET' http://localhost:8090/fruits/itemNameAndLocalEngName/:locationId
	@GetMapping("/itemNameAndLocalEngName/{itemName}/{localEngName}")
	public Fruit.Response findFruitsByItemNameLocalEngName(@PathVariable String itemName, @PathVariable String localEngName) {
		System.out.println("\nGET: findFruitsByItemNameLocalEngName() of FruitController called\n");		
		
		return fruitService.findFruitsByItemNameLocalEngName(itemName, localEngName);
	}	
	
}
