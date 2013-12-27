package com.next.aap.core.persistance.dao.impl;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.stereotype.Component;

import com.next.aap.core.persistance.Tweet;
import com.next.aap.core.persistance.dao.TweetDao;

@Component
public class TweetDaoHibernateSpringImpl extends BaseDaoHibernateSpring<Tweet> implements TweetDao {

	private static final long serialVersionUID = 1L;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.next.imager.persistance.helper.TweetDao#saveTweet(com
	 * .next.imager.persistance.Tweet)
	 */
	@Override
	public Tweet saveTweet(Tweet tweet) {
		tweet = super.saveObject(tweet);
		return tweet;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.next.imager.persistance.helper.TweetDao#getTweetById
	 * (java.lang.Long)
	 */
	@Override
	public Tweet getTweetById(Long id) {
		return super.getObjectById(Tweet.class, id);
	}

	@Override
	public Tweet getTweetByPlannedPostIdAndTwitterAccountId(Long plannedTweetId, Long twitterAccountId) {
		Map<String, Object> params = new TreeMap<String, Object>();
		params.put("plannedTweetId", plannedTweetId);
		params.put("twitterAccountId", twitterAccountId);
		Tweet tweet = executeQueryGetObject("from Tweet where plannedTweetId = :plannedTweetId and twitterAccountId = :twitterAccountId", params);
		return tweet;
	}

	@Override
	public List<Tweet> getTweetByUserId(Long userId) {
		Map<String, Object> params = new TreeMap<String, Object>();
		params.put("userId", userId);
		return executeQueryGetList("from Tweet where twitterAccount.userId = :userId order by dateCreated desc", params);
	}


}
