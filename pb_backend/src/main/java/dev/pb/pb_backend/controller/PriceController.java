package dev.pb.pb_backend.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.pb.pb_backend.entity.Price;
import dev.pb.pb_backend.service.PriceService;

@RestController
@RequestMapping("prices")
@CrossOrigin(origins = "http://localhost:3000")
public class PriceController {
	
	@Autowired
	private PriceService priceService;
	
	// 'GET' http://localhost:8090/prices
	@GetMapping
	public List<Price.Response> findAllPrices() {
		System.out.println("GET: findAllPrices() of PriceController called");		
		List<Price> priceList = priceService.findAllPrices();
		return Price.Response.toResponseList(priceList);
	}
	
	// 'GET' http://localhost:8090/prices/:priceId
	@GetMapping("/{priceId}")
	public Price.Response findLocationByLocationId(@PathVariable int priceId) {
		System.out.println("GET: findLocationByLocationId() of PriceController called");		
		Price foundPrice = priceService.findPriceByPriceId(priceId);
		return Price.Response.toResponse(foundPrice);
	}
	
	// 'POST' http://localhost:8090/prices
	@PostMapping
	public ResponseEntity<Price.Response> createPrice(@RequestBody @Valid Price.Request request) {
		System.out.println("POST: createPrice() of PriceController called");		
		Price newPrice = Price.Request.toEntity(request);
		//Address address = request.getAddress();
		Price savedPrice = priceService.createPrice(newPrice);
		//address.setUser(savedUser);
		//addressRepository.save(address);
		Price.Response response = Price.Response.toResponse(savedPrice);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	// 'GET' http://localhost:8090/prices/location/countryCode/:countryCode
	@GetMapping("/location/countryCode/{countryCode}")
	public List<Price.Response> findByLocationCountryCode(@PathVariable int countryCode) {
		System.out.println("GET: findByLocationCountryCode() of PriceController called");		
		List<Price> priceList = priceService.findByLocationCountryCode(countryCode);
		return Price.Response.toResponseList(priceList);
	}
	
}
