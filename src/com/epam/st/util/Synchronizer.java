package com.epam.st.util;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public final class Synchronizer {
	private static final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
	private static final Lock readLock = lock.readLock();
	private static final Lock writeLock = lock.writeLock();
	
	private Synchronizer() {
	}
	
	public static Lock getReadLock() {
		return readLock;
	}
	
	public static Lock getWriteLock() {
		return writeLock;
	}
}
