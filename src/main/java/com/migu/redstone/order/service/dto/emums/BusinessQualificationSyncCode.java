package com.migu.redstone.order.service.dto.emums;

/**
 * 业务资格办理校验
 * 
 * 是否同步，0:异步，1:同步，默认为1:同步
 */
public enum BusinessQualificationSyncCode {

	/**
	 * 同步
	 */
	SYNC("1"),
	/**
	 * 异步
	 */
	ASYNC("0");

	private String sync;

	private BusinessQualificationSyncCode(String sync) {
		this.sync = sync;
	}

	public String getServiceType() {
		return sync;
	}

}
