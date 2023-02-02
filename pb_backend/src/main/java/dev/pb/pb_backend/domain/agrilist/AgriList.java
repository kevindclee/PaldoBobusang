package dev.pb.pb_backend.domain.agrilist;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.pb.pb_backend.domain.common.entity.Fruit;
import dev.pb.pb_backend.domain.common.entity.Vegetable;
import dev.pb.pb_backend.domain.common.service.FruitService;
import dev.pb.pb_backend.domain.common.service.VegetableService;

@RestController
@RequestMapping(path="/AgriList")
public class AgriList {

	@Autowired
	private FruitService fruitService;
	
	@Autowired
	private VegetableService vegetableService;
	
	@GetMapping
	public List<Object> findItems() {
		List<Object> resList = new ArrayList<Object>();
		List<Fruit.Response> fruits = fruitService.findFruitsByHarvest();
		List<Vegetable.Response> vegetables = vegetableService.findVegetablesByHarvest();
		fruits.stream().forEach(fruit -> resList.add(fruit));
		vegetables.stream().forEach(vegetable -> resList.add(vegetable));
		
		return resList;
	}
	
}
