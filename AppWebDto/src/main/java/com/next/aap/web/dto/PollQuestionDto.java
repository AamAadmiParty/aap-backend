package com.next.aap.web.dto;

import java.util.Date;
import java.util.List;

public class PollQuestionDto {

	private Long id;
	private String content;//content of news which can be html or plain text
	private String imageUrl;// image preview url for this news item
	private String webUrl;// Web url link for this news, which will be shared by share intent
	private String originalUrl;// Web url link for this news, which will be shared by share intent
	private Date validTillDate;//Publish date of this item
	private boolean global;//Whether this News is available global or not
	private ContentStatus contentStatus;
	private List<PollAnswerDto> answers;
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
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public String getWebUrl() {
		return webUrl;
	}
	public void setWebUrl(String webUrl) {
		this.webUrl = webUrl;
	}
	public String getOriginalUrl() {
		return originalUrl;
	}
	public void setOriginalUrl(String originalUrl) {
		this.originalUrl = originalUrl;
	}
	public Date getValidTillDate() {
		return validTillDate;
	}
	public void setValidTillDate(Date validTillDate) {
		this.validTillDate = validTillDate;
	}
	public boolean isGlobal() {
		return global;
	}
	public void setGlobal(boolean global) {
		this.global = global;
	}
	public ContentStatus getContentStatus() {
		return contentStatus;
	}
	public void setContentStatus(ContentStatus contentStatus) {
		this.contentStatus = contentStatus;
	}
	public List<PollAnswerDto> getAnswers() {
		return answers;
	}
	public void setAnswers(List<PollAnswerDto> answers) {
		this.answers = answers;
	}

	
	

	
}
