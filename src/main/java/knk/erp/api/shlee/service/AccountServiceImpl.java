package knk.erp.api.shlee.service;

import knk.erp.api.shlee.util.RestUtil;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService{
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final String account_api_url = "http://api-account:8300";
    private final RestUtil restUtil = new RestUtil(account_api_url);

    public String accountHello(){
        return restUtil.restCall("/hello", new JSONObject());
    }

    public String login(JSONObject requestData){
        String restData = restUtil.restCall("/login", requestData);
        logger.info("responseData: {}", restData);
        return restData;
    }

}
