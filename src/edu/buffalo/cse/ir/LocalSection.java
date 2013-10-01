package edu.buffalo.cse.ir;

public class LocalSection {

	private String title;
	private String text;

	public LocalSection(String title, String text) {

		this.text = text;
		this.title = title;
		// TODO Auto-generated constructor stub
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
