package org.landcycle.json;

import java.io.IOException;
import java.math.BigDecimal;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;
import org.landcycle.utils.CommonUtils;

/**
 * Serializzatore Jackson per i BigDecimal: per la conversione fare riferimento a {@link CommonUtils#currencyToString(BigDecimal)}
 * 
 * @author gmicali
 * 
 */
public class JsonBigDecimalSerializer extends JsonSerializer<BigDecimal> {

	@Override
	public void serialize(BigDecimal value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonGenerationException {
		String text = CommonUtils.currencyToString(value);
		jgen.writeRawValue(text);
	}

	@Override
	public Class<BigDecimal> handledType() {
		return BigDecimal.class;
	}

}
