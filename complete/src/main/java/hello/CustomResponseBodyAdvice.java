package hello;

import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Created by aniruddha@primaseller.com on 13/12/15.
 */

@ControllerAdvice
public class CustomResponseBodyAdvice extends ResponseEntityExceptionHandler implements ResponseBodyAdvice<Object> {
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handleOtherExceptions(Exception ex, WebRequest request) {
		ResponseEnvelope envelop = new ResponseEnvelope(false)
				.addError(new ErrorBody("UNKNOWN_ERROR", ex.getMessage()));
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		return new ResponseEntity<Object>(envelop, HttpStatus.OK);
	}

	@Override public boolean supports(MethodParameter methodParameter,
			Class<? extends HttpMessageConverter<?>> aClass) {
		return true;
	}

	@Override public Object beforeBodyWrite(Object body, MethodParameter methodParameter, MediaType mediaType,
			Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest,
			ServerHttpResponse serverHttpResponse) {
		return body instanceof ResponseEnvelope ? body : new ResponseEnvelope(true).addResult(body);
	}
}
