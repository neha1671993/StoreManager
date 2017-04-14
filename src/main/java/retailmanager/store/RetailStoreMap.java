package retailmanager.store;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import retailmanager.bean.ShopBean;


/**
 * Using TreeMap with key As Shopname SO to avoid Dupliactes Shop Name
 * @author Neha Singla
 *
 */
public class RetailStoreMap {
	public static Map<String,List<ShopBean>> shopmap =new TreeMap<String,List<ShopBean>>();
}
