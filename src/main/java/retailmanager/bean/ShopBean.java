package retailmanager.bean;

public class ShopBean {

	private ShopAddress shopAddress;
	private String shopName;
	private double shopLongitude;
	private double shopLatitude;
	
	public ShopAddress getShopAddress() {
		return shopAddress;
	}
	public void setShopAddress(ShopAddress shopAddress) {
		this.shopAddress = shopAddress;
	}
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public double getShopLongitude() {
		return shopLongitude;
	}
	public void setShopLongitude(double shopLongitude) {
		this.shopLongitude = shopLongitude;
	}
	public double getShopLatitude() {
		return shopLatitude;
	}
	public void setShopLatitude(double shopLatitude) {
		this.shopLatitude = shopLatitude;
	}
	
	@Override
	public String toString() {
		return "ShopBean [shopAddress=" + shopAddress + ", shopName=" + shopName + ", shopLongitude=" + shopLongitude
				+ ", shopLatitude=" + shopLatitude + "]";
	}
	
	
}
