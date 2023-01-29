package dev.pb.pb_backend.domain.common.service;

import java.util.ArrayList;
import java.util.Date;
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
import dev.pb.pb_backend.projection.ItemProjection;

@Service
public class FruitServiceImpl implements FruitService {
	
	private final Long MILLI_SECONDS_IN_DAY = (long) 86400000;

	@Autowired
	private FruitRepository fruitRepository;
	
	@Autowired
	private LocationRepository locationRepository;
	
	@Autowired
	private PriceService priceService;
	
	@Override
	public List<Fruit> findAllFruits() {
		return fruitRepository.findAll();
	}

	@Override
	public Fruit findFruitByCode(int code) {
		return fruitRepository.findById(code).orElseThrow(() -> new RuntimeException(String.format("%d 코드에 해당하는 Fruit가 존재하지 않습니다.", code)));
	}


	@Override
	public Response createFruit(Request request) {
		List<Location> locations = null;
		request.getLocationIds().stream().forEach(locationId -> locations.add(locationRepository.findById(locationId).get()));
		Fruit toSave = Fruit.Request.toEntity(request, locations);
		fruitRepository.save(toSave);
		toSave.setLocations(locations);
		locations.stream().forEach(location -> locationRepository.save(location));

		return Fruit.Response.toResponse(toSave, locations, null);
	}

	@Override
	public List<Fruit> findFruitsByLocationId(int locationId) {
		return fruitRepository.findByLocationsLocationId(locationId);
	}

	@Override
	public List<Fruit> findFruitsByHarvest(Date curDate) {
		Long curMilliSeconds = curDate.getTime();
		Date plusOneDay = new Date();
		plusOneDay.setTime(curMilliSeconds + MILLI_SECONDS_IN_DAY);
		Date minusOneDay = new Date();
		minusOneDay.setTime(curMilliSeconds - MILLI_SECONDS_IN_DAY);
		return fruitRepository.findByHarvestStartBeforeAndHarvestEndAfter(plusOneDay, minusOneDay);
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
	public Fruit updateFruit(Request request) {
		final Fruit foundFruit = findFruitByCode(request.getItemCode());
		List<Location> locationList = foundFruit.getLocations();
		boolean test = false;
		int index = 0;
		for (Location loc : locationList) {
			if (loc.getLocationId() == request.getLocations().get(0).getLocationId()) {
				test = true;
				break;
			}
			index ++;
		}
		if (test) {
			List<Price> priceList = locationList.get(index).getPrices();
			if (request.getPrices() != null) {
				priceList.addAll(request.getPrices());
			}
			foundFruit.setLocations(locationList);
		} else {
			if (request.getLocations() != null) {
				locationList.addAll(request.getLocations());
			}
			foundFruit.setLocations(locationList);
		}
		Fruit savedFruit = fruitRepository.save(foundFruit);
		return savedFruit;
	}

	@Override
	public List<Object> findFruitsByLocalEngName(String localEngName) {
		List<Location> locations = locationRepository.findByLocalEngName(localEngName);
		Map<String, List<Location>> locationsForFruit = new HashMap<String, List<Location>>();
		Map<String, List<Price>> pricesForFruit = new HashMap<String, List<Price>>();
		Set<ItemProjection> fruits = new HashSet<ItemProjection>();
		
		for (Location location : locations) {
			List<ItemProjection> fruit = fruitRepository.findByLocationsCityName(location.getCityName());
			for (ItemProjection item : fruit) {
				String fruitName = item.getItemName();
				if (locationsForFruit.containsKey(fruitName)) locationsForFruit.get(fruitName).add(location);
				else {
					locationsForFruit.put(fruitName, new ArrayList<Location>());
					locationsForFruit.get(fruitName).add(location);
					pricesForFruit.put(fruitName, priceService.findByFruitItemNameAndLocalEngName(fruitName, localEngName));
					fruits.add(item);
				}
			}
		}
		
		return Fruit.Response.toResponseList(fruits, locationsForFruit, pricesForFruit);
	}

	@Override
	public Fruit findFruitsByItemNameLocalEngName(String itemName, String localEngName) {
		return fruitRepository.findDistinctByItemNameAndLocationsLocalEngName(itemName, localEngName);
	}

}









