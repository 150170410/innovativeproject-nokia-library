package com.nokia.library.nokiainnovativeproject.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.nokia.library.nokiainnovativeproject.entities.BookDetails;

import java.io.IOException;
import java.util.ArrayList;

public class BookWithBookDetailsSerializer extends StdSerializer<BookDetails> {
	public BookWithBookDetailsSerializer() {
		this(null);
	}

	public BookWithBookDetailsSerializer(Class<BookDetails> t) {
		super(t);
	}

	@Override
	public void serialize(BookDetails bookDetails, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
		bookDetails.setBooks(new ArrayList<>());
		System.out.println(bookDetails);
		jsonGenerator.writeObject(bookDetails);
	}
}
