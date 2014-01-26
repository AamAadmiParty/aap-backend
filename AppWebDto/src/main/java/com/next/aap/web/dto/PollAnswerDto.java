package com.next.aap.web.dto;


public class PollAnswerDto {

	private Long id;
	private String content;//content of news which can be html or plain text
	private Long pollQuestionId;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Long getPollQuestionId() {
		return pollQuestionId;
	}
	public void setPollQuestionId(Long pollQuestionId) {
		this.pollQuestionId = pollQuestionId;
	}
	@Override
	public String toString() {
		return "PollAnswerDto [id=" + id + ", content=" + content + ", pollQuestionId=" + pollQuestionId + "]";
	}
	
	
	
}
