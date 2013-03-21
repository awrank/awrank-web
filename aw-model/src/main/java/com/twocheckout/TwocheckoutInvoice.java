package com.twocheckout;

import com.google.gson.Gson;
import com.twocheckout.model.Sale;

import java.util.HashMap;


public class TwocheckoutInvoice extends TwocheckoutApi {

	public Sale sale;

	public static Sale retrieve(String invoice_id, String apiusername, String apipassword) throws Exception {
		String urlSuffix = "sales/detail_sale";
		HashMap<String, String> args = new HashMap<String, String>();
		args.put("invoice_id", invoice_id);
		String response = TwocheckoutApi.get(urlSuffix, args, apiusername, apipassword);
		TwocheckoutSale resultObj = new Gson().fromJson(response, TwocheckoutSale.class);
		response = new Gson().toJson(resultObj.sale);
		Sale responseObj = new Gson().fromJson(response, Sale.class);
		return responseObj;
	}
}
