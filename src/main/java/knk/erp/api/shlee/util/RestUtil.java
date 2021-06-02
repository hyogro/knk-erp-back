package knk.erp.api.shlee.util;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.net.www.protocol.http.HttpURLConnection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class RestUtil {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final String url;

    public RestUtil(String url){
        this.url = url;
    }
    public String restCall(String requestUrl, JSONObject jsonObject){
        StringBuilder response = new StringBuilder();
        try {
            URL url = new URL(this.url + requestUrl);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("X-Auth-Token", "API_KEY");
            conn.setRequestProperty("X-Data-Type", "application/json");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream(), StandardCharsets.UTF_8);
            osw.write(jsonObject.toString());
            osw.flush();
            osw.close();

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
            if (conn.getResponseCode() != 200) {
                logger.info("Failed!: HTTP error code : " + conn.getResponseCode());
                logger.info("Failed!: HTTP error message : " + conn.getResponseMessage());
                throw new RuntimeException("Failed: HTTP error code : " + conn.getResponseCode() + "\tmessage : " + conn.getResponseMessage());
            } else {
                logger.info("발송 성공");
            }
            String line;
            while((line = br.readLine()) != null){
                logger.info(line);
                response.append(line);
            }
            br.close();
            conn.disconnect();
        } catch (IOException e) {
            logger.info("RestCall Fail : " + e.getMessage());
        }
        return response.toString();
    }
}
