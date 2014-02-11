package com.luis.twitter.repository.impl;

import java.util.Collections;
import java.util.Objects;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.GenericTypeResolver;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.luis.twitter.model.BaseDomain;
import com.luis.twitter.repository.BaseRepository;

public abstract class BaseRepositoryImpl<T extends BaseDomain> implements BaseRepository<T> {

	@Autowired
	private DataSource dataSource;

	private NamedParameterJdbcTemplate jdbcTemplate;

	private Class<T> domainClass;

	@PostConstruct
	@SuppressWarnings("unchecked")
	public void initialize() {
		this.domainClass = (Class<T>) GenericTypeResolver.resolveTypeArgument(getClass(), BaseRepositoryImpl.class);
		this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	protected NamedParameterJdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	protected RowMapper<T> getRowMapper() {
		return new BeanPropertyRowMapper<>(domainClass);
	}

	protected void save(String sql, T value) {
		jdbcTemplate.update(sql, new BeanPropertySqlParameterSource(value));
	}

	@Override
	public T findById(Long id) {
		Objects.requireNonNull(id);

		String sql = String.format("SELECT * FROM %s WHERE id = :id", getTableName());

		return jdbcTemplate.queryForObject(sql, Collections.singletonMap("id", id), getRowMapper());
	}

	@Override
	public void delete(T value) {
		Objects.requireNonNull(value);

		String deleteQuery = String.format("DELETE FROM %s WHERE id = :id", getTableName());

		jdbcTemplate.update(deleteQuery, Collections.singletonMap("id", value.getId()));
	}

	protected abstract Object getTableName();

}
