package com.anchore.jenkins.plugins.anchore;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class AnchoreAPIClient {
    public static boolean imageAnalyzedBefore(String digest, String engineurl, HttpClientContext context, CloseableHttpClient client) {
        String url = engineurl + "/images/" + digest;
        HttpGet request = new HttpGet(url);
        request.addHeader("Content-Type", "application/json");

        try {
            CloseableHttpResponse response = client.execute(request, context);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == 200) {
                String responseBody = EntityUtils.toString(response.getEntity());
                JSONArray responseJson = JSONArray.fromObject(responseBody);
                String state = responseJson.getJSONObject(0).getString("analysis_status");

                if (state.equals("analyzed"))
                    return true;
            } else return false;
        } catch (IOException e) {
            return false;
        }

        return false;
    }
}
