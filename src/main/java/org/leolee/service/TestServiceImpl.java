package org.leolee.service;

import java.util.HashMap;
import java.util.Map;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.leolee.service.serviceInterface.TestService;

import com.alibaba.fastjson.JSON;

@WebService
@Path("/") 
public class TestServiceImpl implements TestService {

	@WebMethod
	@GET
	@Path("/getJsonData/{id}")
	@Produces({"application/json;charset=UTF-8"}) 
	public String getJsonData(@PathParam("id") int id) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("flag", true);
		map.put("pathParam", id);
		return JSON.toJSONString(map);
	}

}
