package dev.pb.pb_backend.domain.common.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.pb.pb_backend.domain.common.entity.Fruit;
import dev.pb.pb_backend.domain.common.entity.Location;
import dev.pb.pb_backend.domain.common.entity.Price;
import dev.pb.pb_backend.domain.common.entity.Vegetable;
import dev.pb.pb_backend.domain.common.repository.FruitRepository;
import dev.pb.pb_backend.domain.common.repository.LocationRepository;
import dev.pb.pb_backend.domain.common.repository.PriceRepository;
import dev.pb.pb_backend.domain.common.repository.VegetableRepository;

@Service
public class LocationServiceImpl implements LocationService {
	
	@Autowired
	private LocationRepository locationRepository;

	@Autowired
	private FruitRepository fruitRepository;
	
	@Autowired
	private VegetableRepository vegetableRepository;
	
	@Autowired
	private PriceRepository priceRepository;

	@Override
	public List<Location.Response> findAllLocations() {
		List<Location> locations = locationRepository.findAll();
		
		return findByLocations(locations);
	}

	@Override
	public Location.Response findLocationByLocationId(int locationId) {
		Location location = locationRepository.findById(locationId).get();
		List<Fruit> fruits = fruitRepository.findByLocationsLocationId(locationId);
		List<Vegetable> vegetables = vegetableRepository.findByLocationsLocationId(locationId);
		Map<String, List<Price>> prices = new HashMap<String, List<Price>>();
		
		for (Fruit fruit : fruits) {
			prices.put(fruit.getItemName(), priceRepository.findByFruitItemCodeAndLocationLocationId(fruit.getItemCode(), locationId));
		}
		for (Vegetable vegetable: vegetables) {
			prices.put(vegetable.getItemName(), priceRepository.findByVegetableItemCodeAndLocationLocationId(vegetable.getItemCode(), locationId));
		}
		
		return Location.Response.toResponse(location, fruits, vegetables, prices); 
	}

	@Override
	public Location.OnlyLocation createLocation(Location.Request request) {
		locationRepository.save(Location.Request.toEntity(request));
		Location saved = locationRepository.findById(request.getLocationId()).get();
		
		return Location.OnlyLocation.toOnlyLocation(saved);
	}

	@Override
	public List<Location.Response> findByLocalName(String localName) {
		List<Location> locations = locationRepository.findByLocalName(localName);
		
		return findByLocations(locations);
	}

	@Override
	public List<Location.Response> findByLocalEngName(String localEngName) {
		List<Location> locations = locationRepository.findByLocalEngName(localEngName);
		
		return findByLocations(locations);
	}

	@Override
	public List<Location.Response> findByCityEngName(String cityEngName) {
		List<Location> locations = locationRepository.findByCityEngName(cityEngName);
		
		return findByLocations(locations); 
	}
	
	private List<Location.Response> findByLocations(List<Location> locations) {
		Map<Integer, List<Fruit>> fruits = new HashMap<Integer, List<Fruit>>();
		Map<Integer, List<Vegetable>> vegetables = new HashMap<Integer, List<Vegetable>>();
		Map<Integer, Map<String, List<Price>>> prices = new HashMap<Integer, Map<String,List<Price>>>();
		Map<String, List<Price>> pricesOfItem = new HashMap<String, List<Price>>();
		
		for (Location location : locations) {
			List<Fruit> fruitList = fruitRepository.findByLocationsLocationId(location.getLocationId());
			fruits.put(location.getLocationId(), fruitList);
			List<Vegetable> vegetableList = vegetableRepository.findByLocationsLocationId(location.getLocationId());
			vegetables.put(location.getLocationId(), vegetableList);
			
			for (Fruit fruit : fruitList) {
				prices.put(location.getLocationId(), (Map<String, List<Price>>) pricesOfItem.put(fruit.getItemName(), priceRepository.findByFruitItemCodeAndLocationLocationId(fruit.getItemCode(), location.getLocationId())));
			}
			for (Vegetable vegetable : vegetableList) {
				prices.put(location.getLocationId(), (Map<String, List<Price>>) pricesOfItem.put(vegetable.getItemName(), priceRepository.findByVegetableItemCodeAndLocationLocationId(vegetable.getItemCode(), location.getLocationId())));
			}
		}
		
		return Location.Response.toResponseList(locations, fruits, vegetables, prices); 
	}

}









