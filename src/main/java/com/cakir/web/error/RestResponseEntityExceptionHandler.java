package com.cakir.web.error;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
	
	private static final Log logger = LogFactory.getLog(RestResponseEntityExceptionHandler.class);
	
	public RestResponseEntityExceptionHandler() {
		super();
		// TODO Auto-generated constructor stub
	}



	// 409
    @ExceptionHandler({ UserAlreadyExistException.class })
    public ModelAndView handleUserAlreadyExist(RuntimeException ex, HttpServletRequest request) {
    	
    	logger.error(ex.getMessage());
        ModelAndView mav = new ModelAndView();
        mav.addObject("exception", "UserAlreadyExists");
        mav.addObject("url", request.getRequestURL());
        mav.setViewName("exception/error_409");
        return mav;
    }
    
    @ExceptionHandler({UserAlreadyEnabledException.class})
    public ModelAndView handleUserAlreadyEnabled(RuntimeException ex) {
    	
    	logger.error(ex.getMessage());
    	ModelAndView mav = new ModelAndView();
    	mav.addObject("exception", "UserAlreadyEnabled");
    	mav.setViewName("exception/error_409");
    	return mav;
    	
    }
    
    @ExceptionHandler({UserNotFoundException.class})
    public ModelAndView handleUserNotFoundException(RuntimeException ex) {
    	logger.error(ex.getMessage());
    	ModelAndView mav = new ModelAndView();
    	mav.addObject("exception", "UserNotFoundException");
    	mav.setViewName("exception/error_404");
    	return mav;
    }
    
    @ExceptionHandler({UserNotEnabledException.class})
    public ModelAndView handleUserNotEnabledException(RuntimeException ex) {
    	logger.error(ex.getMessage());
    	ModelAndView mav = new ModelAndView();
    	mav.addObject("exception", "UserNotEnabled");
    	mav.setViewName("exception/error_401");
    	return mav;
    }

}
