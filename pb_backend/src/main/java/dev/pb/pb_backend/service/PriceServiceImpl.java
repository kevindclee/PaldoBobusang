package dev.pb.pb_backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.pb.pb_backend.entity.Price;
import dev.pb.pb_backend.repository.PriceRepository;

@Service
public class PriceServiceImpl implements PriceService {
	
	@Autowired
	private PriceRepository priceRepository;

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

}









