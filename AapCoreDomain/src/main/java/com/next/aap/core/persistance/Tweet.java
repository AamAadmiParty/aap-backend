package com.next.aap.core.persistance;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;

@Entity
@Table(name="tweet")
public class Tweet {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	@Version
	@Column(name="ver")
	private int ver;
	
	@Column(name="date_created")
	private Date dateCreated;
	@Column(name="date_modified")
	private Date dateModified;
	@Column(name="creator_id")
	private Long creatorId;
	@Column(name="modifier_id")
	private Long modifierId;
	
	@Column(name = "tweet_external_id")
	private Long tweetExternalId;//content of tweet
	@Column(name = "tweet_content", length=256)
	private String tweetContent;//content of tweet
	@Column(name = "image_url")
	private String imageUrl;// image preview url for this tweet
	
	@ManyToOne( cascade = {CascadeType.PERSIST, CascadeType.MERGE} )
    @JoinColumn(name="twitter_account_id")
    private TwitterAccount twitterAccount;
	@Column(name="twitter_account_id", insertable=false,updatable=false)
	private Long twitterAccountId;

	@ManyToOne( cascade = {CascadeType.PERSIST, CascadeType.MERGE} )
    @JoinColumn(name="planned_tweet_id")
    private PlannedTweet plannedTweet;
	@Column(name="planned_tweet_id", insertable=false,updatable=false)
	private Long plannedTweetId;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public int getVer() {
		return ver;
	}
	public void setVer(int ver) {
		this.ver = ver;
	}
	public Date getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}
	public Date getDateModified() {
		return dateModified;
	}
	public void setDateModified(Date dateModified) {
		this.dateModified = dateModified;
	}
	public Long getCreatorId() {
		return creatorId;
	}
	public void setCreatorId(Long creatorId) {
		this.creatorId = creatorId;
	}
	public Long getModifierId() {
		return modifierId;
	}
	public void setModifierId(Long modifierId) {
		this.modifierId = modifierId;
	}
	public Long getTweetExternalId() {
		return tweetExternalId;
	}
	public void setTweetExternalId(Long tweetExternalId) {
		this.tweetExternalId = tweetExternalId;
	}
	public String getTweetContent() {
		return tweetContent;
	}
	public void setTweetContent(String tweetContent) {
		this.tweetContent = tweetContent;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public TwitterAccount getTwitterAccount() {
		return twitterAccount;
	}
	public void setTwitterAccount(TwitterAccount twitterAccount) {
		this.twitterAccount = twitterAccount;
	}
	public Long getTwitterAccountId() {
		return twitterAccountId;
	}
	public void setTwitterAccountId(Long twitterAccountId) {
		this.twitterAccountId = twitterAccountId;
	}
	public PlannedTweet getPlannedTweet() {
		return plannedTweet;
	}
	public void setPlannedTweet(PlannedTweet plannedTweet) {
		this.plannedTweet = plannedTweet;
	}
	public Long getPlannedTweetId() {
		return plannedTweetId;
	}
	public void setPlannedTweetId(Long plannedTweetId) {
		this.plannedTweetId = plannedTweetId;
	}
}
