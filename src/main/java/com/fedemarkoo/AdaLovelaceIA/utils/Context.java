package com.fedemarkoo.AdaLovelaceIA.utils;

import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;

@Data
@Component
public class Context {
	private User user;
	private Method expectedMethod;
	private Object instance;

}
