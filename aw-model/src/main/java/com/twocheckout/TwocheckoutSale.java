package com.twocheckout;

import com.google.gson.Gson;
import com.twocheckout.model.Sale;
import com.twocheckout.model.SaleList;

import java.util.HashMap;


public class TwocheckoutSale extends TwocheckoutApi {

	public Sale sale;

	public static Sale retrieve(String sale_id, String apiusername, String apipassword) throws Exception {
		String urlSuffix = "sales/detail_sale";
		HashMap<String, String> args = new HashMap<String, String>();
		args.put("sale_id", sale_id);
		String response = TwocheckoutApi.get(urlSuffix, args, apiusername, apipassword);
		TwocheckoutSale resultObj = new Gson().fromJson(response, TwocheckoutSale.class);
		response = new Gson().toJson(resultObj.sale);
		Sale responseObj = new Gson().fromJson(response, Sale.class);
		return responseObj;
	}

	public static SaleList list(HashMap<String, String> args, String apiusername, String apipassword) throws Exception {
		String urlSuffix = "sales/list_sales";
		String response = TwocheckoutApi.get(urlSuffix, args, apiusername, apipassword);
		SaleList responseObj = new Gson().fromJson(response, SaleList.class);
		return responseObj;
	}
}
