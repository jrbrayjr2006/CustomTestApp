/**
 * 
 */
package com.itcert.customtestapp.model;

import java.io.Serializable;
import java.util.List;

import android.os.Parcel;

/**
 * @author james_r_bray
 *
 */
public class TestObject implements Serializable {
	
	private static final long serialVersionUID = 3504698616482509143L;
	
	private String testTitle;
	private List<Question> questions;
	private int index;

	/**
	 * 
	 */
	public TestObject() {
		// TODO Auto-generated constructor stub
	}

	public String getTestTitle() {
		return testTitle;
	}

	public void setTestTitle(String testTitle) {
		this.testTitle = testTitle;
	}

	public List<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

}
