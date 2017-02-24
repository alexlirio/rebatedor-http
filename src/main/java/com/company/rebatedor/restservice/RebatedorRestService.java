package com.company.rebatedor.restservice;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.apache.log4j.Logger;


@Path("/rebatedor-rest-service")
public class RebatedorRestService {
	
	private final static Logger log = Logger.getLogger(RebatedorRestService.class);

    
    @GET
    @Path("/get")
    public Response get(@Context HttpServletRequest httpServletRequest, @Context HttpHeaders httpHeaders, @Context UriInfo uriInfo) {
    	return getResponse(httpServletRequest, httpHeaders, uriInfo, "");
    }

    @POST
    @Path("/post")
    public Response post(@Context HttpServletRequest httpServletRequest, @Context HttpHeaders httpHeaders, @Context UriInfo uriInfo, final String httpBodyRequest) {
    	return getResponse(httpServletRequest, httpHeaders, uriInfo, httpBodyRequest);
    }
    
    @PUT
    @Path("/put")
    public Response put(@Context HttpServletRequest httpServletRequest, @Context HttpHeaders httpHeaders, @Context UriInfo uriInfo, final String httpBodyRequest) {
    	return getResponse(httpServletRequest, httpHeaders, uriInfo, httpBodyRequest);
    }
    
    @DELETE
    @Path("/delete")
    public Response delete(@Context HttpServletRequest httpServletRequest, @Context HttpHeaders httpHeaders, @Context UriInfo uriInfo, final String httpBodyRequest) {
    	return getResponse(httpServletRequest, httpHeaders, uriInfo, httpBodyRequest);
    }

	private Response getResponse(HttpServletRequest httpServletRequest, HttpHeaders httpHeaders, UriInfo uriInfo, final String httpBodyRequest) {
		
		log.info(String.format("HTTP Request Method = %s", httpServletRequest.getMethod()));
		log.info(String.format("HTTP Request URL = %s", httpServletRequest.getRequestURL()));
		log.info(String.format("HTTP Request URL Parameter(s) = %s", uriInfo.getQueryParameters()));
		log.info(String.format("HTTP Request Header(s) = %s", httpHeaders.getRequestHeaders().toString()));
    	log.info(String.format("HTTP Request Body = %s", httpBodyRequest));
    	
    	String httpBodyResponse = null;
    	Integer httpStatusCodeResponse = null;
    	Properties prop = new Properties();
    	InputStream input = null;
    	String resourcePath = this.getClass().getClassLoader().getResource("").getPath().replaceFirst("/", "");
    	
    	try {
    		input = new FileInputStream(resourcePath + "config.properties");
    		prop.load(input);
    	} catch (IOException e) {
    		log.error(e.getClass().getSimpleName() + " in " + this.getClass().getSimpleName() , e);
    		e.printStackTrace();
    	} finally {
    		if (input != null) {
    			try {
    				input.close();
    			} catch (IOException e) {
    				log.error(e.getClass().getSimpleName() + " in " + this.getClass().getSimpleName() , e);
    				e.printStackTrace();
    			}
    		}
    	}
    	
    	httpBodyResponse = prop.getProperty("http_body_file_response");
    	httpStatusCodeResponse = Integer.parseInt(prop.getProperty("http_status_code_response"));
    	
    	String responseFile = resourcePath + httpBodyResponse;
    	log.info(String.format("Response File used = %s", responseFile));
    	try {
    		httpBodyResponse = new String(Files.readAllBytes(Paths.get(responseFile)));
    	} catch (IOException e) {
    		log.error(String.format("IOException = %s", e.getMessage()));
    		e.printStackTrace();
    		return Response.status(500)
    				.entity(e.getMessage())
    				.build();
    	}
    	
    	log.info(String.format("HTTP Response Status Code = %s", httpStatusCodeResponse));
    	log.info(String.format("HTTP Response Header = %s", HttpHeaders.CONTENT_TYPE + "=" + httpHeaders.getRequestHeader(HttpHeaders.CONTENT_TYPE)));
    	log.info(String.format("HTTP Response Body = %s", httpBodyResponse));
    	
    	return Response.status(httpStatusCodeResponse)
    			.header(HttpHeaders.CONTENT_TYPE, httpHeaders.getRequestHeader(HttpHeaders.CONTENT_TYPE))
    			.entity(httpBodyResponse)
    			.build();
	}
	
}
