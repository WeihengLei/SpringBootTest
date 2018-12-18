package com.example.test.util;

import org.hibernate.id.UUIDHexGenerator;

public class IdGenerator {
	private UUIDHexGenerator g = new UUIDHexGenerator();

	private static IdGenerator idGenerator = null;

	private IdGenerator() {
	}

	public synchronized static IdGenerator getInstance() {
		if (idGenerator == null) {
			idGenerator = new IdGenerator();
		}
		return idGenerator;
	}

	public String gen() {
		return g.generate(null, null).toString();
	}

}
