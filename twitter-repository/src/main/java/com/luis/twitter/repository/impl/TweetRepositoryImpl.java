package com.luis.twitter.repository.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import com.luis.twitter.model.Tweet;
import com.luis.twitter.model.User;
import com.luis.twitter.repository.TweetRepository;
import com.luis.twitter.repository.exceptions.NotASingleResultException;

@Repository
@Transactional
public class TweetRepositoryImpl extends BaseRepositoryImpl<Tweet> implements TweetRepository {

	@Override
	protected String getTableName() {
		return "tweets";
	}

	@Override
	public List<Tweet> timelineTweetsForUser(User user) {
		return timelineTweetsForUser(user, "");
	}

	@Override
	public List<Tweet> timelineTweetsForUser(User user, String searchString) {
		String query = "SELECT t.* " //
				+ " FROM tweets t " //
				+ " WHERE  " //
				+ "       ( " //
				+ "         t.author_id = :user_id " //
				+ "        OR " //
				+ "		    t.author_id IN (SELECT uf.following_id FROM user_following uf WHERE uf.user_id = :user_id ) " //
				+ "        ) " //
				+ " %s " //
				+ " ORDER BY t.creation_date DESC ";

		Map<String, Object> parameters = new HashMap<>();
		parameters.put("user_id", user.getId());

		String searchPredicate = "";
		if (!StringUtils.isEmpty(searchString)) {
			searchPredicate = " AND LOWER( t.contents ) LIKE :contents ";
			parameters.put("contents", "%" + searchString.toLowerCase() + "%");
		}
		query = String.format(query, searchPredicate);
		List<Tweet> tweets = getJdbcTemplate().query(query, parameters, getRowMapper());
		return tweets;
	}

	@Override
	public Tweet addTweet(String contents, User user) {
		Assert.hasText(contents);
		Assert.notNull(user);
		Date creationDate = new Date();
		String query = "INSERT INTO tweets (contents,author_id,creation_date) VALUES ( :contents, :author_id, :creation_date )";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("author_id", user.getId());
		parameters.put("contents", contents);
		parameters.put("creation_date", creationDate);
		getJdbcTemplate().update(query, parameters);

		return findTweet(contents, creationDate, user);
	}

	public Tweet findTweet(String contents, Date creationDate, User user) {
		List<Tweet> tweets = findTweets(contents, creationDate, user);

		if (tweets != null && tweets.size() != 1) {
			throw new NotASingleResultException("tweet", tweets);
		}
		return tweets.get(0);
	}

	public List<Tweet> findTweets(String contents, Date creationDate, User user) {
		Map<String, Object> parameters = new HashMap<>();

		String query = "SELECT t.* FROM tweets t WHERE ";
		if (!StringUtils.isEmpty(contents)) {
			query += " t.contents = :contents AND";
			parameters.put("contents", contents);
		}
		if (creationDate != null) {
			query += " t.creation_date = :creation_date AND ";
			parameters.put("creation_date", creationDate);
		}
		if (user != null) {
			query += " t.author_id = :author_id AND";
			parameters.put("author_id", user.getId());
		}

		query = query.substring(0, query.lastIndexOf("AND"));
		return getJdbcTemplate().query(query, parameters, getRowMapper());
	}
}