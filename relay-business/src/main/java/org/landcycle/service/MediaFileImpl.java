package org.landcycle.service;

import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;

import javax.annotation.Resource;
import javax.imageio.ImageIO;

import org.apache.log4j.Logger;
import org.landcycle.media.ImgScalrResizeImpl;
import org.landcycle.media.ResizeDimensionEnum;
import org.landcycle.repository.TaggableEntity;
import org.landcycle.repository.TaggableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("mediaFile")
public class MediaFileImpl implements MediaFile {

	@Resource(name = "sizeImage")
	HashMap<String, String> sizeImage;

	protected static final Logger log = Logger.getLogger(MediaFileImpl.class.getName());
	@Autowired
	org.landcycle.util.ConfigurationLoader configurationLoader;

	@Autowired
	TaggableRepository taggableRepository;

	// 1280
	// 320
	@Override
	public void writeImage(byte[] stream, String path, String imgType, int size) throws Exception {
		BufferedImage img = new ImgScalrResizeImpl().resize(stream, size, ResizeDimensionEnum.FIT_TO_WIDTH);
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(path);
			
			ImageIO.write(img, imgType, out);
		} finally {
			if (out != null) {
				out.flush();
				out.close();
			}
		}
	}

	@Override
	public void writeFile(byte[] stream, String path, String imgType) throws IOException, Exception {
		// *************************************************************************
		// Inizializzo le variabili
		// *************************************************************************
		OutputStream fos = null;
		BufferedOutputStream bos = null;
		// BufferedImage image = null;
		try {
			// image = ImageIO.read(new ByteArrayInputStream(stream));
			// *************************************************************************
			// Converto la string base64 in byte
			// *************************************************************************
			// byte[] bstream = stream;
			// *************************************************************************
			// Scrivo il file
			// *************************************************************************
			// File f = new File(path);
			fos = new FileOutputStream(path);
			bos = new BufferedOutputStream(fos);
			bos.write(stream);
			// change permission
			// int esito = chmod(path, 666);
			// ImageIO.write(image, imgType, f);
			// log.debug("Esito change permission {} ", esito);
		} catch (IOException e) {
			log.error("Errore nello scrivere il file", e);
			throw e;
		} finally {
			// *************************************************************************
			// Chiudo gli stream
			// *************************************************************************
			if (bos != null) {
				bos.flush();
				bos.close();
			}
			if (fos != null) {
				fos.flush();
				fos.close();
			}
			// if(image!= null){
			// image.flush();
			// }
		}
	}

	/**
	 * Read file.
	 * 
	 * @param file
	 *            the file
	 * @return the byte[]
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unused")
	@Override
	public byte[] readFile(File file) throws IOException, Exception {
		// *************************************************************************
		// Inizializzo le variabili
		// *************************************************************************
		InputStream is = null;
		byte[] bytes;
		try {
			// *************************************************************************
			// Leggo il file
			// *************************************************************************
			is = new FileInputStream(file);
			long length = file.length();
			if (length > Integer.MAX_VALUE) {
				// File is too large
			}
			bytes = new byte[(int) length];
			// *************************************************************************
			// Verifica
			// *************************************************************************
			int offset = 0;
			int numRead = 0;
			while (offset < bytes.length && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
				offset += numRead;
			}

			if (offset < bytes.length) {
				throw new IOException("Could not completely read file " + file.getName());
			}
		} finally {
			if (is != null)
				is.close();
		}
		// *************************************************************************
		// response
		// *************************************************************************
		return bytes;
	}

	@Override
	public String getDirUpload() throws Exception {

		return configurationLoader.getBaseImg();
	}

	@Override
	public String getUrlImage() throws Exception {

		return configurationLoader.getUrlImg();
	}

	@Override
	public byte[] readFile(String id, String filter) throws IOException, Exception {
		// *************************************************************************
		// Inizializzo le variabili
		// *************************************************************************
		InputStream is = null;
		byte[] bytes;
		try {
			TaggableEntity ent = taggableRepository.findOne(id);
			if (ent == null)
				return null;
			String pathFilter = getDirUpload() + filter + "/" + id + ent.getImg().substring(ent.getImg().lastIndexOf("."));
			String path = getDirUpload() + id + ent.getImg().substring(ent.getImg().lastIndexOf("."));

			// *************************************************************************
			// Leggo il file
			// *************************************************************************
			File file = new File(pathFilter);
			if (!file.exists() && isImage(ent.getImg().substring(ent.getImg().lastIndexOf(".") + 1))) {
				try {
					File fileTmp = new File(path);
					byte[] b = readFile(fileTmp);
					writeImage(b, pathFilter, ent.getImg().substring(ent.getImg().lastIndexOf(".") + 1), Integer.parseInt(sizeImage.get(filter)));
//					writeFile(b, pathFilter, ent.getType());
				} catch (Exception e) {
					log.warn(e);
					is = new FileInputStream(path);
				}
				// lo scrivo
			}
			is = new FileInputStream(pathFilter);
			long length = file.length();
			if (length > Integer.MAX_VALUE) {
				// File is too large
			}
			bytes = new byte[(int) length];
			// *************************************************************************
			// Verifica
			// *************************************************************************
			int offset = 0;
			int numRead = 0;
			while (offset < bytes.length && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
				offset += numRead;
			}

			if (offset < bytes.length) {
				throw new IOException("Could not completely read file " + file.getName());
			}
		} finally {
			if (is != null)
				is.close();
		}
		// *************************************************************************
		// response
		// *************************************************************************
		return bytes;
	}

	private boolean isImage(String ctype) throws Exception {
		if (ctype == null || (ctype != null && ctype.isEmpty()))
			throw new Exception("Formato non consentito");
		if ((!ctype.equals("png") && !ctype.equals("jpeg") && !ctype.equals("gif") && !ctype.equals("jpg") && !ctype.equals("pjpeg"))) {
			return false;
		}
		return true;
	}
}
