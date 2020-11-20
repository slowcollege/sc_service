package com.slow.college.response;

import java.util.List;

@SuppressWarnings("serial")
public class ListResp<T> extends BaseResponse {
	
	private Integer total;

	private List<T> result;

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public List<T> getResult() {
		return result;
	}

	public void setResult(List<T> result) {
		this.result = result;
	}

	

	

}
