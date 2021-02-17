package com.fedemarkoo.AdaLovelaceIA.dao;

import com.fedemarkoo.AdaLovelaceIA.utils.ObjectUtil;
import com.mongodb.client.FindIterable;
import com.mongodb.client.model.Filters;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.lang.reflect.ParameterizedType;

public class GeneralDao<T> {

	protected final Class<T> type = (Class<T>) ((ParameterizedType)( getClass().getGenericSuperclass())).getActualTypeArguments()[0];

	@Value("${collectionData}")
	private String collection;

	@Autowired
	protected MongoTemplate mongo;

	<T> T findById(String id) {
		return (T) mongo.findById(id, type);
	}

	<T> T findOne(Criteria q){
		return (T) mongo.findOne(Query.query(q), type);
	}

	<T> T findBySinonimo(String word) {
		return findOne(Criteria.where("sinonimos").elemMatch(new Criteria().is(word)));
	}

	void save(T obj){
		mongo.save(obj);
	}
}
