/* 
 * Team Name: Infinite Loop
 * Project: UB_IR
 * File name: Page.java
 */
package edu.buffalo.cse.ir;

public class Page {

	private String title;
	private String id;
	private String timeStamp;
	private String userName;
	private String text;
	
	// Modified page

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
