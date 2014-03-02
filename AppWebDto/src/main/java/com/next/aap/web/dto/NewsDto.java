package com.next.aap.web.dto;

import java.util.Date;

public class NewsDto {

	private Long id;
	private String title; // Title of the news/post item
	private String content;//content of news which can be html or plain text
	private String contentSummary;//content of news which can be html or plain text
	private String imageUrl;// image preview url for this news item
	private String webUrl;// Web url link for this news, which will be shared by share intent
	private String originalUrl;// Web url link for this news, which will be shared by share intent
	private String source;// source of the new like, AamAadmiParty.org or IndianExponent.com or aapkiawaz.com etc
	private String author;// nullable, name of the person who wrote this article
	private Date date;//Publish date of this item
	private boolean global;//Whether this News is available global or not
	private ContentStatus contentStatus;
	private String rejectionReason;
	private Date publishDate;
	private Date dateCreated;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
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
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
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
	public String getContentSummary() {
		return contentSummary;
	}
	public void setContentSummary(String contentSummary) {
		this.contentSummary = contentSummary;
	}
	public String getRejectionReason() {
		return rejectionReason;
	}
	public void setRejectionReason(String rejectionReason) {
		this.rejectionReason = rejectionReason;
	}
	public Date getPublishDate() {
		return publishDate;
	}
	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}
	public Date getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}
	@Override
	public String toString() {
		return "NewsDto [id=" + id + ", title=" + title + ", imageUrl=" + imageUrl
				+ ", webUrl=" + webUrl + ", originalUrl=" + originalUrl + ", source=" + source + ", author=" + author + ", date=" + date + ", global=" + global
				+ ", contentStatus=" + contentStatus + ", rejectionReason=" + rejectionReason + ", publishDate=" + publishDate + ", dateCreated="
				+ dateCreated + "]";
	}


}
