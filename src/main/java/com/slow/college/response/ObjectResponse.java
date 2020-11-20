package com.slow.college.response;

@SuppressWarnings("serial")
public class ObjectResponse<T> extends BaseResponse {

	private T result;

	public T getResult() {
		return result;
	}

	public void setResult(T result) {
		this.result = result;
	}

	@Override
	public String toString() {
		return " [result=" + result + "]";
	}

}
