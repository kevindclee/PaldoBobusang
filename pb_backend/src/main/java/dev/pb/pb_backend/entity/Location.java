package dev.pb.pb_backend.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import dev.pb.pb_backend.projection.LocationCountryCodeProjection;
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
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "LOCATION_ID")
	private int locationId;
	
	@Column(name = "LOCAL_NAME")
	private String localName;
	
	@Column(name = "CITY_NAME")
	private String cityName;
	
	@Column(name = "COUNTRY_CODE")
	private int countryCode;

	@OneToMany(mappedBy = "location")
	private List<Price> prices;

	@ManyToMany(mappedBy="locations")
	private List<Vegetable> vegetables;

	@ManyToMany(mappedBy="locations")
	private List<Fruit> fruits;
	
	// 요청 받을 때 사용할 User Entity의 DTO
	@Setter
	@Getter
	@Builder
	@ToString
	public static class Request {

		@NotNull
		private int locationId;
		
		@NotBlank(message = "localName는 공백('', ' ')이나 Null 지정 불가")
		private String localName;
		
		@NotBlank(message = "cityName는 공백('', ' ')이나 Null 지정 불가")
		private String cityName;
		
		@NotNull
		private int countryCode;

		public static Location toEntity(final Location.Request request) {
			return Location.builder()
					.locationId(request.getLocationId())
					.localName(request.getLocalName())
					.cityName(request.getCityName())
					.countryCode(request.getCountryCode())
					.build();
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
		private String cityName;
		private int countryCode;

		public static Location.Response toResponse(final Location location) {
			return Location.Response.builder()
					.locationId(location.getLocationId())
					.localName(location.getLocalName())
					.cityName(location.getCityName())
					.countryCode(location.getCountryCode())
					.build();
		}

		public static List<Location.Response> toResponseList(final List<Location> locations) {
			List<Location.Response> resList = new ArrayList<>();
			for (Location location : locations) {
				resList.add(Location.Response.toResponse(location));
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

		private int countryCode;

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
		
}







