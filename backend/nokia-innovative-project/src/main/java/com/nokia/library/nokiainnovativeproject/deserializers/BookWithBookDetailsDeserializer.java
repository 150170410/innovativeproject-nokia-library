package com.nokia.library.nokiainnovativeproject.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.nokia.library.nokiainnovativeproject.entities.BookDetails;

import java.io.IOException;

public class BookWithBookDetailsDeserializer extends StdDeserializer<BookDetails> {
	public BookWithBookDetailsDeserializer() {
		this(null);
	}

	public BookWithBookDetailsDeserializer(Class<?> vc) {
		super(vc);
	}

	@Override
	public BookDetails deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
		return null;
	}
}
