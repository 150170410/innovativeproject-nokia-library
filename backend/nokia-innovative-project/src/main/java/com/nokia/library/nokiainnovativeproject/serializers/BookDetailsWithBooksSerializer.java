package com.nokia.library.nokiainnovativeproject.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.nokia.library.nokiainnovativeproject.entities.Book;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class BookDetailsWithBooksSerializer extends StdSerializer<List<Book>> {

	public BookDetailsWithBooksSerializer() {
		this(null);
	}

	public BookDetailsWithBooksSerializer(Class<List<Book>> t) {
		super(t);
	}

	@Override
	public void serialize(List<Book> books, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
		List<Book> buks = new ArrayList<>();
		for (Book book : books) {
			book.setBookDetails(null);
			buks.add(book);
		}
		jsonGenerator.writeObject(buks);
	}
}
