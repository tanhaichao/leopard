package io.leopard.test.mock;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockMultipartHttpServletRequest;

public class MockFileRequest extends MockMultipartHttpServletRequest {

	
	public MockFileRequest() {
		this(null);
	}

	public MockFileRequest(String filename) {
		super.setMethod("POST");
		if (StringUtils.isNotEmpty(filename)) {
			byte[] data;
			try {
				data = FileUtils.readFileToByteArray(new File(filename));
			}
			catch (IOException e) {
				throw new RuntimeException(e.getMessage(), e);
			}
			MockMultipartFile file = new MockMultipartFile("file", data);
			super.addFile(file);
		}
	}
}
