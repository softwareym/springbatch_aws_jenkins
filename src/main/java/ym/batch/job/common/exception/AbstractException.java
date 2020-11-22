package ym.batch.job.common.exception;

public abstract class AbstractException extends RuntimeException {

    public AbstractException(String message){
        super(message);
    }
    public AbstractException(String message, Throwable cause){
        super(message,cause);
    }
    public AbstractException(Throwable cause){
        super(cause);
    }
    public AbstractException(){
        super();
    }

}
