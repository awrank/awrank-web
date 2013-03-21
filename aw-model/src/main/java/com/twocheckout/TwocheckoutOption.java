package com.twocheckout;

import com.google.gson.Gson;
import com.twocheckout.model.ProductOption;
import com.twocheckout.model.ProductOptionList;

import java.util.HashMap;


public class TwocheckoutOption extends TwocheckoutApi {
	public ProductOption[] option;

	public static ProductOption retrieve(String option_id, String apiusername, String apipassword) throws Exception {
		HashMap<String, String> args = new HashMap<String, String>();
		args.put("option_id", option_id);
		String urlSuffix = "products/detail_option";
		String response = TwocheckoutApi.get(urlSuffix, args, apiusername, apipassword);
		TwocheckoutOption responseObj = new Gson().fromJson(response, TwocheckoutOption.class);
		return responseObj.option[0];
	}

	public static ProductOptionList list(HashMap<String, String> args, String apiusername, String apipassword) throws Exception {
		String urlSuffix = "products/list_options";
		String response = TwocheckoutApi.get(urlSuffix, args, apiusername, apipassword);
		ProductOptionList responseObj = new Gson().fromJson(response, ProductOptionList.class);
		return responseObj;
	}

	public static TwocheckoutResponse create(HashMap<String, String> args, String apiusername, String apipassword) throws Exception {
		String urlSuffix = "products/create_option";
		String response = TwocheckoutApi.post(urlSuffix, args, apiusername, apipassword);
		TwocheckoutResponse responseObj = new Gson().fromJson(response, TwocheckoutResponse.class);
		return responseObj;
	}

}