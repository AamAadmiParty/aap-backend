package com.next.aap.core.persistance.dao;

import com.next.aap.core.persistance.UserPollVote;

public interface UserPollVoteDao {

	public abstract UserPollVote saveUserPollVote(UserPollVote userPollVote);

	public abstract UserPollVote getUserPollVoteById(Long id);

	public abstract UserPollVote getUserPollVote(Long userId, Long questionId);
	
}