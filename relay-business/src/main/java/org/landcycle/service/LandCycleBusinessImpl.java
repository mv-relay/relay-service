package org.landcycle.service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.landcycle.api.ForSale;
import org.landcycle.api.Position;
import org.landcycle.api.User;
import org.landcycle.api.UserItem;
import org.landcycle.dao.ForSaleDao;
import org.landcycle.dao.ForSaleEntity;
import org.landcycle.dao.UserDao;
import org.landcycle.dao.UserEntity;
import org.landcycle.utils.CommonUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Component("landCycleBusiness")
public class LandCycleBusinessImpl implements LandCycleBusiness {

	protected static final Logger log = Logger.getLogger(LandCycleBusinessImpl.class.getName());

	@Resource
	org.landcycle.util.ConfigurationLoader configurationLoader;

	@Resource
	UserDao userDao;
	@Resource
	ForSaleDao forSaleDao;

	@Override
	public UserItem saveOrUpdateSale(UserItem upload) throws Exception {
		// TODO Auto-generated method stub
		log.debug("Input save or update : " + CommonUtils.bean2string(upload));
		if (upload.getForSale() != null) {
			ForSale sale = upload.getForSale();
			ForSaleEntity forSaleEntity = new ForSaleEntity();
			String id = upload.getForSale().getId();
			forSaleEntity.setImg(id);
			forSaleEntity.setId(id);
			forSaleEntity.setMailvend(upload.getUser().getMail());
			forSaleEntity.setMailacq(upload.getForSale().getMailAcq());
			BeanUtils.copyProperties(sale, forSaleEntity, new String[] { "id" });
			Position positio = upload.getForSale().getPosition();
			if (positio != null) {
				forSaleEntity.setLat(positio.getLat());
				forSaleEntity.setLng(positio.getLng());
			}
			forSaleEntity.setImg(getUrlImage() + id + "." + sale.getImageType());
			forSaleDao.save(forSaleEntity);
		}
		return upload;
	}

	private void writeFile(byte[] stream, String path) throws IOException, Exception {
		// *************************************************************************
		// Inizializzo le variabili
		// *************************************************************************
		OutputStream fos = null;
		BufferedOutputStream bos = null;
		try {
			// *************************************************************************
			// Converto la string base64 in byte
			// *************************************************************************
			byte[] bstream = stream;
			// *************************************************************************
			// Scrivo il file
			// *************************************************************************
			fos = new FileOutputStream(path);
			bos = new BufferedOutputStream(fos);
			bos.write(bstream);
			// change permission
			// int esito = chmod(path, 666);

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
	private byte[] readFile(File file) throws IOException, Exception {
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

	private String getDirUpload() throws Exception {

		return configurationLoader.getBaseImg();
	}

	private String getUrlImage() throws Exception {

		return configurationLoader.getUrlImg();
	}

	@Override
	public List<UserItem> find(UserItem user) throws Exception {
		// TODO Auto-generated method stub
		ForSaleEntity forSaleEntity = new ForSaleEntity();
		if (user.getForSale() != null && user.getForSale().getPosition() != null) {
			Position positio = user.getForSale().getPosition();
			forSaleEntity.setLat(positio.getLat());
			forSaleEntity.setLng(positio.getLng());
		}
		if (user.getUser() != null)
			forSaleEntity.setMailvend(user.getUser().getMail());
		log.debug("Entity dao request : " + CommonUtils.bean2string(forSaleEntity));
		List<ForSaleEntity> resp = forSaleDao.findByQuery(forSaleEntity);
		List<UserItem> response = new ArrayList<UserItem>();
		log.debug("List<ForSaleEntity> resp : " + CommonUtils.bean2string(resp));
		List<ForSale> respSales = new ArrayList<ForSale>();
		ForSaleEntity tmp = null;
		UserItem tmpItem = null;
		User tmpuser = null;
		for (int i = 0; i < resp.size(); i++) {
			if (tmp == null) {
				tmp = resp.get(i);
				continue;
			}

			if (!tmp.getMailvend().equals(resp.get(i).getMailvend())) {
				tmpItem = new UserItem();
				tmpuser = new User();
				UserEntity ent = userDao.findOne(tmp.getMailvend());
				BeanUtils.copyProperties(ent, tmpuser);
				tmpItem.setUser(tmpuser);
				tmpItem.setForSales(respSales);
				respSales = new ArrayList<ForSale>();
				// tmpmail = tmp.getMailvend();
				response.add(tmpItem);
				tmp = resp.get(i);
			} else {
				ForSale tmpFor = new ForSale();
				BeanUtils.copyProperties(tmp, tmpFor);
				respSales.add(tmpFor);
				Position pos = new Position();
				pos.setLat(tmp.getLat());
				pos.setLng(tmp.getLng());
				tmpFor.setPosition(pos);
				tmp = resp.get(i);
			}
			// // ultimo giro
			if (i == (resp.size() - 1)) {
				tmpItem = new UserItem();
				tmpuser = new User();
				UserEntity ent = userDao.findOne(tmp.getMailvend());
				log.debug("USER : " + CommonUtils.bean2string(ent));
				BeanUtils.copyProperties(ent, tmpuser);
				tmpItem.setUser(tmpuser);
				log.debug("tmpItem : " + CommonUtils.bean2string(tmpItem));
				ForSale tmpFor = new ForSale();
				BeanUtils.copyProperties(tmp, tmpFor);
				respSales.add(tmpFor);
				Position pos = new Position();
				pos.setLat(tmp.getLat());
				pos.setLng(tmp.getLng());
				tmpFor.setPosition(pos);
				tmpItem.setForSales(respSales);
				response.add(tmpItem);
			}
		}
		return response;
	}

	@Override
	public UserItem upload(UserItem upload) throws Exception {
		UUID uuidFile = UUID.randomUUID();
		upload.getForSale().setId(uuidFile.toString());
		// upload.setForSale(forSale);
		// }
		String fileName = uploadFileName(upload.getForSale().getImageType(), upload.getForSale().getId(),
				getDirUpload());
		log.debug("##############fileName : " + fileName);
		writeFile(upload.getForSale().getStreams(), fileName);
		// clean output
		upload.getForSale().setStreams(null);
		return upload;
	}

	private String uploadFileName(String type, String idFile, String path) throws Exception {
		// //
		// *************************************************************************
		// // Creo in nome del file
		// //
		// *************************************************************************
		StringBuilder fileName = new StringBuilder(path);

		fileName.append(idFile).append(".").append(type);
		return fileName.toString();
	}
}
