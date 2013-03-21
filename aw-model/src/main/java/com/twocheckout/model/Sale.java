package com.twocheckout.model;

import com.google.gson.Gson;
import com.twocheckout.TwocheckoutApi;
import com.twocheckout.TwocheckoutResponse;
import com.twocheckout.TwocheckoutSale;

import java.util.HashMap;

public class Sale {
	private long sale_id;
	private String ip_address;
	private String ip_country;
	private String recurring_decline;
	private Invoice[] invoices;
	private Comments[] comments;

	public long getSaleId() {
		return sale_id;
	}

	public String getIpAddress() {
		return ip_address;
	}

	public String getIpCountry() {
		return ip_country;
	}

	public String getRecurringDecline() {
		return recurring_decline;
	}

	public Invoice[] getInvoices() {
		return invoices;
	}

	public Comments[] getComments() {
		return comments;
	}

	public TwocheckoutResponse refund(HashMap<String, String> args, String apiusername, String apipassword) throws Exception {
		args.put("sale_id", String.valueOf(sale_id));
		String urlSuffix = "sales/refund_invoice";
		String response = TwocheckoutApi.post(urlSuffix, args, apiusername, apipassword);
		TwocheckoutResponse responseObj = new Gson().fromJson(response, TwocheckoutResponse.class);
		return responseObj;
	}

	public TwocheckoutResponse comment(HashMap<String, String> args, String apiusername, String apipassword) throws Exception {
		args.put("sale_id", String.valueOf(sale_id));
		String urlSuffix = "sales/create_comment";
		String response = TwocheckoutApi.post(urlSuffix, args, apiusername, apipassword);
		TwocheckoutResponse responseObj = new Gson().fromJson(response, TwocheckoutResponse.class);
		return responseObj;
	}

	public TwocheckoutResponse reauth(String apiusername, String apipassword) throws Exception {
		HashMap<String, String> args = new HashMap<String, String>();
		args.put("sale_id", String.valueOf(sale_id));
		String urlSuffix = "sales/reauth";
		String response = TwocheckoutApi.post(urlSuffix, args, apiusername, apipassword);
		TwocheckoutResponse responseObj = new Gson().fromJson(response, TwocheckoutResponse.class);
		return responseObj;
	}

	public TwocheckoutResponse ship(HashMap<String, String> args, String apiusername, String apipassword) throws Exception {
		args.put("sale_id", String.valueOf(sale_id));
		String urlSuffix = "sales/mark_shipped";
		String response = TwocheckoutApi.post(urlSuffix, args, apiusername, apipassword);
		TwocheckoutResponse responseObj = new Gson().fromJson(response, TwocheckoutResponse.class);
		return responseObj;
	}

	public TwocheckoutResponse stop(String apiusername, String apipassword) throws Exception {
		String urlSuffix = "sales/stop_lineitem_recurring";
		Invoice invoice = invoices[invoices.length - 1];
		Lineitem[] lineitems = invoice.getLineitems();
		String stopped_lineitems = null;
		HashMap<String, String> params = new HashMap<String, String>();
		for (int i = 0; i < lineitems.length; i++) {
			if (lineitems[i].getBilling().getRecurringStatus() != null) {
				if (lineitems[i].getBilling().getRecurringStatus().equals("active")) {
					params.put("lineitem_id", String.valueOf(lineitems[i].getBilling().getLineitemId()));
					String response = TwocheckoutApi.post(urlSuffix, params, apiusername, apipassword);
					TwocheckoutResponse resultObj = new Gson().fromJson(response, TwocheckoutResponse.class);
					if (resultObj.getResponseCode().equals("OK")) {
						stopped_lineitems = stopped_lineitems + "," + String.valueOf(lineitems[i].getLineitemID());
					}
				}
			}
		}
		TwocheckoutResponse responseObj = new TwocheckoutResponse();
		if (stopped_lineitems == null) {
			responseObj.setResponseCode("OK");
			responseObj.setResponseMessage("No active recurring lineitems.");
		} else {
			stopped_lineitems = stopped_lineitems.substring(5);
			responseObj.setResponseCode("OK");
			responseObj.setResponseMessage(stopped_lineitems);
		}
		return responseObj;
	}

	public Sale refresh(String apiusername, String apipassword) throws Exception {
		HashMap<String, String> args = new HashMap<String, String>();
		args.put("sale_id", String.valueOf(sale_id));
		String urlSuffix = "sales/detail_sale";
		String response = TwocheckoutApi.get(urlSuffix, args, apiusername, apipassword);
		TwocheckoutSale responseObj = new Gson().fromJson(response, TwocheckoutSale.class);
		response = new Gson().toJson(responseObj.sale);
		Sale resultObj = new Gson().fromJson(response, Sale.class);
		return resultObj;
	}

}
