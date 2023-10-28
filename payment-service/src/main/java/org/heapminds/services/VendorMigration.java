package org.heapminds.services;

import org.json.JSONObject; // Import the JSONObject class
import org.springframework.stereotype.Service;

@Service
public class VendorMigration {

    public void registerVendor() {
        // Create a new JSONObject for the account request
        JSONObject accountRequest = new JSONObject();

        // Populate the JSON object with data
        accountRequest.put("email", "gauriagain.kumar@example.org");
        accountRequest.put("phone", "9000090000");
        accountRequest.put("legal_business_name", "Acme Corp");
        accountRequest.put("business_type", "partnership");
        accountRequest.put("customer_facing_business_name", "Example");

        // Create a profile JSON object
        JSONObject profile = new JSONObject();
        profile.put("category", "healthcare");
        profile.put("subcategory", "clinic");
        profile.put("description", "Healthcare E-commerce platform");

        // Create an operation JSON object
        JSONObject operation = new JSONObject();
        operation.put("street1", "507, Koramangala 6th block");
        operation.put("street2", "507, Koramangala");
        operation.put("city", "Bengaluru");
        operation.put("state", "Karnataka");
        operation.put("postal_code", "560047");
        operation.put("country", "IN");

        // Create a registered JSON object
        JSONObject registered = new JSONObject();
        registered.put("street1", "507, Koramangala 1th block");
        registered.put("street2", "MG Road");
        registered.put("city", "Bengaluru");
        registered.put("state", "Karnataka");
        registered.put("postal_code", "560034");
        registered.put("country", "IN");

        // Create an addresses JSON object and add operation and registered addresses
        JSONObject addresses = new JSONObject();
        addresses.put("operation", operation);
        addresses.put("registered", registered);

        // Add addresses and business model to the profile
        profile.put("addresses", addresses);
        profile.put("business_model", "Online Clothing ( men, women, ethnic, modern ) fashion and lifestyle, accessories, t-shirt, shirt, track pant, shoes.");

        // Create a legalInfo JSON object
        JSONObject legalInfo = new JSONObject();
        legalInfo.put("pan", "AAACL1234C");
        legalInfo.put("gst", "18AABCU9603R1ZM");

        // Add profile and legalInfo to the account request
        accountRequest.put("profile", profile);
        accountRequest.put("legal_info", legalInfo);

        // Create a brand JSON object
        JSONObject brand = new JSONObject();
        brand.put("color", "FFFFFF");

        // Add brand to the account request
        accountRequest.put("brand", brand);

        // Create a notes JSON object
        JSONObject notes = new JSONObject();
        notes.put("internal_ref_id", "123123");

        // Add notes to the account request
        accountRequest.put("notes", notes);
        accountRequest.put("contact_name", "Gaurav Kumar");

        // Create a contactInfo JSON object
        JSONObject contactInfo = new JSONObject();
        
        // Create chargeback, refund, and support JSON objects
        JSONObject chargeback = new JSONObject();
        chargeback.put("email", "cb@example.org");

        JSONObject refund = new JSONObject();
        refund.put("email", "cb@example.org");

        JSONObject support = new JSONObject();
        support.put("email", "support@example.org");
        support.put("phone", "9999999998");
        support.put("policy_url", "https://www.google.com");

        // Add chargeback, refund, and support to contactInfo
        contactInfo.put("chargeback", chargeback);
        contactInfo.put("refund", refund);
        contactInfo.put("support", support);

        // Add contactInfo to the account request
        accountRequest.put("contact_info", contactInfo);

        // Create an apps JSON object
        JSONObject apps = new JSONObject();

        // Create a list of URLs
        java.util.List<String> url = new java.util.ArrayList<String>();
        url.add("https://www.example.org");

        // Add URLs to the apps object
        apps.put("websites", url);

        // Create Android and iOS JSON objects
        java.util.List<JSONObject> android = new java.util.ArrayList<JSONObject>();
        java.util.List<JSONObject> ios = new java.util.ArrayList<JSONObject>();

        JSONObject android_details = new JSONObject();
        android_details.put("url", "playstore.example.org");
        android_details.put("name", "Example");

        android.add(android_details);

        JSONObject ios_details = new JSONObject();
        ios_details.put("url", "appstore.example.org");
        ios_details.put("name", "Example");

        ios.add(ios_details);

        // Add Android and iOS objects to the apps object
        apps.put("android", android);
        apps.put("ios", ios);

        // Add the apps object to the account request
        accountRequest.put("apps", apps);

        // Print the final JSON object
        System.out.println(accountRequest.toString());
    }
}
