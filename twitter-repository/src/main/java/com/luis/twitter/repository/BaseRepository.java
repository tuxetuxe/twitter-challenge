package com.luis.twitter.repository;

import com.luis.twitter.model.BaseDomain;

public interface BaseRepository<T extends BaseDomain> {

	T findById(Long id);

	void delete(T value);

}