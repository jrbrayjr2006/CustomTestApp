/**
 * 
 */
package com.itcert.customtestapp.model;

import java.util.ArrayList;

/**
 * @author james_r_bray
 *
 */
public class Question {
	
	private ArrayList<String> questionOptions;
	private String question;
	private String selectedOption;
	private String solution;
	private int questionNumber;

	/**
	 * 
	 */
	public Question() {
		// TODO Auto-generated constructor stub
	}

	public ArrayList<String> getQuestionOptions() {
		return questionOptions;
	}

	public void setQuestionOptions(ArrayList<String> questionOptions) {
		this.questionOptions = questionOptions;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getSolution() {
		return solution;
	}

	public void setSolution(String solution) {
		this.solution = solution;
	}

	public int getQuestionNumber() {
		return questionNumber;
	}

	public void setQuestionNumber(int questionNumber) {
		this.questionNumber = questionNumber;
	}

	public String getSelectedOption() {
		return selectedOption;
	}

	public void setSelectedOption(String selectedOption) {
		this.selectedOption = selectedOption;
	}

}
