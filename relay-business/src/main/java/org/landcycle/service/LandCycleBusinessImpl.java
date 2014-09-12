package org.landcycle.service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.landcycle.api.LikeItem;
import org.landcycle.api.Position;
import org.landcycle.api.Taggable;
import org.landcycle.api.User;
import org.landcycle.api.UserItem;
import org.landcycle.repository.LikeEntity;
import org.landcycle.repository.LikeRepository;
import org.landcycle.repository.TaggableEntity;
import org.landcycle.repository.UserEntity;
import org.landcycle.repository.UserRepository;
import org.landcycle.utils.CommonUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Component("landCycleBusiness")
public class LandCycleBusinessImpl implements LandCycleBusiness {

	protected static final Logger log = Logger.getLogger(LandCycleBusinessImpl.class.getName());

	@Autowired
	org.landcycle.util.ConfigurationLoader configurationLoader;

	@Autowired
	UserRepository userRepository;
	@Autowired
	LikeRepository likeRepository;
//	@Autowired
//	ForSaleDao forSaleDao;

	@Override
	public UserItem saveOrUpdateTaggable(UserItem upload) throws Exception {
		// TODO Auto-generated method stub
		log.debug("Input save or update : " + CommonUtils.bean2string(upload));
		if (upload.getTaggable() != null) {
			UserEntity uu = new UserEntity();
			uu.setMail(upload.getUser().getMail());
			Taggable taggable = upload.getTaggable();
			TaggableEntity taggableEntity = new TaggableEntity();
			String id = upload.getTaggable().getId();
			taggableEntity.setImg(id);
			taggableEntity.setId(id);
			taggableEntity.setUser(upload.getUser().getMail());
//			forSaleEntity.setMailacq(upload.getForSale().getMailAcq());
			BeanUtils.copyProperties(taggable, taggableEntity, new String[] { "id" });
			Position positio = upload.getTaggable().getPosition();
			if (positio != null) {
				taggableEntity.setLat(positio.getLat());
				taggableEntity.setLng(positio.getLng());
			}
			taggableEntity.setImg(getUrlImage() + id + "." + taggable.getImageType());
			Set<TaggableEntity> tmp = new HashSet<TaggableEntity>();
			tmp.add(taggableEntity);
			uu.setTaggable(tmp);
			log.debug("User insert : " + CommonUtils.bean2string(uu));
//			forSaleDao.save(forSaleEntity);
			userRepository.save(uu);
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
		TaggableEntity forSaleEntity = new TaggableEntity();
		if (user.getTaggable() != null && user.getTaggable().getPosition() != null) {
			Position positio = user.getTaggable().getPosition();
			forSaleEntity.setLat(positio.getLat());
			forSaleEntity.setLng(positio.getLng());
		}
		if (user.getUser() != null){
			forSaleEntity.setUser(user.getUser().getMail());
		}
		log.debug("Entity dao request : " + CommonUtils.bean2string(forSaleEntity));
		Set<UserEntity> resp = userRepository.findByQuery(forSaleEntity.getLng(),forSaleEntity.getLat(),forSaleEntity.getUser());
		List<UserItem> response = new ArrayList<UserItem>();
		for (UserEntity userEntity : resp) {
			UserItem tmp = new UserItem();
			User u = new User();
			u.setMail(userEntity.getMail());
			u.setFirstName(userEntity.getNome());
			u.setSecondName(userEntity.getCognome());
			Set<TaggableEntity> ee = userEntity.getTaggable();
			for (TaggableEntity forsaleEntity2 : ee) {
				Taggable ff = new Taggable();
				BeanUtils.copyProperties(forsaleEntity2,ff);
				Position pos = new Position();
				pos.setLat(forsaleEntity2.getLat());
				pos.setLng(forsaleEntity2.getLng());
				ff.setPosition(pos);
				tmp.addTag(ff);
			}
			response.add(tmp);
		}
		log.debug("List<UserEntity> resp : " + CommonUtils.bean2string(resp));

		return response;
	}

	@Override
	public UserItem upload(UserItem upload) throws Exception {
		UUID uuidFile = UUID.randomUUID();
		upload.getTaggable().setId(uuidFile.toString());
		// upload.setForSale(forSale);
		// }
		String fileName = uploadFileName(upload.getTaggable().getImageType(), upload.getTaggable().getId(),
				getDirUpload());
		log.debug("##############fileName : " + fileName);
		writeFile(upload.getTaggable().getStreams(), fileName);
		// clean output
		upload.getTaggable().setStreams(null);
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

	@Override
	public LikeItem saveLike(LikeItem like) throws Exception {
		// TODO Auto-generated method stub
		LikeEntity l = new LikeEntity();
		l.setId(like.getId());
		l.setUser(like.getUser());
		LikeEntity  likez = likeRepository.save(l);
		return like;
	}
}
