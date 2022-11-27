package dev.pb.pb_backend.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.pb.pb_backend.entity.Fruit;
import dev.pb.pb_backend.repository.LocationRepository;
import dev.pb.pb_backend.service.FruitService;
import dev.pb.pb_backend.service.VegetableService;

@RestController
@RequestMapping("/AgriMap")
public class AgriMap<E> {
	
	@Autowired
	FruitService fruitService;
	
	@Autowired
	VegetableService vegetableService;
	
	@GetMapping("/{localEngName}/{category}")
	public List<Object> findItemsByCategory(@PathVariable String localEngName, @PathVariable String category) {
		List<Object> resList = new ArrayList<Object>();
		
		if (category.equals("fruit")) resList = fruitService.findFruitsByLocalEngName(localEngName);
		if (category.equals("vegetable")) resList = vegetableService.findVegetablesByLocalEngName(localEngName);
		
		return resList;
	}
	
}
