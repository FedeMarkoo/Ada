package com.fedemarkoo.AdaLovelaceIA.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.lang.reflect.ParameterizedType;
import java.util.List;

@SuppressWarnings("ALL")
public class GeneralDao<T> {

	protected final Class<T> type = (Class<T>) ((ParameterizedType) (getClass().getGenericSuperclass())).getActualTypeArguments()[0];

	@Value("${collectionData}")
	private String collection;

	@Autowired
	protected MongoTemplate mongo;

	public T findById(String id) {
		return mongo.findById(id, type);
	}

	T findOne(Criteria q) {
		return mongo.findOne(Query.query(q), type);
	}

	T findBySinonimo(String word) {
		return findOne(Criteria.where("sinonimos").elemMatch(new Criteria().is(word)));
	}

	public void save(T obj) {
		mongo.save(obj);
	}

	public List<T> findAll() {
		return mongo.findAll(type);
	}
}
