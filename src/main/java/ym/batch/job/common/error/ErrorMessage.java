package ym.batch.job.common.error;

public enum ErrorMessage {
  //INVALID_PARAMETER("파라미터(%s) 값이(%s) 올바르지 않습니다.");
    INVALID_WRONG_RESPONSE("요청에 문제가 있습니다. 트래픽이나 URL을 확인하세요");

    private String errorMessage;

    ErrorMessage(String errorMessage){
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage(){return errorMessage;}
}
