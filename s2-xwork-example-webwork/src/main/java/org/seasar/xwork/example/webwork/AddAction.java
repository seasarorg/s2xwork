package org.seasar.xwork.example.webwork;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.seasar.xwork.annotation.Param;
import org.seasar.xwork.annotation.Result;
import org.seasar.xwork.annotation.XWorkAction;

import com.opensymphony.xwork.ActionSupport;

@XWorkAction(name = "add", result = @Result(type = "velocity", param = @Param(name = "location", value = "add_success.vm")))
public class AddAction extends ActionSupport {
	private AddService addService;

	private AddDto addDto;
	
	private Log log = LogFactory.getLog(AddAction.class);

	public AddAction(AddService addService) {
		this.addService = addService;
	}

	public String execute() throws Exception {
		log.debug("arg1:"+addDto.getArg1());
		log.debug("arg2:"+addDto.getArg2());
		addDto.setResult(addService.add(addDto.getArg1(), addDto.getArg2()));
		log.debug("result:"+addDto.getResult());
		return SUCCESS;
	}

	public void setAddService(AddService addService) {
		this.addService = addService;
	}

	public void setAddDto(AddDto addDto) {
		this.addDto = addDto;
	}
}
