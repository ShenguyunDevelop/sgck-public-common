/**
 * 
 */
package com.sgck.notification;

public interface NotificationHandler {
	public void onNotification(String topic,String tag,byte[] content);
}
