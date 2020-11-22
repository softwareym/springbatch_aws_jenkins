package ym.batch.job.common.exception;

import org.springframework.http.HttpStatus;

public class ApiException extends AbstractException{
    String exceptionCode;

    Object exceptionInfo;

    boolean logable = false;

    public ApiException(Throwable cause , HttpStatus httpStatus){
        super(cause);
        setHttpStatus(httpStatus);
    }

    public ApiException(String message , HttpStatus httpStatus, String exceptionCode, Object exceptionInfo){
        super(message);
        setHttpStatus(httpStatus);
        setExceptionCode(exceptionCode);
        setExceptionInfo(exceptionInfo);
    }

    public ApiException(String message, String exceptionCode, Object exceptionInfo){
        this(message, HttpStatus.SERVICE_UNAVAILABLE,exceptionCode,exceptionInfo);
    }

    public ApiException(String message, HttpStatus httpStatus){
        this(message, httpStatus,null,null);
    }

    public ApiException(String message, Object exceptionInfo){
        this(message, HttpStatus.SERVICE_UNAVAILABLE,null,exceptionInfo);
    }

    public ApiException(Object exceptionInfo){
        setHttpStatus(HttpStatus.SERVICE_UNAVAILABLE);
        setExceptionInfo(exceptionInfo);
    }

    private HttpStatus httpStatus;

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public String getExceptionCode() {
        return exceptionCode;
    }

    public void setExceptionCode(String exceptionCode) {
        this.exceptionCode = exceptionCode;
    }

    public Object getExceptionInfo() {
        return exceptionInfo;
    }

    public void setExceptionInfo(Object exceptionInfo) {
        this.exceptionInfo = exceptionInfo;
    }

    public boolean isLogable() {
        return logable;
    }

    public void setLogable(boolean logable) {
        this.logable = logable;
    }
}
