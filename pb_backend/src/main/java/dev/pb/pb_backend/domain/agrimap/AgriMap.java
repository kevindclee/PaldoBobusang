package dev.pb.pb_backend.domain.agrimap;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.pb.pb_backend.domain.common.entity.Fruit;
import dev.pb.pb_backend.domain.common.entity.Vegetable;
import dev.pb.pb_backend.domain.common.service.FruitService;
import dev.pb.pb_backend.domain.common.service.VegetableService;

@RestController
@RequestMapping("/AgriMap")
public class AgriMap {
	
	@Autowired
	FruitService fruitService;
	
	@Autowired
	VegetableService vegetableService;
	
	@GetMapping("/{localEngName}/fruit")
	public List<Fruit.Response> findItemsByFruit(@PathVariable String localEngName) {
		
		return fruitService.findFruitsByLocalEngName(localEngName);
	}
	
	@GetMapping("/{localEngName}/vegetable")
	public List<Vegetable.Response> findItemsByVegetable(@PathVariable String localEngName) {
		
		return vegetableService.findVegetablesByLocalEngName(localEngName);
	}
	
}
