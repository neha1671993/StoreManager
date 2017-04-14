package retailmanager.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import reatilmanager.util.JSONUtil;
import retailmanager.bean.ShopAddress;
import retailmanager.bean.ShopBean;
import retailmanager.store.RetailStoreMap;




/**
 * 
 * It Is main Controller where we can call our functions
 * @author Neha Singla
 * URL will be :http://localhost:8080/getShopDetails
 */
@Controller
public class RetailManagerController {

	/**
	 * Use to GET Shop Details which are set here Hardcoded
	 * @return
	 */
	
	@RequestMapping(value = "/getShopDetails")
	public ResponseEntity get() {
		ShopBean shopBean = new ShopBean();
		ShopAddress shopAddress = new ShopAddress();
		shopAddress.setShopNumber(1);
		shopAddress.setShopPostCode(136118);
		shopBean.setShopAddress(shopAddress);
		shopBean.setShopName("HCL Shop");
		shopBean.setShopLongitude(11.2);
		shopBean.setShopLatitude(1.2);
		return new ResponseEntity(shopBean, HttpStatus.OK);
	}

	
	/**
	 * Use to POST Shop Details which we want to Set
	 * http://localhost:8080/postShopDetails
	 * 
	 * Data Should be posted in this Format
{
  "shopAddress": {
    "shopNumber": 1,
    "shopPostCode": 136118,
    "shopPlace": null
  },
  "shopName": "HCL Shop",
  "shopLongitude": 11.2,
  "shopLatitude": 1.2
}
	 * @return
	 */
	
	@RequestMapping(value = "/postShopDetails", method = RequestMethod.POST)
	public ResponseEntity postShopDetails(
			@RequestBody ShopBean shopBean) {

		System.out.println("****Start of postShopDetails******");

		if (shopBean != null) {
			try {

				String latLong = JSONUtil.readLatLongFromAddress(shopBean
						.getShopAddress().getShopPlace());
				String[] latLongAry = latLong.split(":");
				shopBean.setShopLongitude(Double.parseDouble(latLongAry[0]));
				shopBean.setShopLatitude(Double.parseDouble(latLongAry[1]));

				List shops = RetailStoreMap.shopmap.get(shopBean
						.getShopAddress().getShopPlace());

				if (shops == null) {
					shops = new ArrayList();
				}


				/**
				 * 
				 * We need to check whether data was already present or not.
				 * For this we need to give REST response to user A should contain information
				 * about the version that was replaced. 
				 * 
				 */

				//As Shops are identified by their unique names

				String shopName=shopBean.getShopName();
				
				System.out.println("***&&&****"+RetailStoreMap.shopmap);

				if(RetailStoreMap.shopmap ==null||RetailStoreMap.shopmap.isEmpty()){
					shops.add(shopBean);
				}
				else
				{
					for (Map.Entry<String,List<ShopBean>> shopMap : RetailStoreMap.shopmap.entrySet()) {

						for (ShopBean shop : shopMap.getValue()) {

							System.out.println("shopName"+shopName+"shop.getShopName()"+shop.getShopName());
							if (shopName.equals(shop.getShopName())) {
								
								String response = "You Have Replaced the Shopname :"+shopName+" the previous data was:"+RetailStoreMap.shopmap;
								shops.add(shopBean);
								RetailStoreMap.shopmap.put(shopBean.getShopName(), shops);
								return new ResponseEntity(response, HttpStatus.OK);
							}
							else
							{
								shops.add(shopBean);		
							}

						}
					}
				}

				RetailStoreMap.shopmap.put(shopBean.getShopName(), shops);
			} catch (Exception e) {
				System.out.println("Unable To Set Data for"+shopBean.toString());
				return new ResponseEntity(shopBean, HttpStatus.EXPECTATION_FAILED);
			}
		}

		System.out.println("****End of postShopDetails*****"+RetailStoreMap.shopmap);

		return new ResponseEntity(shopBean, HttpStatus.OK);

	}
	
	
	/**
	 * 
	 * Use to GET Shop Details based on Particular Latitude and Longitude
	 * 
	 * http://localhost:8080/getShopList?Lng=ShopLatitude&Lat=ShopLongitude
	 * ShopLatitude, ShopLongitude:You want to search 
	 * 
	 * @return
	 */

	@RequestMapping(value = "/getShopList", method = RequestMethod.GET)
	public ResponseEntity<List> getShopList(
			@RequestParam("Lng") double lng, @RequestParam("Lat") double lat) {
		List shopList = new ArrayList();

		for (Map.Entry<String,List<ShopBean>> shopMap : RetailStoreMap.shopmap.entrySet()) {

			for (ShopBean shopBean : shopMap.getValue()) {
				if (lng == shopBean.getShopLongitude()
						&& lat == shopBean.getShopLatitude()) {
					shopList.add(shopBean);
				}
				else
				{
					System.out.println("No data found For these Geometric Coordinates");
				}

			}
		}

		return new ResponseEntity<List>(shopList, HttpStatus.OK);
	}
	
	/**
	 * 
	 * Use to GET All Shop Details 
	 * 
	 * http://localhost:8080/getShopList
	 * 
	 * @return
	 */

	@RequestMapping(value = "/getAllShopList", method = RequestMethod.GET)
	public ResponseEntity<List> getShopList( ) {
		List shopList = new ArrayList();

		for (Map.Entry<String,List<ShopBean>> shopMap : RetailStoreMap.shopmap.entrySet()) {

			for (ShopBean shopBean : shopMap.getValue()) {
					shopList.add(shopBean);
				}
				
		}

		return new ResponseEntity<List>(shopList, HttpStatus.OK);
	}
	
}
