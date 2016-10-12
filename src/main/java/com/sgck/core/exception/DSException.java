package com.sgck.core.exception;

import com.sgck.core.exception.ErrorCode;

public class DSException extends Exception
{

	protected String whatMsg;
	protected int errCode;
	protected int errLevel;

	public DSException(int code, String what)
	{
		super();
		errLevel = ErrorCode.Level_ERROR;
		errCode = code;
		whatMsg = what;
	}

	public DSException(int level, int code, String what)
	{
		super();
		errLevel = level;
		errCode = code;
		whatMsg = what;
	}

	public int getCode()
	{
		return errCode;
	}

	public String getMessage()
	{
		return whatMsg;
	}

	/**
	 * 获取errLevel
	 *
	 * @return errLevel errLevel
	 */
	public int getLevel()
	{
		return errLevel;
	}

	@Override
	public String toString()
	{
		return "DSException{" +
				"errCode=" + errCode +
				", whatMsg='" + whatMsg + '\'' +
				", errLevel=" + errLevel +
				'}';
	}
}