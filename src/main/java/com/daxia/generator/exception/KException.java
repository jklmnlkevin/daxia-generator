package com.daxia.generator.exception;
/**
 * 所有KSoft系列软件的异常体系的顶层父结点.
 * 
 * @date 2013-2-10
 */
public class KException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    
    public KException() {
    }
    
    public KException(String message) {
        super(message);
    }
    
    public KException(String message, Throwable t) {
        super(message, t);
    }
    
    public KException(Throwable t) {
        super(t);
    }
    
    public KException(Throwable t, Throwable t2) {
        super(t.getMessage(), t2);
    }
    

    

}
