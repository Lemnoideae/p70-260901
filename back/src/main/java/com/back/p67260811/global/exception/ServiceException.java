package com.back.p67260811.global.exception;

import com.back.p67260811.global.dto.RsData;
import org.springframework.http.HttpStatusCode;

public class ServiceException extends RuntimeException {
    private final RsData rsData;
    public ServiceException(String resultCode, String msg) {
        super(msg);
        this.rsData = new RsData(resultCode, msg);
    }

    public String getResultCode() {
        return rsData.getResultCode();
    }

    public HttpStatusCode getStatusCode() {
        return HttpStatusCode.valueOf(rsData.getStatusCode());
    }

    public String getMsg() {
        return rsData.getMsg();
    }
}
