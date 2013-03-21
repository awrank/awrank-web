package com.twocheckout;

import com.google.gson.Gson;
import com.twocheckout.model.Coupon;
import com.twocheckout.model.CouponList;

import java.util.HashMap;


public class TwocheckoutCoupon extends TwocheckoutApi {
	public Coupon coupon;

	public static Coupon retrieve(String coupon_code, String apiusername, String apipassword) throws Exception {
		HashMap<String, String> args = new HashMap<String, String>();
		args.put("coupon_code", coupon_code);
		String urlSuffix = "products/detail_coupon";
		String response = TwocheckoutApi.get(urlSuffix, args, apiusername, apipassword);
		TwocheckoutCoupon responseObj = new Gson().fromJson(response, TwocheckoutCoupon.class);
		return responseObj.coupon;
	}

	public static CouponList list(HashMap<String, String> args, String apiusername, String apipassword) throws Exception {
		String urlSuffix = "products/list_coupons";
		String response = TwocheckoutApi.get(urlSuffix, args, apiusername, apipassword);
		CouponList responseObj = new Gson().fromJson(response, CouponList.class);
		return responseObj;
	}

	public static TwocheckoutResponse create(HashMap<String, String> args, String apiusername, String apipassword) throws Exception {
		String urlSuffix = "products/create_coupon";
		String response = TwocheckoutApi.post(urlSuffix, args, apiusername, apipassword);
		TwocheckoutResponse responseObj = new Gson().fromJson(response, TwocheckoutResponse.class);
		return responseObj;
	}
}