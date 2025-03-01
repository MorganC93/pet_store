package pet.store.controller.error;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalErrorHandler {

	@ExceptionHandler(NoSuchElementException.class)
	public Map<String,String> handleNoSuchElementException(NoSuchElementException ex) {
		log.info("Error occurred: {}", ex.toString());
		
		Map<String, String> response = new HashMap<>();
		response.put("message", ex.toString());
		
		return response; 
	}
}
