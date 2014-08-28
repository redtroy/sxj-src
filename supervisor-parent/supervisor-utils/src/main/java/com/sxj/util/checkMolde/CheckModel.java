package com.sxj.util.checkMolde;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.sxj.spring.modules.util.Reflections;

public class CheckModel {
	/**
	 * 判断实体类的属性值是否为空，不为空则给另一个实体类赋值 model1 为需要赋值的MODEL，model2赋值MODEL
	 */
	public Object model(Object model1, Object model2)
			throws NoSuchMethodException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		Field[] field2 = model2.getClass().getDeclaredFields(); // 获取实体类2的所有属性，
		for (int j = 0; j < field2.length; j++) { // 遍历所有属性
			String name = field2[j].getName();
			String name1 = name;
			name = name.substring(0, 1).toUpperCase() + name.substring(1);
			// String type = field2[j].getGenericType().toString();
			// System.out.println(type);
			Method m2 = model2.getClass().getMethod("get" + name);
			// String value = (String) m2.invoke(model2); // 调用getter方法获取属性值
			Object value = m2.invoke(model2);
			if (value != null) {
				// Field[] field1 = model1.getClass().getDeclaredFields(); //
				// 获取实体类1的所有属性，
				Reflections.invokeSetter(model1, name, value);

			}
		}
		return model1;
	}

	public static void main(String[] args) throws NoSuchMethodException,
			IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		CheckModel cm = new CheckModel();
		Test t = new Test();
		t.setId("1");
		t.setName("test");
		t.setText(true);
		Test t2 = new Test();
		t2.setName("haha");
		Test t3 = (Test) cm.model(t, t2);
		System.out.println(t3.getId());
		System.out.println(t3.getName());
		System.out.println(t3.getText());
	}
}
