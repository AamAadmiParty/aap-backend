package com.next.aap.core.persistance.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.next.aap.core.persistance.ContentTweet;
import com.next.aap.core.persistance.dao.ContentTweetDao;

@Repository
public class ContentTweetDaoHibernateSpringImpl extends BaseDaoHibernateSpring<ContentTweet> implements ContentTweetDao  {

	private static final long serialVersionUID = 1L;

	/* (non-Javadoc)
	 * @see com.next.aap.server.persistance.dao.impl.ContentTweetDao#saveContentTweet(com.next.aap.server.persistance.ContentTweet)
	 */
	@Override
	public ContentTweet saveContentTweet(ContentTweet contentTweet) 
	{
		checkIfStringMissing("Tweet Content", contentTweet.getTweetContent());
		contentTweet = super.saveObject(contentTweet);
		return contentTweet;
	}
	

	/* (non-Javadoc)
	 * @see com.next.aap.server.persistance.dao.impl.ContentTweetDao#deleteContentTweet(com.next.aap.server.persistance.ContentTweet)
	 */
	@Override
	public void deleteContentTweet(ContentTweet contentTweet)  {
		super.deleteObject(contentTweet);
	}

	/* (non-Javadoc)
	 * @see com.next.aap.server.persistance.dao.impl.ContentTweetDao#getContentTweetById(java.lang.Long)
	 */
	@Override
	public ContentTweet getContentTweetById(Long id) 
	{
		ContentTweet contentTweet = super.getObjectById(ContentTweet.class, id);
		return contentTweet;
	}

	/* (non-Javadoc)
	 * @see com.next.aap.server.persistance.dao.impl.ContentTweetDao#getAllContentTweets()
	 */
	@Override
	public List<ContentTweet> getAllContentTweets() 
	{
		List<ContentTweet> list = executeQueryGetList("from ContentTweet");
		return list;
	}
	
}