package com.gf.parallel;

public interface LoggerDelegate {
	void log(final LogLevel logLevel, final String message, final Throwable cause);
	void log(final LogLevel logLevel, final String message, final String ...params);
}
