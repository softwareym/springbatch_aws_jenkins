package ym.batch.job.common.status;

public enum CallDiv {
    AIRDATA("AD");

    private String statusCode;

    CallDiv(String statusCode){
        this.statusCode = statusCode;
    }

    public String getStatusCode(){return statusCode;}
}
