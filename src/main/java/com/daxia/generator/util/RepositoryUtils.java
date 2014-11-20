package com.daxia.generator.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class RepositoryUtils<T> {
	
	public static <T> List<T> iterableToList(Iterable<T> iterable) {
		if (iterable == null) {
			return new ArrayList<T>();
		}
		Iterator<T> iterator = iterable.iterator();
		List<T> list = new ArrayList<T>();
		while (iterator.hasNext()) {
			list.add(iterator.next());
		}
		return list;
	}
	
}
