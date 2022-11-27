package dev.pb.pb_backend.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import dev.pb.pb_backend.projection.FruitItemImageProjection;
import dev.pb.pb_backend.projection.ItemProjection;
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
@ToString(exclude= {"locations"})
public class Fruit {

	@Id
	@Column(name = "FRUIT_CODE")
	private int itemCode;

	@Column(name = "ITEM_NAME", nullable = false)
	private String itemName;
	
	@Column(nullable = false)
	private String unit;

	@Column(name = "ITEM_IMAGE")
	private String itemImage;

	@Column(name = "HARVEST_START")
	@Temporal(TemporalType.DATE)
	private Date harvestStart;

	@Column(name = "HARVEST_END")
	@Temporal(TemporalType.DATE)
	private Date harvestEnd;

	@Column(name = "ETC_DETAILS", columnDefinition = "TEXT")
	private String etcDetails;

	private Integer brix;

	@ManyToMany
	@JoinTable(
			name = "FRUIT_LOCATION", 
			joinColumns = @JoinColumn(name = "FRUIT_CODE", referencedColumnName = "FRUIT_CODE"), 
			inverseJoinColumns = @JoinColumn(name = "LOCATION_ID", referencedColumnName = "LOCATION_ID"))
	private List<Location> locations;

	@OneToMany(mappedBy = "fruit")
	private List<Price> prices;

	// 요청 받을 때 사용할 User Entity의 DTO
	@Setter
	@Getter
	@Builder
	@ToString
	public static class Request {

		@NotNull
		private int itemCode;

		@NotBlank(message = "itemName는 공백('', ' ')이나 Null 지정 불가")
		private String itemName;

		private String unit;
		
		private String itemImage;

//		@NotNull
		private Date harvestStart;

//		@NotNull
		private Date harvestEnd;

//		@NotBlank(message = "etcDetails는 공백('', ' ')이나 Null 지정 불가")
		private String etcDetails;

		private Integer brix;
		
		private List<Location> locations;
		private List<Price> prices;

		public static Fruit toEntity(final Fruit.Request request) {
			return Fruit.builder()
					.itemCode(request.getItemCode())
					.itemName(request.getItemName())
					.unit(request.getUnit())
					.itemImage(request.getItemImage())
					.harvestStart(request.getHarvestStart())
					.harvestEnd(request.getHarvestEnd())
					.etcDetails(request.getEtcDetails())
					.brix(request.getBrix())
					.locations(request.getLocations())
					.prices(request.getPrices())
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
		
		private int itemCode;
		private String itemName;
		private String unit;
		private String itemImage;
		private Date harvestStart;
		private Date harvestEnd;
		private String etcDetails;
		private Integer brix;
		
		private List<Location> locations;
		private List<Price> prices;

		public static Fruit.Response toResponse(final ItemProjection item, final List<Location> locations, final List<Price> prices) {
			return Fruit.Response.builder()
						.itemCode(item.getItemCode())
						.itemName(item.getItemName())
						.itemImage(item.getItemImage())
						.harvestStart(item.getHarvestStart())
						.harvestEnd(item.getHarvestEnd())
						.etcDetails(item.getEtcDetails())
						.locations(locations)
						.prices(prices).build();
		}

		public static List<Object> toResponseList(final Set<ItemProjection> fruits, final Map<String, List<Location>> locationsForFruit, final Map<String, List<Price>> pricesForFruit) {
			List<Object> resList = new ArrayList<Object>();
			for (ItemProjection fruit : fruits) {
				resList.add(toResponse(fruit, locationsForFruit.get(fruit.getItemName()), pricesForFruit.get(fruit.getItemName())));
			}
			return resList;
		}
		
//		public static Fruit.Response toLocationResponse(final Fruit fruit, int locationId) {
//			return Fruit.Response.builder()
//					.itemCode(fruit.getItemCode())
//					.itemName(fruit.getItemName())
//					.unit(fruit.getUnit())
//					.itemImage(fruit.getItemImage())
//					.harvestStart(fruit.getHarvestStart())
//					.harvestEnd(fruit.getHarvestEnd())
//					.etcDetails(fruit.getEtcDetails())
//					.brix(fruit.getBrix())
//					.prices(Price.Response.toFruitLocationResponseList(fruit.getPrices(), locationId))
//					.build();
//		}
		
//		public static List<Fruit.Response> toLocationResponseList(final List<Fruit> fruits, int locationId) {
//			List<Fruit.Response> resList = new ArrayList<>();
//			for (Fruit fruit : fruits) {
//				resList.add(Fruit.Response.toLocationResponse(fruit, locationId));
//			}
//			return resList;
//		}
		
		public static Fruit.Response toPriceResponse(final Fruit fruit) {
			if (fruit == null) {
				return null;
			} else {
				return Fruit.Response.builder()
						.itemCode(fruit.getItemCode())
						.itemName(fruit.getItemName())
						.unit(fruit.getUnit())
						.itemImage(fruit.getItemImage())
						.harvestStart(fruit.getHarvestStart())
						.harvestEnd(fruit.getHarvestEnd())
						.etcDetails(fruit.getEtcDetails())
						.brix(fruit.getBrix())
						.build();
			}
		}
		
		public static List<Fruit.Response> toPriceResponseList(final List<Fruit> fruits) {
			List<Fruit.Response> resList = new ArrayList<>();
			for (Fruit fruit : fruits) {
				resList.add(Fruit.Response.toPriceResponse(fruit));
			}
			return resList;
		}
		
		
//		public static Fruit.Response toItemNameAndLocalEngNameResponse(final Fruit fruit, String localEngName) {
//			return Fruit.Response.builder()
//					.itemCode(fruit.getItemCode())
//					.itemName(fruit.getItemName())
//					.unit(fruit.getUnit())
//					.itemImage(fruit.getItemImage())
//					.harvestStart(fruit.getHarvestStart())
//					.harvestEnd(fruit.getHarvestEnd())
//					.etcDetails(fruit.getEtcDetails())
//					.brix(fruit.getBrix())
//					.locations(Location.Response.toFruitItemNameAndLocalEngNameResponseList(fruit.getLocations(), fruit.getItemCode(), localEngName))
////					.locations(Location.Response.toResponseList(fruit.getLocations()))
////					.prices(Price.Response.toResponseList(fruit.getPrices()))
//					.build();
//		}
		
//		public static List<Fruit.Response> toItemNameAndLocalEngNameResponseList(final List<Fruit> fruits, String localEngName) {
//			List<Fruit.Response> resList = new ArrayList<>();
//			for (Fruit fruit : fruits) {
//				resList.add(Fruit.Response.toItemNameAndLocalEngNameResponse(fruit, localEngName));
//			}
//			return resList;
//		}
		
	}
	
	// FruitItemImageProjection 을 이용한 Response DTO
	@Setter
	@Getter
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class ResponseItemImage {

		private String itemName;
		private String itemImage;

		public static Fruit.ResponseItemImage toResponse(final FruitItemImageProjection fruit) {
			return Fruit.ResponseItemImage.builder()
					.itemName(fruit.getItemName())
					.itemImage(fruit.getItemImage())
					.build();
		}

		public static List<Fruit.ResponseItemImage> toResponseList(final List<FruitItemImageProjection> fruits) {
			List<Fruit.ResponseItemImage> resList = new ArrayList<>();
			for (FruitItemImageProjection fruit : fruits) {
				resList.add(Fruit.ResponseItemImage.toResponse(fruit));
			}
			return resList;
		}
	}

}
