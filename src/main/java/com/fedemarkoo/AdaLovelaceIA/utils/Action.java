package com.fedemarkoo.AdaLovelaceIA.utils;

import com.fedemarkoo.AdaLovelaceIA.exceptions.UnknownClassException;
import com.fedemarkoo.AdaLovelaceIA.exceptions.UnknownMethodException;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Stream;

@NoArgsConstructor
public class Action {
	private ObjectUtil object;
	private Method method;
	private Class clazz;
	private java.lang.Object instance;
	private static String OBJECTS_LOCATION = "com.fedemarkoo.AdaLovelaceIA.objects.";

	public void setMethod(String method) throws UnknownMethodException, UnknownClassException {
		try {
			this.method = getClazz().getMethod(getMethodName(method),FraseUtil.class);
		} catch (ReflectiveOperationException e) {
			throw new UnknownMethodException(e);
		}
	}

	private Class getClazz() throws UnknownClassException {
		if (clazz != null)
			return clazz;

		try {
			return clazz = Class.forName(getObject().getClassName());
		} catch (ReflectiveOperationException e) {
			throw new UnknownClassException(e);
		}
	}

	@SneakyThrows
	public java.lang.Object execute(FraseUtil fraseUtil) {
		return method.invoke(getInstance(),fraseUtil);
	}

	@SneakyThrows
	private java.lang.Object getInstance() {
		if (instance != null) return instance;
		return instance = getClazz().getConstructor().newInstance();
	}

	private String getMethodName(String method) throws UnknownMethodException {
		List<Metodo> metodos = getObject().getMetodos();
		Metodo metodo = metodos.stream().filter(m ->
				m.getName().equals(method)
		).findFirst().get();
		if (metodo == null) {
			metodo = getMethodNameSinonimo(method);
		}
		return metodo.getName();
	}

	private Metodo getMethodNameSinonimo(String method) throws UnknownMethodException {
		List<Metodo> metodos = getObject().getMetodos();
		Metodo metodo = metodos.stream().filter(m ->
				anyMatch(m, method)
		).findAny().get();
		if (metodo == null) {
			throw new UnknownMethodException();
		}
		return metodo;
	}

	private static boolean anyMatch(Metodo m, String name) {
		return m.getSinonimos().stream().anyMatch(m2 -> m2.equals(name));
	}

	private ObjectUtil getObject() {
		return object;
	}

	public void setObject(ObjectUtil object) {
		this.object = object;
	}
}
