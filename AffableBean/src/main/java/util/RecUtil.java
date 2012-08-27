package util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpParams;

import entity.Product;

/**
 * Utility dealing with recommendation stuff
 */
public class RecUtil {

    private static String url,apiKey,tenantid;

    static{
        //Load properties
        InputStream is = RecUtil.class.getClassLoader().getResourceAsStream("rec.properties");
        if(is == null){
            throw new RuntimeException("rec.properties not found");
        }
        Properties properties = new Properties();
        try {
            properties.load(is);
            url = properties.getProperty("url");
            apiKey = properties.getProperty("apiKey");
            tenantid = properties.getProperty("tenantid");
            if(url == null){
                throw new RuntimeException("url is null");
            }

            if(apiKey == null){
                throw new RuntimeException("apikey is null");
            }

            if(tenantid == null){
                throw new RuntimeException("tenantid is null");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    };

    public String getUrl() {
        return url;
    }

    public String getApiKey() {
        return apiKey;
    }

    public String getTenantid() {
        return tenantid;
    }
     
    public void postBuy(Product product, String userID, String sessionID) {
        // Create a new HttpClient and Post Header
        HttpClient httpclient = new DefaultHttpClient();
        HttpGet httpget = new HttpGet(url);
        HttpParams params = httpget.getParams();

        try {
            params.setParameter("tenantid", tenantid);
            params.setParameter("itemid", product.getId() + "");
            params.setParameter("itemdescription", product.getDescription());
            params.setParameter("itemurl", "/some.url");
            params.setParameter("apiKey", apiKey);
            params.setParameter("userid", userID);
            params.setParameter("sessionid", sessionID);
            params.setParameter("itemtype", "ITEM");
            
            // Execute HTTP get Request
            HttpResponse response = httpclient.execute(httpget);
            int statusCode = response.getStatusLine().getStatusCode();
            if(statusCode != 200){
                throw new RuntimeException("Status Code=" + statusCode);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    } 
}