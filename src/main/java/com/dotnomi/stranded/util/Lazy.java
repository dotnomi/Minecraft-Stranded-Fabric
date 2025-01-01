package com.dotnomi.stranded.util;

@SuppressWarnings("unused")
public class Lazy<T> {
	private T value;
	private boolean isInitialized = false;

	public Lazy() {}

	private Lazy(T initialValue) {
		this.value = initialValue;
		this.isInitialized = initialValue != null;
	}

	public static <T> Lazy<T> of(T value) {
		return new Lazy<>(value);
	}

	public void set(T value) {
		if (isInitialized) {
			throw new UnsupportedOperationException("Lazy value has already been initialized.");
		}
		this.value = value;
		this.isInitialized = true;
	}

	public T get() {
		if (!isInitialized) {
			throw new IllegalStateException("Lazy value has not been initialized yet.");
		}
		return value;
	}

	public boolean isInitialized() {
		return isInitialized;
	}
}
