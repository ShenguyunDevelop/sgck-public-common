package com.serotonin.modbus4j.exception;

public class ModbusConnectionExeption extends Exception {
	// modbus异常返回的代码
	private int code;

	private static final long serialVersionUID = -1;

	public ModbusConnectionExeption(String message, int code) {
	        super(message);
	        this.code = code;
	    }

	public ModbusConnectionExeption(Throwable cause) {
	        super(cause);
	    }

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

}
