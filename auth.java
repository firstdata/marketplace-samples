// Required import statements
import org.apache.commons.codec.digest.HmacUtils;
import org.apache.http.HttpResponse;
import org.apache.http.Header;
import org.apache.http.message.BasicHeader;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.util.EntityUtils;
import org.apache.http.entity.StringEntity;
import org.json.JSONObject;
import org.json.JSONArray;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

// HMAC
public static final String username = "xxxxxx-xxxx-xxxxxxxxxxxxx"; // Replace with API Key from email
public static final String secret = "xxxxxx-xxxx-xxxxxxxxxxxxx"; // Replace with API Secret from email
public static final String apiUrl = "xxxxxxxxxxxxxxxxxxxxxxxxx"; // Replace with URL from email
public String formattedDate;
public String hmacToken;
public String authHeader;

// doGet method
private HttpResponse doGet(String uri) throws Exception {
  formattedDate = getCurrentGMTDate(); // see below for method implemetation
  hmacToken = generateHMACToken(formattedDate, secret); // see below for method implemetation
  authHeader = "hmac username=\""+username+"\", algorithm=\"hmac-sha1\", headers=\"date\", signature=\""+hmacToken+"\"";
  Header oauthHeader = new BasicHeader("authorization", authHeader );
  Header dateHeader = new BasicHeader("date", formattedDate);
  String ServiceURL = apiUrl+uri;

  HttpClient httpClientLead = HttpClientBuilder.create().build();
  HttpGet httpGet = new HttpGet(ServiceURL);
  httpGet.addHeader(oauthHeader);
  httpGet.addHeader(dateHeader);
  HttpResponse response = httpClientLead.execute(httpGet);
  return response;
}

// doPost method
private HttpResponse doPost(String uri, StringEntity payload) throws Exception {
  formattedDate = getCurrentGMTDate(); // see below for method implemetation
  hmacToken = generateHMACToken(formattedDate, secret); // see below for method implemetation
  authHeader = "hmac username=\""+username+"\", algorithm=\"hmac-sha1\", headers=\"date\", signature=\""+hmacToken+"\"";
  Header oauthHeader = new BasicHeader("authorization", authHeader );
  Header dateHeader = new BasicHeader("date", formattedDate);
  String ServiceURL = apiUrl+uri;

  HttpClient httpClientLead = HttpClientBuilder.create().build();
  HttpPost httpPost = new HttpPost(ServiceURL);
  httpPost.addHeader(oauthHeader);
  httpPost.addHeader(dateHeader);
  httpPost.addHeader("Content-Type","application/json");
  httpPost.setEntity(payload);
  HttpResponse response = httpClientLead.execute(httpPost);
  return response;
}

// method to be used in doGet and doPost methods above
public String getCurrentGMTDate(){
  Date curDate = new Date();
  SimpleDateFormat format = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss z");
  format.setTimeZone(TimeZone.getTimeZone("GMT"));
  String formattedDate = format.format(curDate);
  return formattedDate;
}

// method to be used in doGet and doPost methods above
public String generateHMACToken (String formattedDate, String secret){
  String authorizeString = org.apache.commons.codec.binary.Base64.encodeBase64String(HmacUtils.hmacSha1(secret, "date: "+formattedDate));
  return authorizeString;
}

// Don't forget to add your HMAC Authorization Headers
HttpResponse response = null;
try {
  response = doGet("/marketplace/v1/contracts/31/agreement");
  int statusCode = response.getStatusLine().getStatusCode();
  String response_string = EntityUtils.toString(response.getEntity());
  JSONObject json = new JSONObject(response_string);
  System.out.println("json:" + json.toString());
} catch (Exception e) {
  e.printStackTrace();
}
