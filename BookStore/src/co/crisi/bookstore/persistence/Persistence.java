package co.crisi.bookstore.persistence;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Persistence {
	public static final String PATH_BOOKSTORE = "persistenceData/BookStore.dat";

	public static void serializeObject(Object object) throws IOException {
		FileOutputStream file = new FileOutputStream(PATH_BOOKSTORE);
		ObjectOutputStream out = new ObjectOutputStream(file);
		out.writeObject(object);
		out.flush();
		out.close();
	}

	public static Object readObject() throws IOException, ClassNotFoundException {
		FileInputStream file = new FileInputStream(PATH_BOOKSTORE);
		ObjectInputStream in = new ObjectInputStream(file);
		Object object = in.readObject();
		in.close();
		return object;
	}

	public static void serializeXML(Object object) throws IOException {
		FileOutputStream file = new FileOutputStream(PATH_BOOKSTORE);
		XMLEncoder coder = new XMLEncoder(file);
		coder.writeObject(coder);
		coder.flush();
		coder.close();
		file.close();
	}

	public static Object readObjectXML() throws IOException {
		FileInputStream file = new FileInputStream(PATH_BOOKSTORE);
		XMLDecoder decoder = new XMLDecoder(file);
		Object object = decoder.readObject();
		decoder.close();
		file.close();
		return object;
	}

}
