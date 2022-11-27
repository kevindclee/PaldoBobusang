package dev.pb.pb_backend.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.pb.pb_backend.entity.Location;
import dev.pb.pb_backend.entity.Price;
import dev.pb.pb_backend.projection.PriceLocationIdProjection;
import dev.pb.pb_backend.projection.PriceProjection;
import dev.pb.pb_backend.repository.LocationRepository;
import dev.pb.pb_backend.repository.PriceRepository;

@Service
public class PriceServiceImpl implements PriceService {
	
	@Autowired
	private PriceRepository priceRepository;

	@Autowired
	private LocationRepository locationRepository;
	
	@Override
	public List<Price> findAllPrices() {
		return priceRepository.findAll();
	}

	@Override
	public Price findPriceByPriceId(int priceId) {
		return priceRepository.findById(priceId).orElseThrow(() -> new RuntimeException(String.format("%d priceId에 해당하는 Price가 존재하지 않습니다.", priceId)));
	}

	@Override
	public Price createPrice(Price newPrice) {
		return priceRepository.save(newPrice);
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









