package org.landcycle.controllers;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.landcycle.api.CommentItem;
import org.landcycle.api.LikeItem;
import org.landcycle.api.Position;
import org.landcycle.api.TaggableItem;
import org.landcycle.api.UserItem;
import org.landcycle.api.exception.LandcycleException;
import org.landcycle.api.rest.JsonResponseData;
import org.landcycle.service.LandCycleBusiness;
import org.landcycle.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/land")
public class LandCycleController extends BaseRestController {

	protected static final Logger logger = Logger.getLogger(LandCycleController.class.getName());
	int SIZE_AVATAR = 71680000;

	@RequestMapping(value = "/ping")
	public @ResponseBody
	String ping() {
		return "OK";
	}

	@Autowired
	LandCycleBusiness landCycleBusiness;

	@RequestMapping(value = { "", "/" }, method = RequestMethod.GET)
	public @ResponseBody
	JsonResponseData<TaggableItem> findAround(@RequestParam(value = "lat", required = false) String lat, @RequestParam(value = "lng", required = false) String lng,
	// @RequestParam(value = "user", required = false) String user,
			@RequestParam(value = "tags", required = false) String tags, HttpServletResponse response) {
		TaggableItem sale = new TaggableItem();
		try {
//			UserItem item = new UserItem();
			if (lat != null && !"".equals(lat) && lng != null && !"".equals(lng)) {
				Position pos = new Position();
				pos.setLat(Double.parseDouble(lat));
				pos.setLng(Double.parseDouble(lng));
				sale.setPosition(pos);
				if (tags != null && tags.length() > 0)
					sale.setTags(tags.split(","));
//				item.setTaggable(sale);
			}
			List<TaggableItem> items = landCycleBusiness.find(sale);
			logger.debug("findAround : " + CommonUtils.bean2string(items));
//			item.setTaggables(items);
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
	public @ResponseBody
	JsonResponseData<TaggableItem> findOne(@PathVariable("id") String id) {
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

	@RequestMapping(value = { "", "/" }, method = RequestMethod.POST, consumes = "application/json")
	public @ResponseBody
	JsonResponseData<UserItem> saveOrUpdate(@RequestBody UserItem upload) {

		try {
			if (upload.getTaggable().getStream() != null && !upload.getTaggable().getStream().isEmpty()) {
				// // data:image/jpeg;base64,11111
				TaggableItem tg = upload.getTaggable();
				int start = tg.getStream().indexOf(",");
				String data = tg.getStream().substring(start + 1);

				int end1 = tg.getStream().indexOf(";");
				int start1 = tg.getStream().indexOf(":");

				String ctype = tg.getStream().substring(start1 + 1, end1);
				tg.setStreams(Base64.decodeBase64(data.getBytes()));
				//
				logger.debug("CTYPE : " + ctype);
				int size = tg.getStream().getBytes().length;
				if (size > SIZE_AVATAR) {
					throw new LandcycleException("002", "Dimensione non consentita");
				}
				if (ctype != null
						&& (!ctype.equals("image/png") && !ctype.equals("image/jpeg") && !ctype.equals("image/gif") && !ctype.equals("image/jpg") && !ctype
								.equals("image/pjpeg"))) {
					throw new LandcycleException("0001", "Formato non consentito");
				}
				UserItem item = new UserItem();
				item.setTaggable(tg);
				tg.setImageType(ctype.substring(ctype.lastIndexOf("/") + 1, ctype.length()));
				tg.setId(tg.getId());
				landCycleBusiness.upload(item);
				upload.getTaggable().setImageType(ctype.substring(ctype.lastIndexOf("/") + 1, ctype.length()));
			}
			landCycleBusiness.saveOrUpdateTaggable(upload);
			return ariaResponse(upload);
		} catch (Exception e) {
			logger.error("APPLICATION EXCEPTION", e);
			throw new LandcycleException(e);
		}
	}

	@RequestMapping(value = { "", "/" }, method = RequestMethod.OPTIONS)
	public @ResponseBody
	JsonResponseData<UserItem> options() {
		return ariaResponse(new UserItem());
	}

	@RequestMapping(value = { "", "/" }, method = RequestMethod.PUT)
	public @ResponseBody
	JsonResponseData<UserItem> update(@RequestBody UserItem upload, HttpServletResponse response) {

		try {

			landCycleBusiness.saveOrUpdateTaggable(upload);
			return ariaResponse(upload);
		} catch (Exception e) {
			logger.error("APPLICATION EXCEPTION", e);
			throw new LandcycleException(e);
		}
	}

	@RequestMapping(value = "/Like ", method = RequestMethod.POST)
	public @ResponseBody
	JsonResponseData<LikeItem> like(@RequestBody LikeItem like, HttpServletResponse response) {
		try {
			landCycleBusiness.saveLike(like);
			return ariaResponse(like);
		} catch (Exception e) {
			logger.error("APPLICATION EXCEPTION", e);
			throw new LandcycleException(e);
		}
	}
	@RequestMapping(value = "/Comment ", method = RequestMethod.POST)
	public @ResponseBody
	JsonResponseData<CommentItem> comment(@RequestBody CommentItem like) {
		try {
			landCycleBusiness.saveComment(like);
			return ariaResponse(like);
		} catch (Exception e) {
			logger.error("APPLICATION EXCEPTION", e);
			throw new LandcycleException(e);
		}
	}

	// @RequestMapping(value = "/UploadStream", method = RequestMethod.POST)
	// public @ResponseBody
	// JsonResponseData<UserItem> uploadStream(@RequestBody TaggableFile
	// uploadedFile, HttpServletResponse response) {
	// InputStream is = null;
	// BufferedInputStream bis = null;
	// ByteArrayInputStream bais = null;
	// try {
	// Taggable taggable = new Taggable();
	// // logger.debug(CommonUtils.bean2string(uploadedFile));
	// // data:image/jpeg;base64,
	//
	// int start = uploadedFile.getStream().indexOf(",");
	// String data = uploadedFile.getStream().substring(start + 1);
	//
	// taggable.setStreams(Base64.decodeBase64(data.getBytes()));
	// bais = new ByteArrayInputStream(taggable.getStreams());
	// bis = new BufferedInputStream(bais);
	// is = bis;
	// String ctype = URLConnection.guessContentTypeFromStream(is);
	// if (ctype == null){
	// int end = uploadedFile.getStream().indexOf(";");
	// int starts = uploadedFile.getStream().indexOf(":");
	// String ctypez = uploadedFile.getStream().substring(starts ,end);
	// logger.debug("ctypez : " +ctypez );
	// ctype = "image/jpeg";
	// }
	// logger.debug("CTYPE : " + ctype);
	// int size = taggable.getStreams().length;
	// if (size > SIZE_AVATAR) {
	// throw new LandcycleException("002", "Dimensione non consentita");
	// }
	// if (ctype != null
	// && (!ctype.equals("image/png") && !ctype.equals("image/jpeg") &&
	// !ctype.equals("image/gif") && !ctype.equals("image/jpg") && !ctype
	// .equals("image/pjpeg"))) {
	// throw new LandcycleException("0001", "Formato non consentito");
	// }
	//
	// UserItem item = new UserItem();
	// item.setTaggable(taggable);
	// taggable.setImageType(ctype.substring(ctype.lastIndexOf("/") + 1,
	// ctype.length()));
	// taggable.setId(uploadedFile.getId());
	// landCycleBusiness.upload(item);
	// return ariaResponse(item);
	// } catch (LandcycleException e) {
	// logger.error("APPLICATION EXCEPTION", e);
	// throw e;
	// } catch (Exception e) {
	// logger.error("UNHANDLED EXCEPTION", e);
	// throw new LandcycleException(e);
	// } finally {
	// if (is != null && bis != null && bais != null) {
	// try {
	// bais.close();
	// bis.close();
	// is.close();
	// } catch (IOException e) {
	// // TODO Auto-generated catch block
	//
	// }
	// }
	// }
	// }
	//
	// @RequestMapping(value = "/Upload", method = RequestMethod.POST)
	// public @ResponseBody
	// JsonResponseData<UserItem> upload(@ModelAttribute("uploadedFile")
	// FileUpload uploadedFile, BindingResult result, HttpServletResponse
	// response) {
	// try {
	// MultipartFile file = uploadedFile.getFile();
	// String ctype = file.getContentType();
	// logger.debug("Formato  " + ctype);
	// long size = file.getSize();
	// System.out.println("SIZE FILE : " + size);
	// if (size > SIZE_AVATAR) {
	// throw new LandcycleException("002", "Dimensione non consentita");
	// }
	// if (ctype != null
	// && (!ctype.equals("image/png") && !ctype.equals("image/jpeg") &&
	// !ctype.equals("image/gif") && !ctype.equals("image/jpg") && !ctype
	// .equals("image/pjpeg"))) {
	// throw new LandcycleException("0001", "Formato non consentito");
	// }
	// UserItem item = new UserItem();
	// Taggable taggable = new Taggable();
	// // taggable.setId(id);
	// String fileName = file.getOriginalFilename();
	// taggable.setImageType(fileName.substring(fileName.lastIndexOf(".") + 1,
	// fileName.length()));
	// taggable.setStreams(file.getBytes());
	// item.setTaggable(taggable);
	// landCycleBusiness.upload(item);
	// return ariaResponse(item);
	// } catch (LandcycleException e) {
	// logger.error("APPLICATION EXCEPTION", e);
	// throw e;
	// } catch (Exception e) {
	// logger.error("UNHANDLED EXCEPTION", e);
	// throw new LandcycleException(e);
	// }
	// }
	//
	// @RequestMapping(value = "/Upload/{id}", method = RequestMethod.POST)
	// public @ResponseBody
	// JsonResponseData<UserItem> uploadId(@ModelAttribute("uploadedFile")
	// FileUpload uploadedFile, @PathVariable("id") String id, BindingResult
	// result,
	// HttpServletResponse response) {
	// try {
	// MultipartFile file = uploadedFile.getFile();
	// String ctype = file.getContentType();
	// logger.debug("Formato  " + ctype);
	// long size = file.getSize();
	// System.out.println("SIZE FILE : " + size);
	// if (size > SIZE_AVATAR) {
	// throw new LandcycleException("002", "Dimensione non consentita");
	// }
	// if (ctype != null
	// && (!ctype.equals("image/png") && !ctype.equals("image/jpeg") &&
	// !ctype.equals("image/gif") && !ctype.equals("image/jpg") && !ctype
	// .equals("image/pjpeg"))) {
	// throw new LandcycleException("0001", "Formato non consentito");
	// }
	// UserItem item = new UserItem();
	// Taggable taggable = new Taggable();
	// taggable.setId(id);
	// String fileName = file.getOriginalFilename();
	// taggable.setImageType(fileName.substring(fileName.lastIndexOf(".") + 1,
	// fileName.length()));
	// taggable.setStreams(file.getBytes());
	// item.setTaggable(taggable);
	// landCycleBusiness.upload(item);
	// return ariaResponse(item);
	// } catch (LandcycleException e) {
	// logger.error("APPLICATION EXCEPTION", e);
	// throw e;
	// } catch (Exception e) {
	// logger.error("UNHANDLED EXCEPTION", e);
	// throw new LandcycleException(e);
	// }
	// }

}
