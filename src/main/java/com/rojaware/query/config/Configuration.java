package com.rojaware.query.config;

import static java.lang.String.format;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

public final class Configuration {

	private static Logger LOG = Logger.getLogger(Configuration.class);

    private Date released;
    private String version;
    private String active;
    private List< String > databases;
    private List< String > todos;

    public Date getReleased() {
        return released;
    }

    public String getVersion() {
        return version;
    }

    public void setReleased(Date released) {
        this.released = released;
    }

    public void setVersion(String version) {
        this.version = version;
    }



	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public List<String> getDatabases() {
		return databases;
	}

	public void setDatabases(List<String> databases) {
		this.databases = databases;
	}

	public List<String> getTodos() {
		return todos;
	}

	public void setTodos(List<String> todos) {
		this.todos = todos;
	}

	@Override
	public String toString() {
		return "Configuration [released=" + released + ", version=" + version + ", active=" + active + ", databases="
				+ databases + ", todos=" + todos + "]";
	}

	
}