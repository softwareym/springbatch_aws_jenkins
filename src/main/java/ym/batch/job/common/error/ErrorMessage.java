package ym.batch.job.common.error;

public enum ErrorMessage {
    //INVALID_PARAMETER("파라미터(%s) 값이(%s) 올바르지 않습니다.");
    INVALID_WRONG_SERVICEKEY("서비스키가 올바르지 않습니다."),
    INVALID_WRONG_RESPONSE("허용 트래픽을 초과하였습니다."),
    INVALID_WRONG_ETC("해당 오픈 API가 없거나 만료되었습니다. 상태를 확인하세요");

    private String errorMessage;

    ErrorMessage(String errorMessage){
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage(){return errorMessage;}

}
