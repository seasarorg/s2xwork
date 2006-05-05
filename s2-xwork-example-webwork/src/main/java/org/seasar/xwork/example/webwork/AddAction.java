package org.seasar.xwork.example.webwork;

import com.opensymphony.xwork.ActionSupport;

public class AddAction extends ActionSupport {
	private AddService addService;

	private AddDto addDto;

	public AddAction(AddService addService) {
		this.addService = addService;
	}

	public String execute() throws Exception {
		addDto.setResult(addService.add(addDto.getArg1(), addDto.getArg2()));
		return SUCCESS;
	}

	public void setAddService(AddService addService) {
		this.addService = addService;
	}

	public void setAddDto(AddDto addDto) {
		this.addDto = addDto;
	}
}
