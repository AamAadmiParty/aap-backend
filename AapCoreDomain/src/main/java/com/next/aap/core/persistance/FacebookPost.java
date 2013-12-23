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
@Table(name="facebook_post")
public class FacebookPost {

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

	
	@Column(name = "post_type")
	private String postType;
	
	@Column(name = "picture")
	private String picture;

	@Column(name = "message", columnDefinition="text")
	private String message;
	
	@Column(name = "source")
	private String source;

	@Column(name = "facebook_post_ext_id", nullable = false)
	private String facebookPostExternalId;

	@Column(name = "link")
	private String link;

	@Column(name = "caption")
	private String caption;

	@Column(name = "name")
	private String name;

	@Column(name = "description")
	private String description;

	@Column(name = "total_likes")
	private int totalLikes;
	
	@Column(name = "total_share")
	private Integer totalShares;

	@Column(name = "total_comments")
	private int totalComments;

	@Column(name = "total_reachability")
	private int totalReachability;

	@Column(name = "last_refresh_time")
	private Date lastRefreshTime;

	@ManyToOne( cascade = {CascadeType.PERSIST, CascadeType.MERGE} )
    @JoinColumn(name="facebook_page_id")
    private FacebookPage facebookPage;
	@Column(name="facebook_page_id", insertable=false,updatable=false)
	private Long facebookPageId;
	
	@ManyToOne( cascade = {CascadeType.PERSIST, CascadeType.MERGE} )
    @JoinColumn(name="facebook_group_id")
    private FacebookGroup facebookGroup;
	@Column(name="facebook_group_id", insertable=false,updatable=false)
	private Long facebookGroupId;
	
	
	@ManyToOne( cascade = {CascadeType.PERSIST, CascadeType.MERGE} )
    @JoinColumn(name="facebook_account_id")
    private FacebookAccount facebookAccount;
	@Column(name="facebook_account_id", insertable=false,updatable=false)
	private Long facebookAccountId;
	
	
	
	
}
