package dev.pb.pb_backend.domain.common.controller;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.pb.pb_backend.domain.common.entity.Price;
import dev.pb.pb_backend.domain.common.service.PriceService;

@RestController
@RequestMapping("prices")

public class PriceController {
	
	@Autowired
	private PriceService priceService;
//	
//	// 'GET' http://localhost:8090/prices
	@GetMapping
	public Map<String, List<Object>> findAllPrices() {
		System.out.println("\nGET: findAllPrices() of PriceController called\n");		

		return priceService.findAllPrices();
	}
	
//	// 'GET' http://localhost:8090/prices/:priceId
	@GetMapping("/{priceId}")
	public Object findLocationByLocationId(@PathVariable int priceId) {
		System.out.println("\nGET: findLocationByLocationId() of PriceController called\n");		
		
		return priceService.findPriceByPriceId(priceId);
	}
	
	// 'POST' http://localhost:8090/prices
	@PostMapping
	public Object createPrice(@RequestBody @Valid Price.Request request) {
		System.out.println("POST: createPrice() of PriceController called");		
		
		return priceService.createPrice(request);
	}
//
//	// 'GET' http://localhost:8090/prices/location/countryCode/:countryCode
	@GetMapping("/location/countryCode/{countryCode}")
	public List<Object> findByLocationCountryCode(@PathVariable int countryCode) {
		System.out.println("\nGET: findByLocationCountryCode() of PriceController called\n");		
		
		return priceService.findByLocationCountryCode(countryCode);
	}

//	// 'GET' http://localhost:8090/prices/list/itemCode/:itemCode
	@GetMapping("/list/itemCode/{itemCode}")
	public List<Object> findByFruitCode(@PathVariable int itemCode) {
		System.out.println("\nGET: findByItemCode() of PriceController called\n");		

		return priceService.findByItemCode(itemCode);
	}

	// 'GET' http://localhost:8090/prices/list/itemCodeAndLocationId/:itemCode/:locationId
	@GetMapping("/list/itemCodeAndLocationId/{itemCode}/{locationId}")
	public List<Object> findByItemCodeAndLocationId(@PathVariable int itemCode, @PathVariable int locationId) {
		System.out.println("\nGET: findByItemCodeAndLocationId() of PriceController called\n");		

		return priceService.findByItemCodeAndLocationId(itemCode, locationId);
	}

	// 'GET' http://localhost:8090/prices/map/fruitItemNameAndLocalEngName/:itemName/:localEngName
	@GetMapping("/map/fruitItemNameAndLocalEngName/{itemName}/{localEngName}")
	public List<Object> findByItemNameAndLocalEngName(@PathVariable String itemName, @PathVariable String localEngName) {
		System.out.println("\nGET: findByItemNameAndLocalEngName() of PriceController called\n");		

		return priceService.findByItemNameAndLocalEngName(itemName, localEngName);
	}

	// 'GET' http://localhost:8090/prices/fruitDateCheck/:fruitCode/:locationId/:priceDate
	@GetMapping("/fruitDateCheck/{fruitCode}/{locationId}/{priceDate}")
	public Object findByItemCodeAndLocationIdAndPriceDate(@PathVariable int itemCode, @PathVariable int locationId, @PathVariable String priceDate) {
		System.out.println("\nGET: findByItemCodeAndLocationIdAndPriceDate() of PriceController called");
		LocalDate localDate = LocalDate.parse(priceDate);
		
		return priceService.findByItemCodeAndLocationIdAndPriceDate(itemCode, locationId, localDate);
	}
	
}
