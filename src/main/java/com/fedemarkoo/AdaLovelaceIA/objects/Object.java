package com.fedemarkoo.AdaLovelaceIA.objects;

import com.fedemarkoo.AdaLovelaceIA.utils.Context;
import com.fedemarkoo.AdaLovelaceIA.utils.FraseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.stereotype.Component;

@Component
public class Object {

	private static Context context;
	public static Context getContext() {
		if (context != null) {
			return context;
		}
		return context = new Context();
	}

	protected void setExpectedMethod(String method, Class clazz) {
		try {
			getContext().setExpectedMethod(clazz.getDeclaredMethod(method, FraseUtil.class));
			getContext().setInstance(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void cleanExpectedMethod() {
		getContext().setExpectedMethod(null);
		getContext().setInstance(null);
	}
}
