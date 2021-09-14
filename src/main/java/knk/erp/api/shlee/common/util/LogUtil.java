package knk.erp.api.shlee.common.util;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
@NoArgsConstructor
public class LogUtil {

    private static LogUtil logUtil = null;

    public static LogUtil getInstance(){
        if (logUtil == null) {
            logUtil = new LogUtil();
        }

        return logUtil;
    }

    public String getRequestBody(ContentCachingRequestWrapper request) {
        ContentCachingRequestWrapper wrapper = WebUtils.getNativeRequest(request, ContentCachingRequestWrapper.class);
        if (wrapper != null) {
            byte[] buf = wrapper.getContentAsByteArray();
            if (buf.length > 0) {
                try {
                    return new String(buf, 0, buf.length, wrapper.getCharacterEncoding());
                } catch (UnsupportedEncodingException e) {
                    return " -empty- ";
                }
            }
        }
        return " -empty- ";
    }

    public String getResponseBody(final HttpServletResponse response) throws IOException {
        String payload = null;
        ContentCachingResponseWrapper wrapper =
                WebUtils.getNativeResponse(response, ContentCachingResponseWrapper.class);
        if (wrapper != null) {
            byte[] buf = wrapper.getContentAsByteArray();
            if (buf.length > 0) {
                payload = new String(buf, 0, buf.length, wrapper.getCharacterEncoding());
                wrapper.copyBodyToResponse();
            }
        }
        return null == payload ? " - " : payload;
    }

    public Map<Object, Object> getHeaders(HttpServletRequest request) {
        Map<Object, Object> headerMap = new HashMap<>();

        Enumeration<String> headerArray = request.getHeaderNames();
        while (headerArray.hasMoreElements()) {
            String headerName = headerArray.nextElement();
            headerMap.put(headerName, request.getHeader(headerName));
        }
        return headerMap;
    }

    public String getToken(HttpServletRequest request, String elementName) {

        Enumeration<String> headerArray = request.getHeaderNames();
        while (headerArray.hasMoreElements()) {
            String headerName = headerArray.nextElement();
            if(headerName.equals(elementName)) return request.getHeader(headerName);
        }
        return " -empty- ";
    }

}
