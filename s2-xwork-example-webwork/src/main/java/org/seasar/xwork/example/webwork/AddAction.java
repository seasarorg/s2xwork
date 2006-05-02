package org.seasar.xwork.example.webwork;

import com.opensymphony.xwork.ActionSupport;

public class AddAction extends ActionSupport {
	private AddService addService;

	private int arg1;

	private int arg2;

	private int result;

	public AddAction(AddService addService) {
		this.addService = addService;
	}

	public void setArg1(int arg1) {
		this.arg1 = arg1;
	}

	public void setArg2(int arg2) {
		this.arg2 = arg2;
	}

	public int getResult() {
		return result;
	}

	public String execute() throws Exception {
		result = addService.add(arg1, arg2);
		return SUCCESS;
	}

	public void setAddService(AddService addService) {
		this.addService = addService;
	}
}
