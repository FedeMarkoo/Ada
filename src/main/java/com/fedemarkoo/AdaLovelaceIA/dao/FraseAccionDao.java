package com.fedemarkoo.AdaLovelaceIA.dao;

import com.fedemarkoo.AdaLovelaceIA.utils.FraseAccion;
import org.springframework.stereotype.Service;

@Service
public class FraseAccionDao extends GeneralDao<FraseAccion> {
	public FraseAccion findById(String id) {
		return super.findById(id);
	}
}
