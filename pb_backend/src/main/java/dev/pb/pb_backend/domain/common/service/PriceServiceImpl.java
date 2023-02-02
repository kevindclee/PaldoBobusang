package dev.pb.pb_backend.domain.common.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.pb.pb_backend.domain.common.entity.Fruit;
import dev.pb.pb_backend.domain.common.entity.Location;
import dev.pb.pb_backend.domain.common.entity.Price;
import dev.pb.pb_backend.domain.common.entity.Price.Request;
import dev.pb.pb_backend.domain.common.entity.Vegetable;
import dev.pb.pb_backend.domain.common.repository.FruitRepository;
import dev.pb.pb_backend.domain.common.repository.LocationRepository;
import dev.pb.pb_backend.domain.common.repository.PriceRepository;
import dev.pb.pb_backend.domain.common.repository.VegetableRepository;

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
	public Map<String, List<Object>> findAllPrices() {
		List<Price> prices = priceRepository.findAll();
		Map<String, List<Object>> pricesMap = new HashMap<String, List<Object>>();

		for (Price price : prices) {
			if (price.getFruit() != null) {
				String fruitName = price.getFruit().getItemName();
				if (pricesMap.containsKey(fruitName)) pricesMap.get(fruitName).add(Price.ForFruit.forFruit(price));
				else {
					pricesMap.put(fruitName, new ArrayList<Object>());
					pricesMap.get(fruitName).add(Price.ForFruit.forFruit(price));
				}
			} else {
				String vegetableName = price.getVegetable().getItemName();
				if (pricesMap.containsKey(vegetableName)) pricesMap.get(vegetableName).add(Price.ForFruit.forFruit(price));
				else {
					pricesMap.put(vegetableName, new ArrayList<Object>());
					pricesMap.get(vegetableName).add(Price.ForFruit.forFruit(price));
				}
			}
		}
		
		return pricesMap; 
	}

	@Override
	public Object findPriceByPriceId(int priceId) {
		Price price = priceRepository.findById(priceId).get();

		return findByPrice(price);
	}

	

	@Override
	public Object createPrice(Request request) {
		Fruit fruit = fruitRepository.findById(request.getItemCode()).orElse(null);
		Vegetable vegetable = vegetableRepository.findById(request.getItemCode()).orElse(null);
		Location location = locationRepository.findById(request.getLocationId()).get();
		
		if (fruit != null) {
			Price savedPrice = priceRepository.save(Price.Request.toEntity(request, location, fruit));
			savedPrice.setFruit(fruit);
			savedPrice.setLocation(location);
			fruitRepository.save(fruit);
			locationRepository.save(location);
			
			return Price.ForFruit.forFruit(savedPrice);
		} else {
			if (vegetable == null) return null;
			
			Price savedPrice = priceRepository.save(Price.Request.toEntity(request, location, vegetable));
			savedPrice.setVegetable(vegetable);
			savedPrice.setLocation(location);
			vegetableRepository.save(vegetable);
			locationRepository.save(location);
			
			return Price.ForVegetable.forVegetable(savedPrice);
		}

	}

	@Override
	public List<Object> findByLocationCountryCode(int countryCode) {
		List<Price> prices = priceRepository.findByLocationCountryCode(countryCode);
		
		return findByPrices(prices); 
	}

	@Override
	public List<Object> findByItemCode(int itemCode) {
		Fruit fruit = fruitRepository.findById(itemCode).orElse(null);
		
		if (fruit != null) {
			List<Price> prices = priceRepository.findByFruitItemCode(itemCode);
			
			return findByPrices(prices); 
		} else {
			List<Price> prices = priceRepository.findByVegetableItemCode(itemCode);
			
			return findByPrices(prices); 
		}
	}

	@Override
	public List<Object> findByItemCodeAndLocalName(int itemCode, String localEngName) {
		Fruit fruit = fruitRepository.findById(itemCode).orElse(null);
		
		if (fruit != null) {
			List<Price> prices = priceRepository.findByFruitItemCodeAndLocationLocalEngName(itemCode, localEngName);
			
			return findByPrices(prices); 
		} else {
			List<Price> prices = priceRepository.findByVegetableItemCodeAndLocationLocalEngName(itemCode, localEngName);
			
			return findByPrices(prices); 
		}
	}

	@Override
	public List<Object> findByItemNameAndLocalEngName(String itemName, String localEngName) {
		Fruit fruit = fruitRepository.findByItemNameAndLocationsLocalEngName(itemName, localEngName);
		
		if (fruit != null) {
			List<Price> prices = priceRepository.findByFruitItemNameAndLocationLocalEngName(itemName, localEngName);
			
			return findByPrices(prices); 
		} else {
			List<Price> prices = priceRepository.findByVegetableItemNameAndLocationLocalEngName(itemName, localEngName);
			
			return findByPrices(prices); 
		}
	}

	@Override
	public Object findByItemCodeAndLocationIdAndPriceDate(int itemCode, int locationId, LocalDate priceDate) {
		Fruit fruit = fruitRepository.findById(itemCode).orElse(null);
		
		if (fruit != null) {
			Price price = priceRepository.findByFruitItemCodeAndLocationLocationIdAndPriceDate(itemCode, locationId, priceDate);
			
			return findByPrice(price); 
		} else {
			Price price = priceRepository.findByVegetableItemCodeAndLocationLocationIdAndPriceDate(itemCode, locationId, priceDate);
			
			return findByPrice(price); 
		}
	}

	private Object findByPrice(Price price) {

		if (price.getFruit() != null) return Price.ForFruit.forFruit(price);  
		else return Price.ForVegetable.forVegetable(price);
	}
	
	private List<Object> findByPrices(List<Price> prices) {
		List<Object> resList = new ArrayList<Object>();
		prices.stream().forEach(price -> resList.add(findByPrice(price)));
		
		return resList;
	}

}









