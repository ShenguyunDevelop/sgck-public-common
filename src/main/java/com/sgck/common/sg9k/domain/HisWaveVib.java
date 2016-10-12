package com.sgck.common.sg9k.domain;

import java.io.Serializable;

/**
 * 振动历史波形
 * TODO
 */
public class HisWaveVib extends HisBase implements Serializable{

	private static final long serialVersionUID = -6887079702529552970L;
	
	private byte[] wave_data;

	public byte[] getWave_data() {
		return wave_data;
	}

	public void setWave_data(byte[] wave_data) {
		this.wave_data = wave_data;
	}
	
}
