package com.next.aap.core.persistance;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;

@Entity
@Table(name="facebook_account")
//@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE,region="Account", include="all")
public class FacebookAccount {

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

	
	@Column(name = "user_name", length=256)
	private String userName;

	@Column(name = "facebook_user_id", nullable = false)
	private String facebookUserId;

	@Column(name = "image_url", nullable = false)
	private String imageUrl;

	@ManyToOne( cascade = {CascadeType.PERSIST, CascadeType.MERGE} )
    @JoinColumn(name="user_id")
    private User user;
	@Column(name="user_id", insertable=false,updatable=false)
	private Long userId;
	
	@Column(name = "voice_of_aap", nullable = false)
	private boolean voiceOfAap;
	
	@Column(name = "allow_ddu", nullable = false)
	private boolean allowDdu;

	@Column(name = "allow_timeline", nullable = false)
	private boolean allowTimeLine;

	@Column(name = "last_success")
	private Date lastSuccess;

	@Column(name = "last_failure")
	private Date lastFailure;

	@ManyToMany(cascade=CascadeType.ALL)
	@JoinTable(name = "facebook_friends",
	joinColumns = {
	@JoinColumn(name="facebook_account_id") 
	},
	inverseJoinColumns = {
	@JoinColumn(name="friend_facebook_account_id")
	})
	private Set<FacebookAccount> friendsAccounts;
	
	@Column(name = "total_friends")
	private Integer totalFriends;

	@Column(name = "total_friends_with_us")
	private Integer totalFriendsWithUs;

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
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getFacebookUserId() {
		return facebookUserId;
	}
	public void setFacebookUserId(String facebookUserId) {
		this.facebookUserId = facebookUserId;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public boolean isVoiceOfAap() {
		return voiceOfAap;
	}
	public void setVoiceOfAap(boolean voiceOfAap) {
		this.voiceOfAap = voiceOfAap;
	}
	public boolean isAllowDdu() {
		return allowDdu;
	}
	public void setAllowDdu(boolean allowDdu) {
		this.allowDdu = allowDdu;
	}
	public boolean isAllowTimeLine() {
		return allowTimeLine;
	}
	public void setAllowTimeLine(boolean allowTimeLine) {
		this.allowTimeLine = allowTimeLine;
	}
	public Set<FacebookAccount> getFriendsAccounts() {
		return friendsAccounts;
	}
	public void setFriendsAccounts(Set<FacebookAccount> friendsAccounts) {
		this.friendsAccounts = friendsAccounts;
	}
	public Date getLastSuccess() {
		return lastSuccess;
	}
	public void setLastSuccess(Date lastSuccess) {
		this.lastSuccess = lastSuccess;
	}
	public Date getLastFailure() {
		return lastFailure;
	}
	public void setLastFailure(Date lastFailure) {
		this.lastFailure = lastFailure;
	}
	public Integer getTotalFriends() {
		return totalFriends;
	}
	public void setTotalFriends(Integer totalFriends) {
		this.totalFriends = totalFriends;
	}
	public Integer getTotalFriendsWithUs() {
		return totalFriendsWithUs;
	}
	public void setTotalFriendsWithUs(Integer totalFriendsWithUs) {
		this.totalFriendsWithUs = totalFriendsWithUs;
	}
}
