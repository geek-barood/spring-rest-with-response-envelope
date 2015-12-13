package hello;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Created by aniruddha@primaseller.com on 13/12/15.
 */
public class ResponseEnvelope {
	private final boolean success;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Object result;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private List<ErrorBody> errors;

	public ResponseEnvelope(boolean success) {
		this.success = success;
	}

	public ResponseEnvelope addError(ErrorBody error) {
		if (errors == null) {
			errors = new ArrayList<ErrorBody>();
		}
		errors.add(error);
		return this;
	}

	public ResponseEnvelope addResult(Object result) {
		if (this.result == null) {
			this.result = result;
		}
		return this;
	}

	public boolean isSuccess() {
		return success;
	}

	public Object getResult() {
		return result;
	}

	public List<ErrorBody> getErrors() {
		return errors;
	}
}
