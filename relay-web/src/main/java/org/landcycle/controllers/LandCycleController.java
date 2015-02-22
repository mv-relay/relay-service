package org.landcycle.controllers;

import java.net.URLDecoder;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.landcycle.api.CommentItem;
import org.landcycle.api.LikeItem;
import org.landcycle.api.MediaItem;
import org.landcycle.api.Position;
import org.landcycle.api.RouteItem;
import org.landcycle.api.TaggableItem;
import org.landcycle.api.User;
import org.landcycle.api.UserItem;
import org.landcycle.api.exception.LandcycleException;
import org.landcycle.api.rest.JsonResponseData;
import org.landcycle.service.LandCycleBusiness;
import org.landcycle.service.MediaFile;
import org.landcycle.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/land")
public class LandCycleController extends BaseRestController {

	protected static final Logger logger = Logger.getLogger(LandCycleController.class.getName());
	int SIZE_AVATAR = 71680000;

	@RequestMapping(value = "/ping")
	public @ResponseBody String ping() {
		return "OK";
	}

	@Autowired
	LandCycleBusiness landCycleBusiness;
	@Autowired
	MediaFile mediaFile;

	@RequestMapping(value = { "", "/" }, method = RequestMethod.GET)
	public @ResponseBody JsonResponseData<TaggableItem> findAround(@RequestParam(value = "lat", required = false) String lat,
			@RequestParam(value = "lng", required = false) String lng, @RequestParam(value = "tags", required = false) String tags,
			@RequestParam(value = "mail", required = false) String mail) {
		TaggableItem sale = new TaggableItem();
		try {
			if (lat != null && !"".equals(lat) && lng != null && !"".equals(lng)) {
				Position pos = new Position();
				pos.setLat(Double.parseDouble(lat));
				pos.setLng(Double.parseDouble(lng));
				sale.setPosition(pos);
				if (tags != null && tags.length() > 0)
					sale.setTags(tags.split(","));
			}
			if (mail != null && !mail.isEmpty()) {
				User user = new User();
				user.setMail(mail);
				sale.setUser(user);
			}
			List<TaggableItem> items = landCycleBusiness.find(sale);
			logger.debug("findAround : " + CommonUtils.bean2string(items));
			return ariaResponse(items);
		} catch (LandcycleException e) {
			logger.error("APPLICATION EXCEPTION", e);
			throw e;
		} catch (Exception e) {
			logger.error("UNHANDLED EXCEPTION", e);
			throw new LandcycleException(e);
		}

	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public @ResponseBody JsonResponseData<TaggableItem> findOne(@PathVariable("id") String id) {
		try {
			UserItem item = new UserItem();
			TaggableItem taggable = new TaggableItem();
			taggable.setId(id);
			item.setTaggable(taggable);
			TaggableItem items = landCycleBusiness.findOne(item);
			logger.debug("findAround : " + CommonUtils.bean2string(items));
			return ariaResponse(items);
		} catch (LandcycleException e) {
			logger.error("APPLICATION EXCEPTION", e);
			throw e;
		} catch (Exception e) {
			logger.error("UNHANDLED EXCEPTION", e);
			throw new LandcycleException(e);
		}

	}

	@RequestMapping(value = "/findByUser/{mail:.+}", method = RequestMethod.GET)
	public @ResponseBody JsonResponseData<TaggableItem> findByUser(@PathVariable("mail") String id) {
		try {
			List<TaggableItem> items = landCycleBusiness.findByUser(URLDecoder.decode(id, "UTF-8"));
			logger.debug("findByUser : " + CommonUtils.bean2string(items));
			return ariaResponse(items);
		} catch (LandcycleException e) {
			logger.error("APPLICATION EXCEPTION", e);
			throw e;
		} catch (Exception e) {
			logger.error("UNHANDLED EXCEPTION", e);
			throw new LandcycleException(e);
		}

	}

	@RequestMapping(value = { "", "/" }, method = RequestMethod.POST, consumes = "application/json")
	public @ResponseBody JsonResponseData<UserItem> saveOrUpdate(@RequestBody UserItem upload) {

		try {
			if (upload.getTaggable().getStream() != null && !upload.getTaggable().getStream().isEmpty()) {
				// // data:image/jpeg;base64,11111
				TaggableItem tg = upload.getTaggable();
				int start = tg.getStream().indexOf(",");
				String data = tg.getStream().substring(start + 1);

				int end1 = tg.getStream().indexOf(";");
				int start1 = tg.getStream().indexOf(":");

				String ctype = tg.getStream().substring(start1 + 1, end1);
				//
				logger.debug("CTYPE : " + ctype);
				int size = tg.getStream().getBytes().length;
				checkSize(size);
				checkType(ctype);
				MediaItem item = new MediaItem();
				item.setStreams(Base64.decodeBase64(data.getBytes()));
				item.setType(ctype.substring(ctype.lastIndexOf("/") + 1, ctype.length()));
				item.setId(tg.getId());
				landCycleBusiness.uploadMedia(item);
				upload.getTaggable().setImageType(ctype.substring(ctype.lastIndexOf("/") + 1, ctype.length()));
			}
			landCycleBusiness.saveOrUpdateTaggable(upload);
			return ariaResponse(upload);
		} catch (Exception e) {
			logger.error("APPLICATION EXCEPTION", e);
			throw new LandcycleException(e);
		}
	}

	private void checkType(String ctype) {
		// if (ctype != null
		// && (!ctype.equals("image/png") && !ctype.equals("image/jpeg") &&
		// !ctype.equals("image/gif") && !ctype.equals("image/jpg") && !ctype
		// .equals("image/pjpeg"))) {
		// throw new LandcycleException("0001", "Formato non consentito");
		// }
	}

	private void checkSize(int size) {
		// if (size > SIZE_AVATAR) {
		// throw new LandcycleException("002", "Dimensione non consentita");
		// }
	}

	@RequestMapping(value = { "", "/" }, method = RequestMethod.OPTIONS)
	public @ResponseBody JsonResponseData<UserItem> options() {
		return ariaResponse(new UserItem());
	}

	@RequestMapping(value = "/Like/{user:.+}", method = RequestMethod.GET)
	public @ResponseBody JsonResponseData<LikeItem> getlike(@PathVariable("user") String user) {
		try {
			LikeItem like = new LikeItem();
			like.setUser(user);
			List<LikeItem> resp = landCycleBusiness.getLikes(like);
			return ariaResponse(resp);
		} catch (Exception e) {
			logger.error("APPLICATION EXCEPTION", e);
			throw new LandcycleException(e);
		}
	}

	@RequestMapping(value = "/TaggableLike/{user:.+}", method = RequestMethod.GET)
	public @ResponseBody JsonResponseData<TaggableItem> getTaggablelike(@PathVariable("user") String user) {
		try {
			LikeItem like = new LikeItem();
			like.setUser(user);
			List<TaggableItem> resp = landCycleBusiness.getTaggableLikes(like);
			return ariaResponse(resp);
		} catch (Exception e) {
			logger.error("APPLICATION EXCEPTION", e);
			throw new LandcycleException(e);
		}
	}

	@RequestMapping(value = "/Like ", method = RequestMethod.POST)
	public @ResponseBody JsonResponseData<LikeItem> like(@RequestBody LikeItem like, HttpServletResponse response) {
		try {
			landCycleBusiness.saveLike(like);
			return ariaResponse(like);
		} catch (Exception e) {
			logger.error("APPLICATION EXCEPTION", e);
			throw new LandcycleException(e);
		}
	}

	@RequestMapping(value = "/Like/{id}/{user:.+}", method = RequestMethod.DELETE)
	public @ResponseBody JsonResponseData<LikeItem> deleteLike(@PathVariable("id") String id, @PathVariable("user") String user) {
		try {
			LikeItem like = new LikeItem();
			like.setId(id);
			like.setUser(user);
			landCycleBusiness.deleteLike(like);
			return ariaResponse(like);
		} catch (Exception e) {
			logger.error("APPLICATION EXCEPTION", e);
			throw new LandcycleException(e);
		}
	}

	@RequestMapping(value = "/Comment", method = RequestMethod.POST)
	public @ResponseBody JsonResponseData<CommentItem> comment(@RequestBody CommentItem like) {
		try {
			landCycleBusiness.saveComment(like);
			return ariaResponse(like);
		} catch (Exception e) {
			logger.error("APPLICATION EXCEPTION", e);
			throw new LandcycleException(e);
		}
	}

	@RequestMapping(value = "/Route", method = RequestMethod.POST)
	public @ResponseBody JsonResponseData<RouteItem> route(@RequestBody RouteItem route) {
		try {
			landCycleBusiness.saveRoute(route);
			return ariaResponse(route);
		} catch (Exception e) {
			logger.error("APPLICATION EXCEPTION", e);
			throw new LandcycleException(e);
		}
	}

	@RequestMapping(value = "/UploadMedia", method = RequestMethod.POST, consumes = "application/json")
	public @ResponseBody JsonResponseData<MediaItem> saveOrUpdateMedia(@RequestBody MediaItem media) {

		try {
			if (media.getStream() != null && !media.getStream().isEmpty()) {
				// // data:image/jpeg;base64,11111
				int start = media.getStream().indexOf(",");
				String data = media.getStream().substring(start + 1);

				int end1 = media.getStream().indexOf(";");
				int start1 = media.getStream().indexOf(":");

				String ctype = media.getStream().substring(start1 + 1, end1);
				media.setStreams(Base64.decodeBase64(data.getBytes()));
				//
				logger.debug("CTYPE : " + ctype);
				int size = media.getStream().getBytes().length;
				checkSize(size);
				checkType(ctype);

				media.setType(ctype.substring(ctype.lastIndexOf("/") + 1, ctype.length()));
				landCycleBusiness.uploadMedia(media);
				// upload.getTaggable().setImageType(ctype.substring(ctype.lastIndexOf("/")
				// + 1, ctype.length()));
			}
			landCycleBusiness.saveOrUpdateMedia(media);
			media.setStream(null);
			return ariaResponse(media);
		} catch (Exception e) {
			logger.error("APPLICATION EXCEPTION", e);
			throw new LandcycleException(e);
		}
	}

	@RequestMapping(value = "/UploadMediaMulti", method = RequestMethod.POST)
	public @ResponseBody JsonResponseData<MediaItem> upload(@ModelAttribute("upolad") FileUpload upload) {
		try {
			// ****************************************************************************
			// VALIDATE THE REQUEST
			// ****************************************************************************
			// TODO: validateRequest(ajaxRequest, request);

			MultipartFile file = upload.getFile();
			// String ctype = file.getContentType();
			String ctype = MimeTypes.getContentType(file.getBytes());
			// ****************************************************************************
			// call business AND RETURN
			// ****************************************************************************
			MediaItem media = new MediaItem();
			media.setId(upload.getId());
			media.setType(ctype.substring(ctype.lastIndexOf("/") + 1, ctype.length()));
			media.setStreams(file.getBytes());
			landCycleBusiness.uploadMedia(media);
			landCycleBusiness.saveOrUpdateMedia(media);
			media.setStream(null);
			media.setStreams(null);
			return ariaResponse(media);
		} catch (Exception e) {
			logger.error("APPLICATION EXCEPTION", e);
			throw new LandcycleException(e);
		}
	}

	@RequestMapping(value = "/config/{id}", method = RequestMethod.GET)
	public @ResponseBody JsonResponseData<String> getConfig(@PathVariable("id") String id) {
		try {

			String config = landCycleBusiness.getConfig(id);
			return ariaResponse(config);
		} catch (Exception e) {
			logger.error("APPLICATION EXCEPTION", e);
			throw new LandcycleException(e);
		}
	}

	@RequestMapping(value = "/media/{filter}/{id}", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<byte[]> getMedia(@PathVariable("filter") String filter, @PathVariable("id") String id) {
		try {

			final HttpHeaders headers = new HttpHeaders();

			return new ResponseEntity<byte[]>(mediaFile.readFile(id, filter), headers, HttpStatus.CREATED);
		} catch (Exception e) {
			logger.error("APPLICATION EXCEPTION", e);
			throw new LandcycleException(e);
		}
	}
}
