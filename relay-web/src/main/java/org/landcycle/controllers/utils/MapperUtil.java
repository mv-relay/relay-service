package org.landcycle.controllers.utils;

import org.codehaus.jackson.Version;
import org.codehaus.jackson.map.Module;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.codehaus.jackson.map.module.SimpleModule;
import org.landcycle.json.JsonBigDecimalSerializer;

public class MapperUtil extends ObjectMapper{

	public MapperUtil() {
		super();
		m_configure_defaults();
		Module mod = m_configure_modules();
		this.registerModule(mod);
	}
	/**
	 * imposta i defaults in modo tale che non vengano stampate le properties a null;
	 */
	private void m_configure_defaults() {
		setSerializationInclusion(Inclusion.NON_NULL);
		configure(SerializationConfig.Feature.WRITE_NULL_MAP_VALUES, false);
		configure(SerializationConfig.Feature.WRITE_DATES_AS_TIMESTAMPS, false);
		configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS, false);
		SerializationConfig serConfig = getSerializationConfig();
		//serConfig.setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);

	}

	/**
	 * configura serializzatori/deserializzatori custom
	 * 
	 * @return un modulo configurato;
	 */
	private SimpleModule m_configure_modules() {
		SimpleModule mod = new SimpleModule("AsiaMappers", Version.unknownVersion());
		mod.addSerializer(new JsonBigDecimalSerializer());
		return mod;
	}
}
