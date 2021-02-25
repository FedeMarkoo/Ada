package com.fedemarkoo.AdaLovelaceIA.utils;

import com.fedemarkoo.AdaLovelaceIA.exceptions.UnknownClassException;
import com.fedemarkoo.AdaLovelaceIA.exceptions.UnknownMethodException;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

@NoArgsConstructor
public class Action {
	private ObjectUtil object;
	private Method method;
	private static final String OBJECTS_LOCATION = "com.fedemarkoo.AdaLovelaceIA.objects";
	private java.lang.Object instance;
	private Class<?> clazz;

	private static boolean anyMatch(final Metodo m, final String name) {
		return m.getSinonimos() != null && m.getSinonimos().stream().anyMatch(m2 -> m2.equals(name));
	}

	public void setMethod(String method) throws UnknownMethodException, UnknownClassException {
		try {
			this.method = getClazz().getMethod(getMethodName(method), FraseUtil.class);
		} catch (ReflectiveOperationException e) {
			throw new UnknownMethodException(e);
		}
	}

	private Class<?> getClazz() throws UnknownClassException {
		if (clazz != null)
			return clazz;

		try {
			return clazz = Class.forName(getObject().getClassName());
		} catch (ReflectiveOperationException e) {
			throw new UnknownClassException(e);
		}
	}

	@SneakyThrows
	private java.lang.Object getInstance() {
		if (instance != null) return instance;
		return instance = getClazz().getConstructor().newInstance();
	}

	@SneakyThrows
	public java.lang.Object execute(FraseUtil fraseUtil) {
		return method.invoke(getInstance(), fraseUtil);
	}

	private String getMethodName(final String method) throws UnknownMethodException {
		List<Metodo> metodos = getObject().getMetodos();
		Metodo metodo = metodos.stream().filter(m ->
				m.getName().equals(method)
		).findFirst().orElse(getMethodNameSinonimo(method));
		if (metodo == null) {
			return Arrays.stream(clazz.getMethods())
					.filter(m -> m.getName().equals(method))
					.findFirst().orElseThrow(UnknownMethodException::new).getName();
		}
		return metodo.getName();
	}

	private Metodo getMethodNameSinonimo(String method) {
		List<Metodo> metodos = getObject().getMetodos();
		return metodos.stream().filter(m ->
				anyMatch(m, method)
		).findAny().orElse(null);
	}

	private ObjectUtil getObject() {
		return object;
	}

	public void setObject(ObjectUtil object) {
		this.object = object;
	}
}
