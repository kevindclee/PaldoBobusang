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

import dev.pb.pb_backend.domain.common.entity.Location;
import dev.pb.pb_backend.domain.common.entity.Price;
import dev.pb.pb_backend.domain.common.entity.Vegetable;
import dev.pb.pb_backend.domain.common.entity.Vegetable.Request;
import dev.pb.pb_backend.domain.common.entity.Vegetable.Response;
import dev.pb.pb_backend.domain.common.repository.LocationRepository;
import dev.pb.pb_backend.domain.common.repository.PriceRepository;
import dev.pb.pb_backend.domain.common.repository.VegetableRepository;

@Service
public class VegetableServiceImpl implements VegetableService {
	
	@Autowired
	private VegetableRepository vegetableRepository;
	
	@Autowired
	private LocationRepository locationRepository;
	
	@Autowired
	private PriceRepository priceRepository;
	
	@Override
	public List<Vegetable.Response> findAllVegetables() {
		List<Vegetable> vegetables = vegetableRepository.findAll();
		
		return findByVegetables(vegetables);
	}

	@Override
	public Vegetable.Response findVegetableByCode(int code) {
		Vegetable vegetable = vegetableRepository.findById(code).get();
		List<Location> locations = locationRepository.findByVegetablesItemName(vegetable.getItemName());
		List<Price> prices = priceRepository.findByVegetableItemName(vegetable.getItemName());;
		
		return Vegetable.Response.toResponse(vegetable, locations, prices); 
	}

	@Override
	public Response createVegetable(Request request) {
		List<Location> locations = new ArrayList<Location>();
		request.getLocationIds().stream().forEach(locationid -> locations.add(locationRepository.findById(locationid).get()));
		Vegetable savedVegetable = vegetableRepository.save(Vegetable.Request.toEntity(request, locations));
		savedVegetable.setLocations(locations);
		locations.stream().forEach(location -> locationRepository.save(location));

		return Vegetable.Response.toResponse(savedVegetable, locations, null);
	}

	@Override
	public List<Vegetable.Response> findVegetablesByLocationId(int locationId) {
		List<Vegetable> vegetables = vegetableRepository.findByLocationsLocationId(locationId);
		
		return findByVegetables(vegetables);
	}

	@Override
	public List<Vegetable.Response> findVegetablesByHarvest() {
		ZonedDateTime curDate = ZonedDateTime.now();
		LocalDate plusOneDay = LocalDate.from(curDate.plusDays(1));
		LocalDate minusOneDay = LocalDate.from(curDate.minusDays(1));
		List<Vegetable> vegetables = vegetableRepository.findByHarvestStartBeforeAndHarvestEndAfter(plusOneDay, minusOneDay);
		
		return findByVegetables(vegetables);
	}

	@Override
	public Vegetable.Response updateVegetable(Vegetable.Request request) {
		Vegetable foundVegetable = vegetableRepository.findById(request.getItemCode()).get();
		Vegetable savedVegetable = vegetableRepository.save(Vegetable.Request.updateVegetable(foundVegetable, request));

		return Vegetable.Response.toResponse(savedVegetable, savedVegetable.getLocations(), savedVegetable.getPrices());
	}

	@Override
	public List<Vegetable.Response> findVegetablesByLocalEngName(String localEngName) {
		List<Location> locations = locationRepository.findByLocalEngName(localEngName);
		Map<String, List<Location>> locationsForVegetable = new HashMap<String, List<Location>>();
		Map<String, List<Price>> pricesForVegetable = new HashMap<String, List<Price>>();
		Set<Vegetable> vegetables = new HashSet<Vegetable>();
		
		for (Location location : locations) {
			List<Vegetable> vegetableList = vegetableRepository.findByLocationsCityName(location.getCityName());
			for (Vegetable vegetable : vegetableList) {
				String vegetableName = vegetable.getItemName();
				if (locationsForVegetable.containsKey(vegetableName)) locationsForVegetable.get(vegetableName).add(location);
				else {
					locationsForVegetable.put(vegetableName, new ArrayList<Location>());
					locationsForVegetable.get(vegetableName).add(location);
					pricesForVegetable.put(vegetableName, priceRepository.findByVegetableItemNameAndLocationLocalEngName(vegetable.getItemName(), localEngName));
					vegetables.add(vegetable);
				}
			}
		}
		
		return Vegetable.Response.toResponseList(vegetables, locationsForVegetable, pricesForVegetable);
	}

	@Override
	public Vegetable.Response findVegetablesByItemNameLocalEngName(String itemName, String localEngName) {
		Vegetable vegetable = vegetableRepository.findDistinctByItemNameAndLocationsLocalEngName(itemName, localEngName);
		List<Location> locations = locationRepository.findByVegetablesItemNameAndLocalEngName(itemName, localEngName);
		List<Price> prices = priceRepository.findByVegetableItemNameAndLocationLocalEngName(itemName, localEngName);
		
		return Vegetable.Response.toResponse(vegetable, locations, prices); 
	}
	
	private List<Vegetable.Response> findByVegetables(List<Vegetable> vegetables) {
		Map<String, List<Location>> locations = new HashMap<String, List<Location>>();
		Map<String, List<Price>> prices = new HashMap<String, List<Price>>();
		
		for (Vegetable vegetable : vegetables) {
			locations.put(vegetable.getItemName(), locationRepository.findByVegetablesItemName(vegetable.getItemName()));
			prices.put(vegetable.getItemName(), priceRepository.findByFruitItemName(vegetable.getItemName()));
		}
		
		return Vegetable.Response.toResponseList(vegetables, locations, prices); 
	}

}









