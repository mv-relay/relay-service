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

	@Resource
	org.landcycle.util.ConfigurationLoader configurationLoader;

	@Resource
	UserDao userDao;
	@Resource
	ForSaleDao forSaleDao;

	@Override
	public UserItem saveOrUpdateSale(UserItem upload) throws Exception {
		// TODO Auto-generated method stub

		if (upload.getForSale() != null
				&& upload.getForSale().getStream() != null
				&& upload.getForSale().getStream().getBytes().length > 0) {
			// final byte[] stream = upload.getForSale().getStream().getBytes();

		}
		// UserEntity used = new UserEntity();
		// BeanUtils.copyProperties(upload, used);
		// userDao.save(used);
		if (upload.getForSale() != null) {
			ForSale sale = upload.getForSale();
			ForSaleEntity forSaleEntity = new ForSaleEntity();
			String id = upload.getForSale().getId();
			forSaleEntity.setImg(id);
			forSaleEntity.setId(id);
			forSaleEntity.setMailvend(upload.getUser().getMail());
			BeanUtils
					.copyProperties(sale, forSaleEntity, new String[] { "id" });
			Position positio = upload.getForSale().getPosition();
			if (positio != null) {
				forSaleEntity.setLat(positio.getLat());
				forSaleEntity.setLng(positio.getLng());
			}
			forSaleEntity
					.setImg(getUrlImage() + id + "." + sale.getImageType());
			forSaleDao.save(forSaleEntity);
		}
		return upload;
	}

	private void writeFile(String stream, String path) throws IOException,
			Exception {
		// *************************************************************************
		// Inizializzo le variabili
		// *************************************************************************
		OutputStream fos = null;
		BufferedOutputStream bos = null;
		try {
			// *************************************************************************
			// Converto la string base64 in byte
			// *************************************************************************
			byte[] bstream = stream.getBytes();
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
			// log.error("Errore nello scrivere il file", e);
			e.printStackTrace();
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

	private void writeFile(byte[] stream, String path) throws IOException,
			Exception {
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
			// log.error("Errore nello scrivere il file", e);
			e.printStackTrace();
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
				// TODO
			}
			bytes = new byte[(int) length];
			// *************************************************************************
			// Verifica
			// *************************************************************************
			int offset = 0;
			int numRead = 0;
			while (offset < bytes.length
					&& (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
				offset += numRead;
			}

			if (offset < bytes.length) {
				throw new IOException("Could not completely read file "
						+ file.getName());
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
		if (user.getForSale() != null
				&& user.getForSale().getPosition() != null) {
			Position positio = user.getForSale().getPosition();
			forSaleEntity.setLat(positio.getLat());
			forSaleEntity.setLng(positio.getLng());
		}
		// List<UserEntity> users = userDao.findAll();

		List<ForSaleEntity> resp = forSaleDao.findByQuery(forSaleEntity);
		List<UserItem> response = new ArrayList<UserItem>();
		System.out.println("List<ForSaleEntity> resp : "
				+ CommonUtils.bean2string(resp));
		// String tmpmail = null;
		// List<ForSale> tmpItems = new ArrayList<ForSale>();
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
				// tmpFor.setImg(getUrlImage()+"/"+tmpFor.getId());
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
				System.out.println("USER : " + CommonUtils.bean2string(ent));
				BeanUtils.copyProperties(ent, tmpuser);
				tmpItem.setUser(tmpuser);
				System.out.println("tmpItem : "
						+ CommonUtils.bean2string(tmpItem));
				ForSale tmpFor = new ForSale();
				BeanUtils.copyProperties(tmp, tmpFor);
				// tmpFor.setImg(getUrlImage()+"/"+tmpFor.getId());
				respSales.add(tmpFor);
				Position pos = new Position();
				pos.setLat(tmp.getLat());
				pos.setLng(tmp.getLng());
				tmpFor.setPosition(pos);
				tmpItem.setForSales(respSales);

				// tmpmail = tmp.getMailvend();
				response.add(tmpItem);
			}

		}

		return response;
	}

	// private void checkLastRecord(TimelineItem[] items, boolean paging, int
	// rowPage, List<TimelineItem> tmpItems, TimelineItem prevItem, int i) {
	// if (i == (items.length - 1) || (rowPage == PAGE_RECORDS && !paging)) {
	// if (checkAggregate(prevItem)) {
	// prevItem.setCount(0);
	// tmpItems.add(prevItem);
	// } else {
	// mergeDescription(items[i]);
	// tmpItems.add(items[i]);
	// }
	//
	// }
	// }
	@Override
	public UserItem upload(UserItem upload) throws Exception {
		// TODO Auto-generated method stub
		UUID uuidFile = UUID.randomUUID();
		// if(upload.getForSale() == null){
		// ForSale forSale = new ForSale();
		upload.getForSale().setId(uuidFile.toString());
		// upload.setForSale(forSale);
		// }
		String fileName = uploadFileName(upload.getForSale().getImageType(),
				upload.getForSale().getId(), getDirUpload());
		System.out.println("##############fileName : " + fileName);
		writeFile(upload.getForSale().getStreams(), fileName);
		upload.getForSale().setStream(null);
		return upload;
	}

	private String uploadFileName(String type, String idFile, String path)
			throws Exception {

		// String path = getEnvEntryString(MediaConstants.DOQ_PATH);
		// if (path == null || (path != null && path.length() == 0)) {
		// throw new DevelopmentException("Path di upload non configurato");
		// }
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
