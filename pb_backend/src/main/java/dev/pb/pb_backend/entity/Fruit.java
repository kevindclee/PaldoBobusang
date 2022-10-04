package dev.pb.pb_backend.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

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
@Getter
@Setter
@ToString
public class Fruit {

	@Id
	private int code;

	@Column(name = "ITEM_NAME", nullable = false)
	private String itemName;

	@Column(name = "KIND_NAME", nullable = false)
	private String kindName;

	@Column(nullable = false)
	private String image;

	@Column(name = "HARVEST_START", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date harvestStart;

	@Column(name = "HARVEST_END", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date harvestEnd;

	@Column(name = "ETC_DETAILS", nullable = false, columnDefinition = "TEXT")
	private String etcDetails;

	@Column(nullable = false)
	private int brix;

	@ManyToMany
	@JoinTable(
			name = "FRUIT_LOCATION", 
			joinColumns = @JoinColumn(name = "CODE", referencedColumnName = "CODE"), 
			inverseJoinColumns = @JoinColumn(name = "LOCATION_ID", referencedColumnName = "LOCATION_ID"))
	private List<Location> locations;

	@ManyToMany
	@JoinTable(
			name = "FRUIT_PRICE", 
			joinColumns = @JoinColumn(name = "CODE", referencedColumnName = "CODE"), 
			inverseJoinColumns = @JoinColumn(name = "PRICE_ID", referencedColumnName = "PRICE_ID"))
	private List<Price> prices;

	// 요청 받을 때 사용할 User Entity의 DTO
	@Setter
	@Getter
	@Builder
	@ToString
	public static class Request {

		@NotNull
		private int code;

		@NotBlank(message = "itemName는 공백('', ' ')이나 Null 지정 불가")
		private String itemName;

		@NotBlank(message = "kindName는 공백('', ' ')이나 Null 지정 불가")
		private String kindName;

		@NotBlank(message = "image는 공백('', ' ')이나 Null 지정 불가")
		private String image;

		@NotNull
		private Date harvestStart;

		@NotNull
		private Date harvestEnd;

		@NotBlank(message = "etcDetails는 공백('', ' ')이나 Null 지정 불가")
		private String etcDetails;

		@NotNull
		private int brix;
		
		private Location location;
		private Price price;

		public static Fruit toEntity(final Fruit.Request request) {
			return Fruit.builder()
					.code(request.getCode())
					.itemName(request.getItemName())
					.kindName(request.getKindName())
					.image(request.getImage())
					.harvestStart(request.getHarvestStart())
					.harvestEnd(request.getHarvestEnd())
					.etcDetails(request.getEtcDetails())
					.brix(request.getBrix())
					.locations(new ArrayList<Location>(Arrays.asList(request.getLocation())))
					.prices(new ArrayList<Price>(Arrays.asList(request.getPrice())))
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
		
		private int code;
		private String itemName;
		private String kindName;
		private String image;
		private Date harvestStart;
		private Date harvestEnd;
		private String etcDetails;
		private int brix;
		
		private List<Location.Response> locations;
		private List<Price.Response> prices;

		public static Fruit.Response toResponse(final Fruit fruit) {
			return Fruit.Response.builder()
					.code(fruit.getCode())
					.itemName(fruit.getItemName())
					.kindName(fruit.getKindName())
					.image(fruit.getImage())
					.harvestStart(fruit.getHarvestStart())
					.harvestEnd(fruit.getHarvestEnd())
					.etcDetails(fruit.getEtcDetails())
					.brix(fruit.getBrix())
					.locations(Location.Response.toResponseList(fruit.getLocations()))
					.prices(Price.Response.toResponseList(fruit.getPrices()))
					.build();
		}

		public static List<Fruit.Response> toResponseList(final List<Fruit> fruits) {
			List<Fruit.Response> resList = new ArrayList<>();
			for (Fruit fruit : fruits) {
				resList.add(Fruit.Response.toResponse(fruit));
			}
			return resList;
		}
	}

}
