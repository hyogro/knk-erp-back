package knk.erp.api.shlee.service;

import knk.erp.api.shlee.util.RestUtil;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ScheduleServiceImpl implements ScheduleService{
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final String schedule_api_url = "http://api-schedule:8200";
    private final RestUtil restUtil = new RestUtil(schedule_api_url);

    public String callSchedule(){
        return restUtil.restCall("/isWork", new JSONObject());
    }

    public String createSchedule(JSONObject request){
        return restUtil.restCall("/S0001",request);

    }
}


