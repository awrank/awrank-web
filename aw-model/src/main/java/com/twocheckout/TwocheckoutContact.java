package com.twocheckout;

import com.google.gson.Gson;
import com.twocheckout.model.Contact;

import java.util.HashMap;


public class TwocheckoutContact extends TwocheckoutApi {

	public Contact vendor_contact_info;

	public static Contact retrieve(String apiusername, String apipassword) throws Exception {
		HashMap<String, String> args = new HashMap<String, String>();
		String urlSuffix = "acct/detail_contact_info";
		String response = TwocheckoutApi.get(urlSuffix, args, apiusername, apipassword);
		TwocheckoutContact resultObj = new Gson().fromJson(response, TwocheckoutContact.class);
		response = new Gson().toJson(resultObj.vendor_contact_info);
		Contact responseObj = new Gson().fromJson(response, Contact.class);
		return responseObj;
	}
}