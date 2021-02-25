package com.fedemarkoo.AdaLovelaceIA.objects;

import com.fedemarkoo.AdaLovelaceIA.utils.Context;
import com.fedemarkoo.AdaLovelaceIA.utils.FraseUtil;
import lombok.Singleton;
import org.springframework.stereotype.Component;

@Component
@Singleton
public class Object {

	private static Context context;

	public static Context getContext() {
		if (context != null) {
			return context;
		}
		return context = new Context();
	}

	@SuppressWarnings("SameParameterValue")
	protected void setExpectedMethod(String method, Class clazz) {
		try {
			//noinspection unchecked
			getContext().setExpectedMethod(clazz.getDeclaredMethod(method, FraseUtil.class));
			getContext().setInstance(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void cleanExpectedMethod() {
		getContext().setExpectedMethod(null);
	}

}
