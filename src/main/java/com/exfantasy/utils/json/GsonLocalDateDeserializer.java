package com.exfantasy.utils.json;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

public class GsonLocalDateDeserializer implements JsonDeserializer<LocalDate> {

	@Override
	public LocalDate deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
			throws JsonParseException {
		String strDate = json.getAsJsonPrimitive().getAsString();
		Date date = new Date(Long.parseLong(strDate));
		LocalDate lDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return lDate;
	}

}
