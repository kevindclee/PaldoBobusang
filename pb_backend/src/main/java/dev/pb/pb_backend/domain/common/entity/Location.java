package dev.pb.pb_backend.domain.common.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import dev.pb.pb_backend.projection.LocationCountryCodeProjection;
import dev.pb.pb_backend.projection.LocationLocationIdProjection;
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
public class Location {

	@Id
	@Column(name = "LOCATION_ID")
	private int locationId;
	
	@Column(name = "LOCAL_NAME", nullable = false)
	private String localName;
	
	@Column(name = "LOCAL_ENG_NAME", nullable = false)
	private String localEngName;
	
	@Column(name = "CITY_NAME", nullable = false)
	private String cityName;
	
	@Column(name = "CITY_ENG_NAME", nullable = false)
	private String cityEngName;
	
	@Column(name = "COUNTRY_CODE")
	private Integer countryCode;

	@OneToMany(mappedBy = "location")
	@JsonIgnore
	private List<Price> prices;

	@JsonIgnore
	@ManyToMany(mappedBy="locations")
	private List<Vegetable> vegetables;

	@JsonIgnore
	@ManyToMany(mappedBy="locations")
	private List<Fruit> fruits;
	
	// 요청 받을 때 사용할 User Entity의 DTO
	@Setter
	@Getter
	@ToString
	public static class Request {

		@NotNull
		private int locationId;
		
		@NotBlank(message = "localName는 공백('', ' ')이나 Null 지정 불가")
		private String localName;
		private String localEngName;
		
		@NotBlank(message = "cityName는 공백('', ' ')이나 Null 지정 불가")
		private String cityName;
		private String cityEngName;
		
		private Integer countryCode;

		public static Location toEntity(final Location.Request request) {
			return Location.builder()
					.locationId(request.getLocationId())
					.localName(request.getLocalName())
					.localEngName(request.getLocalEngName())
					.cityName(request.getCityName())
					.cityEngName(request.getCityEngName())
					.countryCode(request.getCountryCode())
					.prices(new ArrayList<Price>())
					.vegetables(new ArrayList<Vegetable>())
					.fruits(new ArrayList<Fruit>())
					.build();
		}
		
	}

	// 서버가 응답할 때 사용할 User Entity의 DTO
	@Setter
	@Getter
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class OnlyLocation {
		
		private int locationId;
		private String localName;
		private String localEngName;
		private String cityName;
		private String cityEngName;
		private Integer countryCode;
		
		public static OnlyLocation toOnlyLocation(Location location) {
			
			return OnlyLocation.builder()
					.locationId(location.getLocationId())
					.localName(location.getLocalName())
					.localEngName(location.getLocalEngName())
					.cityName(location.getCityName())
					.cityEngName(location.getCityEngName())
					.countryCode(location.getCountryCode())
					.build();
		}
		
		public static List<OnlyLocation> toOnlyLocations(List<Location> locations) {
			List<Location.OnlyLocation> onlyLocations = null;
			locations.stream().forEach(location -> onlyLocations.add(Location.OnlyLocation.toOnlyLocation(location)));
			
			return onlyLocations; 
		}
	}
	// 서버가 응답할 때 사용할 User Entity의 DTO
	@Setter
	@Getter
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class Response {

		private int locationId;
		private String localName;
		private String localEngName;
		private String cityName;
		private String cityEngName;
		private Integer countryCode;
		private List<Vegetable.Response> vegetables;
		private List<Fruit.Response> fruits;

		public static Location.Response toResponse(final Location location, List<Fruit> fruits, List<Vegetable> vegetables, Map<String, List<Price>> prices) {
			return Location.Response.builder()
					.locationId(location.getLocationId())
					.localName(location.getLocalName())
					.localEngName(location.getLocalEngName())
					.cityName(location.getCityName())
					.cityEngName(location.getCityEngName())
					.countryCode(location.getCountryCode())
					.vegetables(Vegetable.Response.toResponse(vegetables, location, prices))
					.fruits(Fruit.Response.toResponse(fruits, location, prices))
					.build();
		}

		public static List<Location.Response> toResponseList(final List<Location> locations, Map<Integer, List<Fruit>> fruits, Map<Integer, List<Vegetable>> vegetables, Map<Integer, Map<String, List<Price>>> prices) {
			List<Location.Response> resList = new ArrayList<>();
			locations.stream().forEach(location -> resList.add(toResponse(location, fruits.get(location.getLocationId()), vegetables.get(location.getLocationId()), prices.get(location.getLocationId()))));
			return resList;
		}
		
		public static Location.Response toFruitResponse(final Location location, int fruitCode) {
			return Location.Response.builder()
					.locationId(location.getLocationId())
					.localName(location.getLocalName())
					.localEngName(location.getLocalEngName())
					.cityName(location.getCityName())
					.cityEngName(location.getCityEngName())
					.countryCode(location.getCountryCode())
					.prices(Price.Response.toLocationFruitResponseList(location.getPrices(), fruitCode))
					.build();
		}

		public static List<Location.Response> toFruitResponseList(final List<Location> locations, int fruitCode) {
			List<Location.Response> resList = new ArrayList<>();
			for (Location location : locations) {
				resList.add(Location.Response.toFruitResponse(location, fruitCode));
			}
			return resList;
		}
		
		public static Location.Response toFruitItemNameAndLocalEngNameResponse(final Location location, int fruitCode) {
			return Location.Response.builder()
					.locationId(location.getLocationId())
					.localName(location.getLocalName())
					.localEngName(location.getLocalEngName())
					.cityName(location.getCityName())
					.cityEngName(location.getCityEngName())
					.countryCode(location.getCountryCode())
					.prices(Price.Response.toLocationFruitResponseList(location.getPrices(), fruitCode))
					.build();
		}

		public static List<Location.Response> toFruitItemNameAndLocalEngNameResponseList(final List<Location> locations, int fruitCode, String localEngName) {
			List<Location.Response> resList = new ArrayList<>();
			for (Location location : locations) {
				if (localEngName.equals(location.getLocalEngName())) {
					resList.add(Location.Response.toFruitResponse(location, fruitCode));
				}
			}
			return resList;
		}
		
		public static Location.Response toVegetableResponse(final Location location, int vegetableCode) {
			return Location.Response.builder()
					.locationId(location.getLocationId())
					.localName(location.getLocalName())
					.localEngName(location.getLocalEngName())
					.cityName(location.getCityName())
					.cityEngName(location.getCityEngName())
					.countryCode(location.getCountryCode())
					.prices(Price.Response.toLocationVegetableResponseList(location.getPrices(), vegetableCode))
					.build();
		}

		public static List<Location.Response> toVegetableResponseList(final List<Location> locations, int vegetableCode) {
			List<Location.Response> resList = new ArrayList<>();
			for (Location location : locations) {
				resList.add(Location.Response.toVegetableResponse(location, vegetableCode));
			}
			return resList;
		}
		
		public static Location.Response toVegetableItemNameAndLocalEngNameResponse(final Location location, int vegetableCode) {
			return Location.Response.builder()
					.locationId(location.getLocationId())
					.localName(location.getLocalName())
					.localEngName(location.getLocalEngName())
					.cityName(location.getCityName())
					.cityEngName(location.getCityEngName())
					.countryCode(location.getCountryCode())
					.prices(Price.Response.toLocationFruitResponseList(location.getPrices(), vegetableCode))
					.build();
		}

		public static List<Location.Response> toVegetableItemNameAndLocalEngNameResponseList(final List<Location> locations, int vegetableCode, String localEngName) {
			List<Location.Response> resList = new ArrayList<>();
			for (Location location : locations) {
				if (localEngName.equals(location.getLocalEngName())) {
					resList.add(Location.Response.toFruitResponse(location, vegetableCode));
				}
			}
			return resList;
		}
		
		public static Location.Response toPriceResponse(final Location location) {
			return Location.Response.builder()
					.locationId(location.getLocationId())
					.localName(location.getLocalName())
					.localEngName(location.getLocalEngName())
					.cityName(location.getCityName())
					.cityEngName(location.getCityEngName())
					.countryCode(location.getCountryCode())
					.build();
		}

		public static List<Location.Response> toPriceResponseList(final List<Location> locations) {
			List<Location.Response> resList = new ArrayList<>();
			for (Location location : locations) {
				resList.add(Location.Response.toPriceResponse(location));
			}
			return resList;
		}
	}
	
	// LocaionCountryCode Projection 을 이용한 Response DTO
	@Setter
	@Getter
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class ResponseCountryCode {

		private Integer countryCode;

		public static Location.ResponseCountryCode toResponse(final LocationCountryCodeProjection location) {
			return Location.ResponseCountryCode.builder()
					.countryCode(location.getCountryCode())
					.build();
		}

		public static List<Location.ResponseCountryCode> toResponseList(final List<LocationCountryCodeProjection> locations) {
			List<Location.ResponseCountryCode> resList = new ArrayList<>();
			for (LocationCountryCodeProjection location : locations) {
				resList.add(Location.ResponseCountryCode.toResponse(location));
			}
			return resList;
		}
	}
	
	// LocaionCountryCode Projection 을 이용한 Response DTO
	@Setter
	@Getter
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class ResponseLocationId {

		private int locationId;

		public static Location.ResponseLocationId toResponse(final LocationLocationIdProjection location) {
			return Location.ResponseLocationId.builder()
					.locationId(location.getLocationId())
					.build();
		}

		public static List<Location.ResponseLocationId> toResponseList(final List<LocationLocationIdProjection> locations) {
			List<Location.ResponseLocationId> resList = new ArrayList<>();
			for (LocationLocationIdProjection location : locations) {
				resList.add(Location.ResponseLocationId.toResponse(location));
			}
			return resList;
		}
	}
		
}







