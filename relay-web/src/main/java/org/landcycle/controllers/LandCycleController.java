package org.landcycle.controllers;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.landcycle.api.ForSale;
import org.landcycle.api.Position;
import org.landcycle.api.User;
import org.landcycle.api.UserItem;
import org.landcycle.api.exception.LandcycleException;
import org.landcycle.api.rest.JsonResponseData;
import org.landcycle.exception.AsiaException;
import org.landcycle.service.LandCycleBusiness;
import org.landcycle.utils.CommonUtils;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
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

	@Resource
	LandCycleBusiness landCycleBusiness;

	@RequestMapping(value = { "", "/" }, method = RequestMethod.POST, consumes = "application/json")
	public @ResponseBody
	JsonResponseData<UserItem> saveOrUpdate(@RequestBody UserItem upload, HttpServletResponse response) {
		try {
			// ObjectMapper mapper = new ObjectMapper();
			// String jjson = mapper.writeValueAsString(upload);
			logger.debug(upload.toString());
			landCycleBusiness.saveOrUpdateSale(upload);
//			response.setHeader("Access-Control-Allow-Origin", "*");
			return ariaResponse(upload);
		} catch (Exception e) {
			throw new LandcycleException(e);
		}
	}

	@RequestMapping(value = { "", "/" }, method = RequestMethod.GET)
	public @ResponseBody
	JsonResponseData<UserItem> findAround(
			@RequestParam(value = "lat", required = false) String lat,
			@RequestParam(value = "lng", required = false) String lng,
			@RequestParam(value = "mailvend", required = false) String mailvend, HttpServletResponse response) {
		try {
			UserItem item = new UserItem();
			if (lat != null && !"".equals(lat) && lng != null
					&& !"".equals(lng)) {
				Position pos = new Position();
				pos.setLat(Double.parseDouble(lat));
				pos.setLng(Double.parseDouble(lng));
				ForSale sale = new ForSale();
				sale.setPosition(pos);
				User u = new User();
				u.setMail(mailvend);
				item.setUser(u);
				item.setForSale(sale);
			}
			List<UserItem> items = landCycleBusiness.find(item);
			logger.debug("findAround : " + CommonUtils.bean2string(items));
//			response.setHeader("Access-Control-Allow-Origin", "*");
			return ariaResponse(items);
		} catch (Exception e) {
			throw new LandcycleException(e);
		}

	}

	@RequestMapping(value = { "", "/" }, method = RequestMethod.PUT)
	public @ResponseBody
	JsonResponseData<UserItem> update(@RequestBody UserItem upload, HttpServletResponse response) {
		try {
		landCycleBusiness.saveOrUpdateSale(upload);
//		response.setHeader("Access-Control-Allow-Origin", "*");
		return ariaResponse(new UserItem());
		} catch (Exception e) {
			throw new LandcycleException(e);
		}
	}

	@RequestMapping(value = "/Upload", method = RequestMethod.POST)
	public @ResponseBody
	JsonResponseData<UserItem> upload(
			@ModelAttribute("uploadedFile") FileUpload uploadedFile,
			BindingResult result, HttpServletResponse response) {
		try {
			MultipartFile file = uploadedFile.getFile();
			String ctype = file.getContentType();
			logger.debug("Formato  " + ctype);
			long size = file.getSize();
			System.out.println("SIZE FILE : " + size);
			if (size > SIZE_AVATAR) {
				throw new AsiaException("002", "Dimensione non consentita");
			}
			if (ctype != null
					&& (!ctype.equals("image/png")
							&& !ctype.equals("image/jpeg")
							&& !ctype.equals("image/gif")
							&& !ctype.equals("image/jpg") && !ctype
								.equals("image/pjpeg"))) {
				throw new LandcycleException("0001", "Formato non consentito");
			}
			UserItem item = new UserItem();
			ForSale forSale = new ForSale();
			String fileName = file.getOriginalFilename();
			forSale.setImageType(fileName.substring(
					fileName.lastIndexOf(".") + 1, fileName.length()));
			forSale.setStream(new String(file.getBytes(),"UTF-8"));
			forSale.setStreams(file.getBytes());
			item.setForSale(forSale);
			landCycleBusiness.upload(item);
//			response.setHeader("Access-Control-Allow-Origin", "*");
			return ariaResponse(item);
		} catch (AsiaException e) {
			// log.error("APPLICATION EXCEPTION", e);
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			// log.error("UNHANDLED EXCEPTION", e);
			e.printStackTrace();
			throw new AsiaException(e);
		}
	}

}
