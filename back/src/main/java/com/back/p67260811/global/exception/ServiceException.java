package com.back.p67260811.global.exception;

import com.back.p67260811.global.dto.RsData;

public class ServiceException extends RuntimeException {
    private final RsData rsData;
    public ServiceException(String resultCode, String message) {
        super(message);
        this.rsData = new RsData(resultCode, message);
    }

    public String getResultCode() {
        return rsData.getResultCode();
    }

    public String getMsg() {
        return rsData.getMsg();
    }
}
