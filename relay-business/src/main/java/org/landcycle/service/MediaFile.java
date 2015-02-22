package org.landcycle.service;

import java.io.File;
import java.io.IOException;

public interface MediaFile {

	String getUrlImage() throws Exception;

	String getDirUpload() throws Exception;

	byte[] readFile(File file) throws IOException, Exception;

	byte[] readFile(String id, String filter) throws IOException, Exception;

	void writeFile(byte[] stream, String path, String imgType) throws IOException, Exception;

	void writeImage(byte[] stream, String path, String imgType, int size) throws Exception;

}
