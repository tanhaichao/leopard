package io.leopard.test.mock;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;

import org.springframework.mock.web.MockMultipartFile;

/**
 * 本机文件.
 * 
 * @author ahai
 *
 */
public class LocalMultipartFile extends MockMultipartFile {

	public LocalMultipartFile(String path) throws IOException {
		super(path, readFile(path));
		File file = new File(path);
		this.setOriginalFilename(file.getName());
	}

	public void setOriginalFilename(String originalFilename) {
		try {
			Field field = MockMultipartFile.class.getDeclaredField("originalFilename");
			field.setAccessible(true);
			field.set(this, originalFilename);
		}
		catch (Exception e) {

		}
	}

	protected static InputStream readFile(String path) throws FileNotFoundException {
		File file = new File(path);
		// System.err.println("file:" + file.exists());
		return new FileInputStream(file);
	}
}
