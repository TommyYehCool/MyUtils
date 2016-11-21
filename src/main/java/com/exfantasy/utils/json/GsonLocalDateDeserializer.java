package com.exfantasy.utils.json;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

public class GsonLocalDateDeserializer implements JsonDeserializer<LocalDate> {
	
	private final SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Override
	public LocalDate deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
			throws JsonParseException {
		String strDate = json.getAsJsonPrimitive().getAsString();
		try {
			Date date = dateTimeFormat.parse(strDate);
			LocalDate lDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	        return lDate;
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

}
