package com.fedemarkoo.AdaLovelaceIA.dao;

import com.fedemarkoo.AdaLovelaceIA.utils.ObjectUtil;
import org.springframework.stereotype.Service;

@Service
public class ActionDao extends GeneralDao<ObjectUtil> {

	public ObjectUtil getObject(String word) {
		ObjectUtil byId = super.findById(word);
		if (byId != null) {
			return byId;
		}
		return findBySinonimo(word);
	}

}
