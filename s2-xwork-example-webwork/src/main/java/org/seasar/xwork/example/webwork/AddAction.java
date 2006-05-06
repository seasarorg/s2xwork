package org.seasar.xwork.example.webwork;

import org.seasar.xwork.annotation.Param;
import org.seasar.xwork.annotation.Result;
import org.seasar.xwork.annotation.XWorkAction;

import com.opensymphony.xwork.ActionSupport;

@XWorkAction(name = "test", interceptorRef = "", result = @Result(type = "velocity", param = @Param(name = "location", value = "add_success.vm")))
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
