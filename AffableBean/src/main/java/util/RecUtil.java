package util;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.Properties;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

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
         

        try {
            StringBuilder sb = new StringBuilder("?");
            sb.append("tenantid").append("=").append(encode(tenantid)).append("&");
            sb.append("itemid").append("=").append(encode(product.getId() + "")).append("&");
            sb.append("itemdescription").append("=").append(encode(product.getDescription())).append("&");
            sb.append("itemurl").append("=").append(encode("/some.url")).append("&");
            sb.append("apikey").append("=").append( encode(apiKey)).append("&");
            sb.append("userid").append("=").append( encode(userID)).append("&");
            sb.append("sessionid").append("=").append( encode(sessionID)).append("&");
            sb.append("itemtype").append("=").append( "ITEM");
            
            String safeUrl = url + sb.toString();

            HttpGet httpget = new HttpGet(safeUrl);
            
            
            System.out.println("Making calls:"+httpget.getURI());

            // Execute HTTP get Request
            HttpResponse response = httpclient.execute(httpget);
            int statusCode = response.getStatusLine().getStatusCode();
            if(statusCode != 200){
                throw new RuntimeException("Status Code=" + statusCode);
            }

            HttpEntity entity = response.getEntity();
            if (entity != null) {
                System.out.println(EntityUtils.toString(entity));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    } 
    
    @SuppressWarnings("deprecation")
    private String encode(String str){
        return URLEncoder.encode(str);
    }
}