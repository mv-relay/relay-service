package org.landcycle.controllers;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.landcycle.api.LikeItem;
import org.landcycle.api.Position;
import org.landcycle.api.Taggable;
import org.landcycle.api.UserItem;
import org.landcycle.api.exception.LandcycleException;
import org.landcycle.api.rest.JsonResponseData;
import org.landcycle.service.LandCycleBusiness;
import org.landcycle.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
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
	public @ResponseBody
	String ping() {
		return "OK";
	}

	@Autowired
	LandCycleBusiness landCycleBusiness;

	@RequestMapping(value = { "", "/" }, method = RequestMethod.POST, consumes = "application/json")
	public @ResponseBody
	JsonResponseData<UserItem> saveOrUpdate(@RequestBody UserItem upload) {
		try {
			logger.debug(CommonUtils.bean2string(upload));
			landCycleBusiness.saveOrUpdateTaggable(upload);
			return ariaResponse(upload);
		} catch (Exception e) {
			logger.error("APPLICATION EXCEPTION", e);
			throw new LandcycleException(e);
		}
	}

	@RequestMapping(value = { "", "/" }, method = RequestMethod.OPTIONS)
	public @ResponseBody
	JsonResponseData<UserItem> options()
	{
		return ariaResponse(new UserItem()); 
	}
	
	@RequestMapping(value = { "", "/" }, method = RequestMethod.GET)
	public @ResponseBody
	JsonResponseData<UserItem> findAround(@RequestParam(value = "lat", required = false) String lat,
			@RequestParam(value = "lng", required = false) String lng,
//			@RequestParam(value = "user", required = false) String user,
			@RequestParam(value = "tags", required = false) String tags,HttpServletResponse response) {
		try {
			UserItem item = new UserItem();
			if (lat != null && !"".equals(lat) && lng != null && !"".equals(lng)) {
				Position pos = new Position();
				pos.setLat(Double.parseDouble(lat));
				pos.setLng(Double.parseDouble(lng));
				Taggable sale = new Taggable();
				sale.setPosition(pos);
				if(tags != null && tags.length() > 0)
					sale.setTags(tags.split(","));
//				User u = new User();
//				u.setMail(user);
//				item.setUser(u);
				item.setTaggable(sale);
			}
			List<UserItem> items = landCycleBusiness.find(item);
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

	@RequestMapping(value = "/Upload", method = RequestMethod.POST)
	public @ResponseBody
	JsonResponseData<UserItem> upload(@ModelAttribute("uploadedFile") FileUpload uploadedFile, BindingResult result,
			HttpServletResponse response) {
		try {
			MultipartFile file = uploadedFile.getFile();
			String ctype = file.getContentType();
			logger.debug("Formato  " + ctype);
			long size = file.getSize();
			System.out.println("SIZE FILE : " + size);
			if (size > SIZE_AVATAR) {
				throw new LandcycleException("002", "Dimensione non consentita");
			}
			if (ctype != null
					&& (!ctype.equals("image/png") && !ctype.equals("image/jpeg") && !ctype.equals("image/gif")
							&& !ctype.equals("image/jpg") && !ctype.equals("image/pjpeg"))) {
				throw new LandcycleException("0001", "Formato non consentito");
			}
			UserItem item = new UserItem();
			Taggable taggable = new Taggable();
			String fileName = file.getOriginalFilename();
			taggable.setImageType(fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length()));
			taggable.setStreams(file.getBytes());
			item.setTaggable(taggable);
			landCycleBusiness.upload(item);
			return ariaResponse(item);
		} catch (LandcycleException e) {
			logger.error("APPLICATION EXCEPTION", e);
			throw e;
		} catch (Exception e) {
			logger.error("UNHANDLED EXCEPTION", e);
			throw new LandcycleException(e);
		}
	}
	
	@RequestMapping(value = "/Upload/{id}", method = RequestMethod.POST)
	public @ResponseBody
	JsonResponseData<UserItem> uploadId(@ModelAttribute("uploadedFile") FileUpload uploadedFile,@PathVariable("id") String id, BindingResult result,
			HttpServletResponse response) {
		try {
			MultipartFile file = uploadedFile.getFile();
			String ctype = file.getContentType();
			logger.debug("Formato  " + ctype);
			long size = file.getSize();
			System.out.println("SIZE FILE : " + size);
			if (size > SIZE_AVATAR) {
				throw new LandcycleException("002", "Dimensione non consentita");
			}
			if (ctype != null
					&& (!ctype.equals("image/png") && !ctype.equals("image/jpeg") && !ctype.equals("image/gif")
							&& !ctype.equals("image/jpg") && !ctype.equals("image/pjpeg"))) {
				throw new LandcycleException("0001", "Formato non consentito");
			}
			UserItem item = new UserItem();
			Taggable forSale = new Taggable();
			forSale.setId(id);
			String fileName = file.getOriginalFilename();
			forSale.setImageType(fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length()));
			forSale.setStreams(file.getBytes());
			item.setTaggable(forSale);
			landCycleBusiness.upload(item);
			return ariaResponse(item);
		} catch (LandcycleException e) {
			logger.error("APPLICATION EXCEPTION", e);
			throw e;
		} catch (Exception e) {
			logger.error("UNHANDLED EXCEPTION", e);
			throw new LandcycleException(e);
		}
	}

}
