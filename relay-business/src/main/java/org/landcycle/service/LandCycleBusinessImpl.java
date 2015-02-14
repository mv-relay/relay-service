package org.landcycle.service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.landcycle.api.CommentItem;
import org.landcycle.api.LikeItem;
import org.landcycle.api.MediaItem;
import org.landcycle.api.Position;
import org.landcycle.api.RouteItem;
import org.landcycle.api.RouteTagItem;
import org.landcycle.api.TaggableBaseItem;
import org.landcycle.api.TaggableItem;
import org.landcycle.api.User;
import org.landcycle.api.UserItem;
import org.landcycle.repository.CommentEntity;
import org.landcycle.repository.CommentRepository;
import org.landcycle.repository.ConfigEntity;
import org.landcycle.repository.ConfigRepository;
import org.landcycle.repository.LikeEntity;
import org.landcycle.repository.LikeRepository;
import org.landcycle.repository.LikesKey;
import org.landcycle.repository.MediaEntity;
import org.landcycle.repository.MediaRepository;
import org.landcycle.repository.RouteEntity;
import org.landcycle.repository.RouteRepository;
import org.landcycle.repository.RouteTagEntity;
import org.landcycle.repository.TagEntity;
import org.landcycle.repository.TaggableEntity;
import org.landcycle.repository.TaggableRepository;
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
	TaggableRepository taggableRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	LikeRepository likeRepository;
	@Autowired
	CommentRepository commentRepository;
	@Autowired
	ConfigRepository configRepository;
	@Autowired
	MediaRepository mediaRepository;
	@Autowired
	RouteRepository routeRepository;

	@Override
	public UserItem saveOrUpdateTaggable(UserItem upload) throws Exception {
		// TODO Auto-generated method stub
		log.debug("Input save or update : " + CommonUtils.bean2string(upload));
		if (upload.getTaggable() != null) {

			UserEntity uu = new UserEntity();
			uu.setMail(upload.getUser().getMail());
			TaggableItem taggable = upload.getTaggable();
			TaggableEntity taggableEntity = new TaggableEntity();
			String id = upload.getTaggable().getId();
			if (upload.getTaggable().getTags() != null && upload.getTaggable().getTags().length > 0) {
				List<TagEntity> tmpTags = new ArrayList<TagEntity>();
				for (int i = 0; i < upload.getTaggable().getTags().length; i++) {
					TagEntity tag = new TagEntity(id, upload.getTaggable().getTags()[i]);
					tmpTags.add(tag);
				}
				taggableEntity.setTags(tmpTags);
			}
			taggableEntity.setImg(id);
			taggableEntity.setId(id);
			taggableEntity.setUser(upload.getUser().getMail());
			BeanUtils.copyProperties(taggable, taggableEntity, new String[] { "id", "tags", "medias" });
			Position positio = upload.getTaggable().getPosition();
			if (positio != null) {
				taggableEntity.setLat(positio.getLat());
				taggableEntity.setLng(positio.getLng());
			}
			taggableEntity.setImg(getUrlImage() + id + "." + taggable.getImageType());
			taggable.setImg(getUrlImage() + id + "." + taggable.getImageType());
			Set<TaggableEntity> tmp = new HashSet<TaggableEntity>();

			tmp.add(taggableEntity);
			uu.setTaggable(tmp);
			log.debug("User insert : " + CommonUtils.bean2string(uu));
			userRepository.save(uu);
			taggable.setStream(null);
			upload.setTaggable(taggable);
		}
		return upload;
	}

	@Override
	public MediaItem saveOrUpdateMedia(MediaItem media) throws Exception {

		MediaEntity me = new MediaEntity();
		me.setIdTaggable(media.getId());
		me.setName(media.getName());
		me.setPath(getUrlImage() + media.getId() + "." + media.getType());
		me.setType(media.getType());
		mediaRepository.save(me);
		media.setStream(null);

		return media;
	}

	private void writeFile(byte[] stream, String path, String imgType) throws IOException, Exception {
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
	public List<TaggableItem> find(TaggableItem taggable) throws Exception {
		List<TaggableEntity> taggables = null;

		TaggableEntity forSaleEntity = new TaggableEntity();

		Position positio = taggable.getPosition();
		forSaleEntity.setLat(positio.getLat());
		forSaleEntity.setLng(positio.getLng());
		log.debug("Entity dao request : " + CommonUtils.bean2string(forSaleEntity));
		taggables = taggableRepository.findByQuery(forSaleEntity.getLng(), forSaleEntity.getLat());
		log.debug("Entity dao response : " + CommonUtils.bean2string(taggables));
		List<TaggableItem> response = new ArrayList<TaggableItem>();
		for (TaggableEntity tagEntity : taggables) {

			TaggableItem ff = new TaggableItem();
			BeanUtils.copyProperties(tagEntity, ff);
			Position pos = new Position();
			pos.setLat(tagEntity.getLat());
			pos.setLng(tagEntity.getLng());
			ff.setPosition(pos);

			if (tagEntity.getMedia() != null && tagEntity.getMedia().size() > 0) {
				int i = 0;
				List<MediaEntity> media = tagEntity.getMedia();
				MediaItem[] mediaRespone = new MediaItem[tagEntity.getMedia().size()];
				for (MediaEntity mediaEntity : media) {

					MediaItem ttmp = new MediaItem();
					BeanUtils.copyProperties(mediaEntity, ttmp);
					mediaRespone[i] = ttmp;
					i++;
				}
				ff.setMedias(mediaRespone);
			}
			if (tagEntity.getLikes() != null && tagEntity.getLikes().size() > 0) {
				log.debug("compute likes");
				List<LikeEntity> likeEntity = tagEntity.getLikes();
				LikeItem likeResponse = new LikeItem();
				likeResponse.setCount(tagEntity.getLikes().size());
				likeResponse.setMyLike(computeUserLike(likeEntity, taggable));

				ff.setLikes(likeResponse);
			}

			response.add(ff);
		}

		return response;
	}

	private boolean computeUserLike(List<LikeEntity> likeEntity, TaggableItem taggable) throws Exception {
		if (taggable.getUser() != null) {
			User user = taggable.getUser();
			for (LikeEntity likeEnt : likeEntity) {
				log.debug("Likes key : " + CommonUtils.bean2string(likeEnt));
				LikesKey key = likeEnt.getLikesKey();
				if (key != null && key.getUser() != null && user.getMail().equalsIgnoreCase(key.getUser())) {
					return true;
				}
			}
		}
		return false;
	}

	private RouteItem computeTagRoute(List<String> idTaggableExt, RouteItem routeResponse) throws Exception {
		// TODO Auto-generated method stub
		List<TaggableEntity> response = taggableRepository.findByIdIn(idTaggableExt);
		log.debug("response in : " + CommonUtils.bean2string(response));
		Map<String, TaggableEntity> map = new HashMap<String, TaggableEntity>();
		for (TaggableEntity taggableEntity : response) {
			map.put(taggableEntity.getId(), taggableEntity);
		}
		RouteTagItem[] rti = routeResponse.getRoutes();
		for (int i = 0; i < rti.length; i++) {
			RouteTagItem tmp = rti[i];
			TaggableEntity tag = map.get(tmp.getIdTaggableExt());
			TaggableBaseItem baseItem = new TaggableBaseItem();
			BeanUtils.copyProperties(tag, baseItem);
			tmp.setTaggable(baseItem);
		}
		return routeResponse;
	}

	@Override
	public TaggableItem findOne(UserItem user) throws Exception {

		TaggableEntity tagEntity = taggableRepository.findOne(user.getTaggable().getId());
		TaggableItem response = new TaggableItem();
		if (tagEntity != null) {
			BeanUtils.copyProperties(tagEntity, response);
			Position pos = new Position();
			pos.setLat(tagEntity.getLat());
			pos.setLng(tagEntity.getLng());
			response.setPosition(pos);

			if (tagEntity.getRoute() != null && tagEntity.getRoute().size() > 0) {
				List<String> idTaggableExt = new ArrayList<String>();
				List<RouteEntity> route = tagEntity.getRoute();
				RouteItem routeResponse = new RouteItem();
				int j = 0;
				// è un ciclo ma un tag può avere solo un percorso ( per
				// sviluppi futuri)
				for (RouteEntity routeEntity : route) {
					BeanUtils.copyProperties(routeEntity, routeResponse);
					List<RouteTagEntity> routes = routeEntity.getRouteTag();
					RouteTagItem[] responseRouteTag = new RouteTagItem[routes.size()];
					for (RouteTagEntity routeTagEntity : routes) {
						RouteTagItem tmp = new RouteTagItem();
						BeanUtils.copyProperties(routeTagEntity, tmp);
						responseRouteTag[j] = tmp;

						idTaggableExt.add(routeTagEntity.getIdTaggableExt());
						j++;
					}
					routeResponse.setRoutes(responseRouteTag);
				}
				routeResponse = computeTagRoute(idTaggableExt, routeResponse);
				response.setRoute(routeResponse);
			}
			if (tagEntity.getComments() != null && tagEntity.getComments().size() > 0) {
				int i = 0;
				List<CommentEntity> commentsEntity = tagEntity.getComments();
				CommentItem[] commentResponse = new CommentItem[commentsEntity.size()];
				for (CommentEntity commentEntity : commentsEntity) {

					CommentItem ttmp = new CommentItem();
					BeanUtils.copyProperties(commentEntity, ttmp);
					commentResponse[i] = ttmp;
					i++;
				}
				response.setComments(commentResponse);
			}
		}

		return response;
	}

	@Override
	public List<TaggableItem> findByUser(String user) throws Exception {

		List<TaggableItem> response = new ArrayList<TaggableItem>();
		log.debug("user : " + user);
		List<TaggableEntity> userEntity = taggableRepository.findByUser(user);
		log.debug("Find by user " + CommonUtils.bean2string(userEntity));

		if (userEntity != null && userEntity.size() > 0) {
			for (TaggableEntity taggableEntity : userEntity) {
				TaggableItem tmp = new TaggableItem();
				BeanUtils.copyProperties(taggableEntity, tmp);
				response.add(tmp);
			}
		}

		return response;
	}

	@Override
	public MediaItem uploadMedia(MediaItem upload) throws Exception {
		String fileName = uploadFileName(upload.getType(), upload.getId(), getDirUpload());
		log.debug("##############fileName : " + fileName);
		writeFile(upload.getStreams(), fileName, upload.getType());
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
	public List<LikeItem> getLikes(LikeItem like) throws Exception {

		List<LikeItem> resp = new ArrayList<LikeItem>();
		List<LikeEntity> respEntity = likeRepository.findByUser(like.getUser());
		for (LikeEntity likeEntity : respEntity) {
			LikeItem tmp = new LikeItem();
			tmp.setId(likeEntity.getLikesKey().getId());
			tmp.setUser(likeEntity.getLikesKey().getUser());
			resp.add(tmp);
		}
		return resp;
		// return null;
	}

	@Override
	public List<TaggableItem> getTaggableLikes(LikeItem like) throws Exception {
		List<LikeEntity> respEntity = likeRepository.findByUser(like.getUser());
		List<String> id = new ArrayList<String>();
		for (LikeEntity likeEntity : respEntity) {
			id.add(likeEntity.getLikesKey().getId());
		}
		if (id.size() == 0)
			return null;
		List<TaggableItem> response = new ArrayList<TaggableItem>();
		List<TaggableEntity> item = taggableRepository.findByIdIn(id);
		if (item != null && item.size() > 0) {
			TaggableItem taggable = new TaggableItem();
			User u = new User();
			u.setMail(like.getUser());
			taggable.setUser(u);
			for (TaggableEntity taggableEntity : item) {
				TaggableItem tmp = new TaggableItem();
				if (taggableEntity.getLikes() != null && taggableEntity.getLikes().size() > 0) {
					// log.debug("compute likes");
					List<LikeEntity> likeEntity = taggableEntity.getLikes();
					LikeItem likeResponse = new LikeItem();
					likeResponse.setCount(taggableEntity.getLikes().size());
					likeResponse.setMyLike(computeUserLike(likeEntity, taggable));

					tmp.setLikes(likeResponse);
				}
				BeanUtils.copyProperties(taggableEntity, tmp);
				response.add(tmp);
			}
		}
		return response;
	}

	@Override
	public LikeItem saveLike(LikeItem like) throws Exception {
		// TODO Auto-generated method stub
		LikeEntity l = new LikeEntity();
		LikesKey key = new LikesKey();

		key.setId(like.getId());
		key.setUser(like.getUser());
		l.setLikesKey(key);
		likeRepository.save(l);
		return like;
	}

	@Override
	public void deleteLike(LikeItem like) throws Exception {
		LikesKey key = new LikesKey();
		LikeEntity l = new LikeEntity();
		key.setId(like.getId());
		key.setUser(like.getUser());
		l.setLikesKey(key);
		likeRepository.delete(l);
	}

	@Override
	public CommentItem saveComment(CommentItem comment) throws Exception {
		// TODO Auto-generated method stub
		CommentEntity l = new CommentEntity();
		l.setId(comment.getId());
		l.setUser(comment.getUser());
		l.setComment(comment.getComment());
		commentRepository.save(l);
		return comment;
	}

	@Override
	public RouteItem saveRoute(RouteItem route) throws Exception {
		// TODO Auto-generated method stub
		RouteEntity l = new RouteEntity();
		l.setId(route.getId());
		l.setDescRoute(route.getDescRoute());
		RouteTagItem[] routeTag = route.getRoutes();
		for (int i = 0; i < routeTag.length; i++) {
			RouteTagEntity rte = new RouteTagEntity();
			rte.setId(route.getId());
			rte.setIdTaggableExt(routeTag[i].getIdTaggableExt());
			l.getRouteTag().add(rte);
		}
		routeRepository.save(l);
		return route;
	}

	@Override
	public String getConfig(String idApp) throws Exception {
		ConfigEntity ent = configRepository.findByIdApp(idApp);
		if (ent != null)
			return ent.getJsonConfig();
		else
			return null;
	}
}
