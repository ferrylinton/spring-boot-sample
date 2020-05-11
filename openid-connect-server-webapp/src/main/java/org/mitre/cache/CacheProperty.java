package org.mitre.cache;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CacheProperty {

	private static final String OFF = "OFF";
	
	private static final String INFO = "INFO";

	@Value("#{systemProperties['showSql'] ?: 'false'}")
	private boolean showSql;
	
	@Value("#{systemProperties['disableCache'] ?: 'false'}")
	private boolean disabled;

	public String getLoggingLevel() {
		return showSql ? INFO : OFF;
	}

	public boolean isShowSql() {
		return showSql;
	}

	public void setShowSql(boolean showSql) {
		this.showSql = showSql;
	}

	public boolean isDisabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	
}
