package dev.pb.pb_backend.domain.common.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import dev.pb.pb_backend.domain.common.entity.Fruit.OnlyFruit;
import dev.pb.pb_backend.projection.PriceLocationIdProjection;
import dev.pb.pb_backend.projection.PriceProjection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

//Jpa 설정
@Entity

//Lombok 설정
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@ToString
public class Price {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PRICE_ID")
	private long priceId;
	
	private int priceValue;
	
	@Column(name = "PRICE_DATE")
	@Temporal(TemporalType.DATE)
	private LocalDate priceDate;
	
	@ManyToOne
	@JoinColumn(name = "LOCATION_ID")
	@JsonIgnore
	private Location location;

	@ManyToOne
	@JoinColumn(name = "VEGETABLE_CODE")
	@JsonIgnore
	private Vegetable vegetable;

	@ManyToOne
	@JoinColumn(name = "FRUIT_CODE")
	@JsonIgnore
	private Fruit fruit;
	
	public void setLocation(Location location) {
		location.getPrices().add(this);
	}
	
	public void setFruit(Fruit fruit) {
		fruit.getPrices().add(this);
	}
	
	public void setVegetable(Vegetable vegetable) {
		vegetable.getPrices().add(this);
	}
	
	// 요청 받을 때 사용할 User Entity의 DTO
	@Setter
	@Getter
	@Builder
	@ToString
	public static class Request {

		private int priceId;
		private int priceValue;
		private LocalDate priceDate;
		private int itemCode;
		private int locationId;

		public static Price toEntity(final Price.Request request, Location location, Fruit fruit) {
			return Price.builder()
					.priceId(request.getPriceId())
					.priceValue(request.getPriceValue())
					.priceDate(request.getPriceDate())
					.location(location)
					.fruit(fruit)
					.build();
		}
		
		public static Price toEntity(final Price.Request request, Location location, Vegetable vegetable) {
			return Price.builder()
					.priceId(request.getPriceId())
					.priceValue(request.getPriceValue())
					.priceDate(request.getPriceDate())
					.location(location)
					.vegetable(vegetable)
					.build();
		}
		
	}
	
	// 서버가 응답할 때 사용할 User Entity의 DTO
	@Setter
	@Getter
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class OnlyPrice {
		
		private long priceId;
		private int priceValue;
		private LocalDate priceDate;
		
		public static OnlyPrice toOnlyPrice(Price price) {
			
			return OnlyPrice.builder()
					.priceId(price.getPriceId())
					.priceValue(price.getPriceValue())
					.priceDate(price.getPriceDate())
					.build();
		}
		
		public static List<OnlyPrice> toOnlyPrices(List<Price> prices) {
			List<Price.OnlyPrice> onlyPrices = null;
			prices.stream().forEach(price -> onlyPrices.add(Price.OnlyPrice.toOnlyPrice(price))); 

			return onlyPrices; 
		}
	}

	// 서버가 응답할 때 사용할 User Entity의 DTO
	@Setter
	@Getter
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class ForFruit {

		private long priceId;
		private int priceValue;
		private LocalDate priceDate;
		private Location.OnlyLocation location;
		private Fruit.OnlyFruit fruit;
		
		public static ForFruit forFruit(Price price) {
			
			return ForFruit.builder()
					.priceId(price.getPriceId())
					.priceValue(price.getPriceValue())
					.priceDate(price.getPriceDate())
					.location(Location.OnlyLocation.toOnlyLocation(price.getLocation()))
					.fruit(Fruit.OnlyFruit.toOnlyFruit(price.getFruit()))
					.build();
		}
	}
	// 서버가 응답할 때 사용할 User Entity의 DTO
	@Setter
	@Getter
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class ForVegetable {
		
		private long priceId;
		private int priceValue;
		private LocalDate priceDate;
		private Location.OnlyLocation location;
		private Vegetable.OnlyVegetable vegetable;
		
		public static ForVegetable forVegetable(Price price) {
			
			return ForVegetable.builder()
					.priceId(price.getPriceId())
					.priceValue(price.getPriceValue())
					.priceDate(price.getPriceDate())
					.location(Location.OnlyLocation.toOnlyLocation(price.getLocation()))
					.vegetable(Vegetable.OnlyVegetable.toOnlyVegetable(price.getVegetable()))
					.build();
		}
	}
}






