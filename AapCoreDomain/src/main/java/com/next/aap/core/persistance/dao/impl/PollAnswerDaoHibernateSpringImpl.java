package com.next.aap.core.persistance.dao.impl;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.stereotype.Component;

import com.next.aap.core.persistance.PollAnswer;
import com.next.aap.core.persistance.dao.PollAnswerDao;

@Component
public class PollAnswerDaoHibernateSpringImpl extends BaseDaoHibernateSpring<PollAnswer> implements PollAnswerDao{


	private static final long serialVersionUID = 1;

	/* (non-Javadoc)
	 * @see com.next.aap.server.persistance.dao.impl.PollAnswerDao#savePollAnswer(com.next.aap.server.persistance.PollAnswer)
	 */
	@Override
	public PollAnswer savePollAnswer(PollAnswer pollAnswer){
		checkIfStringMissing("Content", pollAnswer.getContent());
		checkIfObjectMissing("Question", pollAnswer.getPollQuestion());
		pollAnswer = super.saveObject(pollAnswer);
		return pollAnswer;
	}

	/* (non-Javadoc)
	 * @see com.next.aap.server.persistance.dao.impl.PollAnswerDao#getPollAnswerById(java.lang.Long)
	 */
	@Override
	public PollAnswer getPollAnswerById(Long id) {
		return super.getObjectById(PollAnswer.class, id);
	}
	/* (non-Javadoc)
	 * @see com.next.aap.server.persistance.dao.impl.PollAnswerDao#getPollAnswersAfterId(java.lang.Long, int)
	 */
	@Override
	public List<PollAnswer> getPollAnswersAfterId(Long lastId,int pageSize){
		Map<String, Object> params = new TreeMap<String, Object>();
		params.put("lastId", lastId);
		params.put("pageSize", pageSize);
		List<PollAnswer> list = executeQueryGetList("from PollAnswer where id > :lastId order by id asc limit :pageSize");
		return list;
	}

	@Override
	public List<PollAnswer> getPollAnswersByQuestionId(Long userId) {
		Map<String, Object> params = new TreeMap<String, Object>();
		params.put("userId", userId);
		return executeQueryGetList("from PollAnswer where userId = :userId", params);
	}
}
