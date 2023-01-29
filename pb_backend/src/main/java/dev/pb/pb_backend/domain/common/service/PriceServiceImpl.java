package dev.pb.pb_backend.domain.common.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.pb.pb_backend.domain.common.entity.Fruit;
import dev.pb.pb_backend.domain.common.entity.Location;
import dev.pb.pb_backend.domain.common.entity.Price;
import dev.pb.pb_backend.domain.common.entity.Price.Request;
import dev.pb.pb_backend.domain.common.entity.Price.Response;
import dev.pb.pb_backend.domain.common.entity.Vegetable;
import dev.pb.pb_backend.domain.common.repository.FruitRepository;
import dev.pb.pb_backend.domain.common.repository.LocationRepository;
import dev.pb.pb_backend.domain.common.repository.PriceRepository;
import dev.pb.pb_backend.domain.common.repository.VegetableRepository;
import dev.pb.pb_backend.projection.PriceLocationIdProjection;
import dev.pb.pb_backend.projection.PriceProjection;

@Service
public class PriceServiceImpl implements PriceService {
	
	@Autowired
	private PriceRepository priceRepository;

	@Autowired
	private LocationRepository locationRepository;
	
	@Autowired
	private FruitRepository fruitRepository;
	
	@Autowired
	private VegetableRepository vegetableRepository;
	
	@Override
	public List<Price> findAllPrices() {
		return priceRepository.findAll();
	}

	@Override
	public Price findPriceByPriceId(int priceId) {
		return priceRepository.findById(priceId).orElseThrow(() -> new RuntimeException(String.format("%d priceId에 해당하는 Price가 존재하지 않습니다.", priceId)));
	}

	@Override
	public Response createPrice(Request request) {
		Fruit fruit = fruitRepository.findById(request.getItemCode()).orElseThrow(() -> null);
		Vegetable vegetable = vegetableRepository.findById(request.getItemCode()).orElseThrow(() -> null);
		Location location = locationRepository.findById(request.getLocationId()).get();
		
		if (fruit != null) {
			Price toSave = Price.Request.toEntity(request, location, fruit);
			priceRepository.save(toSave);
			toSave.setFruit(fruit);
			toSave.setLocation(location);
			fruitRepository.save(fruit);
			locationRepository.save(location);
		} else {
			Price toSave = Price.Request.toEntity(request, location, vegetable);
			priceRepository.save(toSave);
			toSave.setVegetable(vegetable);
			toSave.setLocation(location);
			vegetableRepository.save(vegetable);
			locationRepository.save(location);
		}

		return Price.Response.;
	}

	@Override
	public List<Price> findByLocationCountryCode(int countryCode) {
		return priceRepository.findByLocationCountryCode(countryCode);
	}

	@Override
	public List<PriceLocationIdProjection> findByVegetableitemCode(int itemCode) {
		return priceRepository.findDistinctByVegetableItemCode(itemCode);
	}

	@Override
	public List<PriceLocationIdProjection> findByFruitItemCode(int itemCode) {
		return priceRepository.findDistinctByFruitItemCode(itemCode);
	}

	@Override
	public List<Price.Response> findByVegetableItemCodeAndLocalEngName(int itemCode, String localEngName) {
		List<Location> locations = locationRepository.findByLocalEngName(localEngName);
		List<Integer> markets = new ArrayList<Integer>();
		
		for (Location location : locations) {
			if (location.getCountryCode() != null) markets.add(location.getLocationId());
		}
		
		List<PriceProjection> prices = new ArrayList<PriceProjection>();
		
		for (Integer locationId : markets) {
			prices = priceRepository.findByVegetableItemCodeAndLocationLocationId(itemCode, locationId);
		}
		
		return Price.Response.toResponseList(prices);
	}

	@Override
	public List<Price.Response> findByFruitItemCodeAndLocalEngName(int itemCode, String localEngName) {
		List<Location> locations = locationRepository.findByLocalEngName(localEngName);
		List<Integer> markets = new ArrayList<Integer>();
		
		for (Location location : locations) {
			if (location.getCountryCode() != null) markets.add(location.getLocationId());
		}
		
		List<PriceProjection> prices = new ArrayList<PriceProjection>();
		
		for (Integer locationId : markets) {
			prices = priceRepository.findByFruitItemCodeAndLocationLocationId(itemCode, locationId);
		}
		
		
		return Price.Response.toResponseList(prices); 
	}

	@Override
	public List<Price> findByFruitItemNameAndLocalEngName(String itemName, String localEngName) {
		return priceRepository.findByFruitItemNameAndLocationLocalEngName(itemName, localEngName);
	}

	@Override
	public List<Price> findByVegetableItemNameAndLocalEngName(String itemName, String localEngName) {
		return priceRepository.findByVegetableItemNameAndLocationLocalEngName(itemName, localEngName);
	}

	@Override
	public Price findByFruitItemCodeAndLocationIdAndPriceDate(int itemCode, int locationId, Date priceDate) {
		return priceRepository.findByFruitItemCodeAndLocationLocationIdAndPriceDate(itemCode, locationId, priceDate);
	}

	@Override
	public Price findByVegetableitemCodeAndLocationIdAndPriceDate(int itemCode, int locationId, Date priceDate) {
		return priceRepository.findByVegetableItemCodeAndLocationLocationIdAndPriceDate(itemCode, locationId, priceDate);
	}

}









