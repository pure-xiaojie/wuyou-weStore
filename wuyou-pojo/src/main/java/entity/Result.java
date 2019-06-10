package entity;

import java.io.Serializable;

/** 
 * @author  jie 
 * @date 创建时间：2019年4月9日 下午5:23:46 
 * @version 1.0 
 * @return  
 * */
public class Result implements Serializable{
	private boolean success;   //是否成功
	private String message;  //返回信息
	
	
	public Result(boolean success, String message) {
		super();
		this.success = success;
		this.message = message;
	}
	
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
}
