package com.next.aap.core.persistance.dao;

import java.util.List;

import com.next.aap.core.persistance.ContentTweet;

public interface ContentTweetDao {

	/**
	 * Creates/updates a contentTweet in Database
	 * 
	 * @param contentTweet
	 * @return saved contentTweet
	 * @
	 */
	public abstract ContentTweet saveContentTweet(ContentTweet contentTweet);

	/**
	 * deletes a contentTweet in Database
	 * 
	 * @param contentTweet
	 * @return updated contentTweet
	 * @
	 */
	public abstract void deleteContentTweet(ContentTweet contentTweet);

	/**
	 * return a ContentTweet with given primary key/id
	 * 
	 * @param id
	 * @return ContentTweet with PK as id(parameter)
	 * @
	 */
	public abstract ContentTweet getContentTweetById(Long id);
	
	public abstract List<ContentTweet> getAllContentTweets();

}