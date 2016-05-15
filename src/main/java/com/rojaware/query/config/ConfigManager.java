/**
 *
 */
package com.rojaware.query.config;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.emitter.ScalarAnalysis;

/**
 * Singleton class to provide static configuration values by keyword
 *
 * @author LEES4
 *
 */
public class ConfigManager {
	private static final String CONFIG_FILE_NAME = "config.yml";
	static private ConfigManager _instance = null;
	static private Configuration config;
	private static Logger LOG = Logger.getLogger(ConfigManager.class);

	public Configuration getConfig() {
		return config;
	}

	protected ConfigManager() {
		init(CONFIG_FILE_NAME);
	}

	public Object load(String fileName) {
		InputStream ios = IOUtils.toInputStream(fileName);
		Yaml yaml = new Yaml();
		Object data = yaml.load(ios);
		return data;
	}

	public void init(String fileName) {
		Yaml yaml = new Yaml();

		// load as any object
		ClassLoader classLoader = getClass().getClassLoader();
		try {
			String doc = IOUtils.toString(classLoader.getResourceAsStream(fileName));

			this.config = yaml.loadAs(doc, Configuration.class);
//			LOG.info(config.toString());

		} catch (IOException e) {
			LOG.error("Config load failed :: ", e);
		}

	}

	static public ConfigManager instance() {
		if (_instance == null) {
			_instance = new ConfigManager();
		}
		return _instance;
	}


	public void dump() {
		Yaml yaml = new Yaml();

		// load as any object
		ClassLoader classLoader = getClass().getClassLoader();
		try {
			String doc = IOUtils.toString(classLoader.getResourceAsStream(CONFIG_FILE_NAME));
			Map<String, Object> result = (Map<String, Object>) yaml.load(doc);
			System.out.println(result.toString());
		} catch (IOException e) {
			LOG.error("Config load failed :: ", e);
		}
	}
	
	public void test() {

		List<String> dbs = config.getDatabases();
		System.out.println(" dbs --> " + dbs.toString());
		
	}
	/**
	 * Saves the configuration.
	 */
	public void save() {
		Yaml yaml = new Yaml();

		try {
			yaml.dump(config, new PrintWriter(new File(CONFIG_FILE_NAME)));
		} catch (FileNotFoundException e) {
			LOG.error( "Unable to save configuration.", e);
		}
	}
	
	
	
}
