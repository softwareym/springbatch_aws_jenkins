package ym.batch.job.common.status;

public enum TreateStts {
    COMPLETE("CP"),
    WAIT("WT");

    private String treateSttsCode;

    TreateStts(String treateSttsCode){
        this.treateSttsCode = treateSttsCode;
    }

    public String getTreateSttsCode(){return treateSttsCode;}
}
