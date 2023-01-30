package dev.pb.pb_backend.domain.common.service;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.pb.pb_backend.domain.common.entity.Fruit;
import dev.pb.pb_backend.domain.common.entity.Location;
import dev.pb.pb_backend.domain.common.entity.Price;
import dev.pb.pb_backend.domain.common.entity.Fruit.Request;
import dev.pb.pb_backend.domain.common.entity.Fruit.Response;
import dev.pb.pb_backend.domain.common.repository.FruitRepository;
import dev.pb.pb_backend.domain.common.repository.LocationRepository;
import dev.pb.pb_backend.domain.common.repository.PriceRepository;

@Service
public class FruitServiceImpl implements FruitService {
	

	@Autowired
	private FruitRepository fruitRepository;
	
	@Autowired
	private LocationRepository locationRepository;
	
	@Autowired
	private PriceRepository priceRepository;
	
	@Override
	public List<Fruit.Response> findAllFruits() {
		List<Fruit> fruits = fruitRepository.findAll();
		
		return findByFruits(fruits);
	}

	@Override
	public Fruit.Response findFruitByCode(int code) {
		Fruit fruit = fruitRepository.findById(code).get();
		List<Location> locations = locationRepository.findByFruitsItemName(fruit.getItemName());
		List<Price> prices = priceRepository.findByFruitItemName(fruit.getItemName());
		
		return Fruit.Response.toResponse(fruit, locations, prices); 
	}


	@Override
	public Response createFruit(Request request) {
		List<Location> locations = new ArrayList<Location>();
		request.getLocationIds().stream().forEach(locationId -> locations.add(locationRepository.findById(locationId).get()));
		Fruit savedFruit = fruitRepository.save(Fruit.Request.toEntity(request, locations));
		savedFruit.setLocations(locations);
		locations.stream().forEach(location -> locationRepository.save(location));

		return Fruit.Response.toResponse(savedFruit, locations, null);
	}

	@Override
	public List<Fruit.Response> findFruitsByLocationId(int locationId) {
		List<Fruit> fruits = fruitRepository.findByLocationsLocationId(locationId);
		
		return findByFruits(fruits);
	}

	@Override
	public List<Fruit.Response> findFruitsByHarvest() {
		ZonedDateTime curDate = ZonedDateTime.now();
		LocalDate plusOneDay = LocalDate.from(curDate.plusDays(1));
		LocalDate minusOneDay = LocalDate.from(curDate.minusDays(1));
		List<Fruit> fruits = fruitRepository.findByHarvestStartBeforeAndHarvestEndAfter(plusOneDay, minusOneDay);
		
		return findByFruits(fruits); 
	}
	
//	@Override
//	public List<FruitItemImageProjection> findFruitsByHarvestInList(Date curDate) {
//		Long curMilliSeconds = curDate.getTime();
//		Date plusOneDay = new Date();
//		plusOneDay.setTime(curMilliSeconds + MILLI_SECONDS_IN_DAY);
//		Date minusOneDay = new Date();
//		minusOneDay.setTime(curMilliSeconds - MILLI_SECONDS_IN_DAY);
//		return fruitRepository.findDistinctByHarvestStartBeforeAndHarvestEndAfter(plusOneDay, minusOneDay);
//	}

//	@Override
//	public Fruit updateFruitLocations(Request request) {
//		final Fruit foundFruit = findFruitByCode(request.getFruitCode());
//		foundFruit.setLocations(request.getLocations());
//		Fruit savedFruit = fruitRepository.save(foundFruit);
//		return savedFruit;
//	}
	
	@Override
	public Fruit.Response updateFruit(Fruit.Request request) {
		Fruit foundFruit = fruitRepository.findById(request.getItemCode()).get();
		Fruit savedFruit = fruitRepository.save(Fruit.Request.updateFruit(foundFruit, request));
		
		return Fruit.Response.toResponse(savedFruit, savedFruit.getLocations(), savedFruit.getPrices());
	}

	@Override
	public List<Fruit.Response> findFruitsByLocalEngName(String localEngName) {
		List<Location> locations = locationRepository.findByLocalEngName(localEngName);
		Map<String, List<Location>> locationsForFruit = new HashMap<String, List<Location>>();
		Map<String, List<Price>> pricesForFruit = new HashMap<String, List<Price>>();
		Set<Fruit> fruits = new HashSet<Fruit>();
		
		for (Location location : locations) {
			List<Fruit> fruitList = fruitRepository.findByLocationsCityName(location.getCityName());
			for (Fruit fruit : fruitList) {
				String fruitName = fruit.getItemName();
				if (locationsForFruit.containsKey(fruitName)) locationsForFruit.get(fruitName).add(location);
				else {
					locationsForFruit.put(fruitName, new ArrayList<Location>());
					locationsForFruit.get(fruitName).add(location);
					pricesForFruit.put(fruitName, priceRepository.findByFruitItemNameAndLocationLocalEngName(fruitName, localEngName));
					fruits.add(fruit);
				}
				
			}
		}
		
		return Fruit.Response.toResponseList(fruits, locationsForFruit, pricesForFruit);
	}

	@Override
	public Fruit.Response findFruitsByItemNameLocalEngName(String itemName, String localEngName) {
		Fruit fruit = fruitRepository.findDistinctByItemNameAndLocationsLocalEngName(itemName, localEngName);
		List<Location> location = locationRepository.findByLocalEngName(localEngName);
		List<Price> prices = priceRepository.findByFruitItemNameAndLocationLocalEngName(itemName, localEngName);
	
		return Fruit.Response.toResponse(fruit, location, prices); 
	}
	
	
	private List<Fruit.Response> findByFruits(List<Fruit> fruits) {
		Map<String, List<Location>> locations = new HashMap<String, List<Location>>();
		Map<String, List<Price>> prices = new HashMap<String, List<Price>>();
		
		for (Fruit fruit : fruits) {
			locations.put(fruit.getItemName(), locationRepository.findByFruitsItemName(fruit.getItemName()));
			prices.put(fruit.getItemName(), priceRepository.findByFruitItemName(fruit.getItemName()));
		}
		
		return Fruit.Response.toResponseList(fruits, locations, prices); 
	}
}









