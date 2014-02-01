package com.next.aap.core.persistance.dao.impl;

import java.util.Map;
import java.util.TreeMap;

import org.springframework.stereotype.Component;

import com.next.aap.core.persistance.UserPollVote;
import com.next.aap.core.persistance.dao.UserPollVoteDao;

@Component
public class UserPollVoteDaoHibernateSpringImpl extends BaseDaoHibernateSpring<UserPollVote> implements UserPollVoteDao{


	private static final long serialVersionUID = 1;

	/* (non-Javadoc)
	 * @see com.next.aap.server.persistance.dao.impl.UserPollVoteDao#saveUserPollVote(com.next.aap.server.persistance.UserPollVote)
	 */
	@Override
	public UserPollVote saveUserPollVote(UserPollVote userPollVote){
		checkIfObjectMissing("User", userPollVote.getUser());
		checkIfObjectMissing("Poll Question", userPollVote.getPollQuestion());
		checkIfObjectMissing("Poll Answer", userPollVote.getPollAnswer());
		userPollVote = super.saveObject(userPollVote);
		return userPollVote;
	}

	/* (non-Javadoc)
	 * @see com.next.aap.server.persistance.dao.impl.UserPollVoteDao#getUserPollVoteById(java.lang.Long)
	 */
	@Override
	public UserPollVote getUserPollVoteById(Long id) {
		return super.getObjectById(UserPollVote.class, id);
	}

	@Override
	public UserPollVote getUserPollVote(Long userId, Long pollQuestionId) {
		Map<String, Object> params = new TreeMap<String, Object>();
		params.put("userId", userId);
		params.put("pollQuestionId", pollQuestionId);
		UserPollVote userPollVote = executeQueryGetObject("from UserPollVote where userId = :userId and pollQuestionId = :pollQuestionId", params);
		return userPollVote;

	}
}
