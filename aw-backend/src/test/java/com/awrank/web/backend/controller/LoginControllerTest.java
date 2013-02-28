package com.awrank.web.backend.controller;

import junit.framework.TestCase;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import javax.validation.constraints.AssertTrue;

/**
 * Test cases for login controller methods or their inside peaces of code.
 *
 * @author Andrew Stoyaltsev
 */

public class LoginControllerTest extends TestCase {

    @Test
    public void testGoogleResponseJson() throws JSONException {
        String response = "{\n" +
                "  \"access_token\" : \"ya29.AHES6ZScILZeTm80ywLkr30kUAqWU71H3KBF7_mr7KRWUg\",\n" +
                "  \"token_type\" : \"Bearer\",\n" +
                "  \"expires_in\" : 3594,\n" +
                "  \"id_token\" : \"eyJhbGciOiJSUzI1NiIsImtpZCI6IjdlMDAxNzhiYzk0ODVkNWFiMzEzMTk5Y2VmNThkY2QwNmJhMzNjOGMifQ.eyJpc3MiOiJhY2NvdW50cy5nb29nbGUuY29tIiwidmVyaWZpZWRfZW1haWwiOiJ0cnVlIiwiZW1haWxfdmVyaWZpZWQiOiJ0cnVlIiwidG9rZW5faGFzaCI6Ii1ldVR6aXNrQ3FUNjhZSmpGX0tRelEiLCJhdF9oYXNoIjoiLWV1VHppc2tDcVQ2OFlKakZfS1F6USIsImlkIjoiMTE0NjYwNDc5MDY1MTk4NTI5NTE5Iiwic3ViIjoiMTE0NjYwNDc5MDY1MTk4NTI5NTE5IiwiY2lkIjoiNTY3NzEyNzk2MTU2LWxoOXJjNjFrazVxc2xsbmc1NHNrMzE0c3VpMHRsczA5LmFwcHMuZ29vZ2xldXNlcmNvbnRlbnQuY29tIiwiYXpwIjoiNTY3NzEyNzk2MTU2LWxoOXJjNjFrazVxc2xsbmc1NHNrMzE0c3VpMHRsczA5LmFwcHMuZ29vZ2xldXNlcmNvbnRlbnQuY29tIiwiYXVkIjoiNTY3NzEyNzk2MTU2LWxoOXJjNjFrazVxc2xsbmc1NHNrMzE0c3VpMHRsczA5LmFwcHMuZ29vZ2xldXNlcmNvbnRlbnQuY29tIiwiZW1haWwiOiJhNWFwcHRlc3RAZ21haWwuY29tIiwiaWF0IjoxMzYyMDU1NzcyLCJleHAiOjEzNjIwNTk2NzJ9.IpAvkGMSKfi-qHYzT2T1bfErK7BhVEttuMf8FwEI4qIIvHkgXcoB0jGFGhN4qatDw1xX4cdaYpJXIl84fdqWS3bsbAtTBom-UwB9YeegZLi60UvSzj3dbywtcK6sbWmRFAckY_HpOK-jQjJvH0dynUjAu9UalEq7qE2AhoISLTE\"\n" +
                "}";

        JSONObject jsonObject = new JSONObject(response);
        assertTrue(jsonObject.has("access_token"));
        assertTrue(jsonObject.has("token_type"));
        assertTrue(jsonObject.has("expires_in"));
        assertEquals("ya29.AHES6ZScILZeTm80ywLkr30kUAqWU71H3KBF7_mr7KRWUg", jsonObject.get("access_token"));
        assertEquals("Bearer", jsonObject.get("token_type"));
        assertEquals(3594, jsonObject.get("expires_in"));

        // First three params are necessary minimum in Google request-token response.
        // They, definetly, should be tested.
    }

}
