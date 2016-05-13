package com.rojaware.query.controller;


import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.rmi.CORBA.Util;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.google.gson.Gson;
import com.rojaware.query.exception.ErrorResource;
import com.rojaware.query.exception.FieldErrorResource;
import com.rojaware.query.exception.QueryException;
import com.rojaware.query.model.CsvReportVO;
import com.rojaware.query.model.Query;
import com.rojaware.query.model.TableView;
import com.rojaware.query.service.QueryService;
import com.rojaware.query.util.SplitString;
 
 
 
 
@Controller ("appController")
@RequestMapping("/")
@SessionAttributes("tableView")
public class AppController {
 
	final static Logger LOG = Logger.getLogger(AppController.class);
	   
	@Autowired
    QueryService queryService;
   
 
    /**
     * This method will list all existing querys.
     */
    @RequestMapping(value = { "/", "/list" }, method = RequestMethod.GET)
    public String listQuerys(ModelMap model) {
 
    	List<Query> queryList = queryService.findAllQuerys();
        LOG.info(""+ queryList.toString());
        model.addAttribute("queryList", queryList);
        return "querylist";
    }
    
    @RequestMapping(value = "/run-query-{id}", method = RequestMethod.GET)
	public String run(@PathVariable Integer id, ModelMap model) {
		Query query = queryService.findById(id);
		LOG.debug("run before or without parameters...");
		String message = null;
		ErrorResource er = new ErrorResource();	
		// IF query from database has parameter map, display parameter inputs
		Map<String, Object> map = query.getMap();
		if (map != null && map.size() > 0 ) {
			 model.addAttribute("query", query);
			 return "parameters";
		}
		TableView tableView=null;
		try {
			tableView = queryService.execute(query);
		} catch (QueryException e) {
			message = "Unable to execute Query :: ";
			FieldErrorResource error = new FieldErrorResource();
			error.setCode("UNABLE_RUN_QUERY");
			error.setField("QUERY_DATA");
			error.setMessage(message);
			error.setResource("QUERY");
			LOG.error(message, e);
			er.getFieldErrors().add(error);
		}
		Gson gson = new Gson();
		LOG.debug(gson.toJson(tableView));
		model.addAttribute("query", query);
		model.addAttribute("tableView", tableView);
        return "resultlist";
	}
   
    @RequestMapping(value = "/run-query-{id}", method = RequestMethod.POST)
   	public String run(@Valid Query query, BindingResult result, ModelMap model) {
   		LOG.debug("run with parameters...");
   		String message = null;
   		ErrorResource er = new ErrorResource();	
   		// IF query from database has parameter map, display parameter inputs
   		Map<String, Object> map = query.getMap();
   		if (map == null || map.size() == 0 ) {
   			message = "Parameter can't be blank :: ";
   			FieldErrorResource error = new FieldErrorResource();
   			error.setCode("BLANK_PARAMETERS");
   			error.setField("QUERY_DATA");
   			error.setMessage(message);
   			error.setResource("QUERY");
   			LOG.error(message);
   			er.getFieldErrors().add(error);
   			model.addAttribute("query", query);
   			model.addAttribute("errorResource", er);
			
			return "parameters";
   		}
   		if (isMapValueBlank(map)) {
				model.addAttribute("query", query);
				// TODO pass error msg
				return "parameters";
   		}
   		TableView tableView=null;
   		try {
   			tableView = queryService.execute(query);
   		} catch (QueryException e) {
   			message = "Unable to execute Query :: ";
   			FieldErrorResource error = new FieldErrorResource();
   			error.setCode("UNABLE_RUN_QUERY");
   			error.setField("QUERY_DATA");
   			error.setMessage(message);
   			error.setResource("QUERY");
   			LOG.error(message, e);
   			er.getFieldErrors().add(error);
   		}
   		Gson gson = new Gson();
   		LOG.debug(gson.toJson(tableView));
   		model.addAttribute("query", query);
   		model.addAttribute("tableView", tableView);
           return "resultlist";
   	}
    private boolean isMapValueBlank(Map<String, Object> map) {
		for (Map.Entry<String, Object> entry : map.entrySet()) {
			String value = (String)entry.getValue();
			if (StringUtils.isBlank(value)) {
				return true;
			}
		}
		return false;
	}

	/**
     * This method will provide the medium to add a new query.
     */
    @RequestMapping(value = { "/newquery" }, method = RequestMethod.GET)
    public String newQuery(ModelMap model) {
    	LOG.debug("display new input");
    	Query query = new Query();
        model.addAttribute("query", query);
        model.addAttribute("edit", false);
        return "registration";
    }
 
    /**
     * This method will be called on form submission, handling POST request for
     * saving query in database. It also validates the query input
     */
    @RequestMapping(value = { "/newquery" }, method = RequestMethod.POST)
    public String saveQuery(@Valid Query query, BindingResult result, ModelMap model) {
    	LOG.debug("save new input");
        if (result.hasErrors()) {
            return "registration";
        }
 
        /*
         * Preferred way to achieve uniqueness of field [sso] should be implementing custom @Unique annotation 
         * and applying it on field [sso] of Model class [Query].
         * 
         * Below mentioned peace of code [if block] is to demonstrate that you can fill custom errors outside the validation
         * framework as well while still using internationalized messages.
         * 
         */
        if(!queryService.isQueryUnique( query.getId(), query.getName())){
//            FieldError ssoError =new FieldError("query","ssoId",messageSource.getMessage("non.unique.ssoId",
//            		new String[]{query.getName()}, Locale.getDefault()));
//            result.addError(ssoError);
            return "registration";
        }
        // build parameter map from SQL
        buildParameters(query);
        queryService.saveQuery(query);
 
        model.addAttribute("success", "Query :: /n" + query.getName() + "/n"+ query.getSql() + "/n registered successfully");
        //return "success";
        return "registrationsuccess";
    }
 
 
    private void buildParameters(Query query) {
		// collect all {} words from SQL
    	String sql = query.getSql();
    	Map<String, Object> map = SplitString.toParameterMap(sql);
    	query.setMap(map);
	}

	/**
     * This method will provide the medium to update an existing query.
     */
    @RequestMapping(value = { "/edit-query-{id}" }, method = RequestMethod.GET)
    public String editQuery(@PathVariable("id") Integer id, ModelMap model) {
        Query query = queryService.findById(id);
        model.addAttribute("query", query);
        model.addAttribute("edit", true);
        return "registration";
    }
     
    /**
     * This method will be called on form submission, handling POST request for
     * updating query in database. It also validates the query input
     */
    @RequestMapping(value = { "/edit-query-{id}" }, method = RequestMethod.POST)
    public String updateQuery(@Valid Query query, BindingResult result,
            ModelMap model, @PathVariable("id") Integer id) {
 
        if (result.hasErrors()) {
            return "registration";
        }
 
        /*//Uncomment below 'if block' if you WANT TO ALLOW UPDATING SSO_ID in UI which is a unique key to a Query.
        if(!queryService.isQuerySSOUnique(query.getId(), query.getSsoId())){
            FieldError ssoError =new FieldError("query","ssoId",messageSource.getMessage("non.unique.ssoId", new 
 
String[]{query.getSsoId()}, Locale.getDefault()));
            result.addError(ssoError);
            return "registration";
        }*/
 
        queryService.updateQuery(query);
 
        model.addAttribute("success", "Query " + query.getName() + " "+ query.getSql() + " updated successfully");
        return "registrationsuccess";
    }
 
     
    /**
     * This method will delete an query by it's id value.
     */
    @RequestMapping(value = { "/delete-query-{id}" }, method = RequestMethod.GET)
    public String deleteQuery(@PathVariable("id") Integer id) {
    	LOG.debug("DELETING");
        queryService.deleteQueryById(id);
        return "redirect:/rest/list";
    }
    
    @RequestMapping(value = "/downloadCSV")
	public void downloadCSV(HttpServletResponse response, @ModelAttribute("tableView") TableView tableView, ModelMap model) throws IOException {
 
		response.setContentType("text/csv");
		String time = DateFormat.getInstance().format(new Date());
		response.setHeader("Content-Type", "application/vnd.ms-excel");

		//PETR-187 Fix: Excel can't read special characters when encoding is set to UTF-8
		response.setCharacterEncoding("WINDOWS-1252");
		String reportName = "CSV_Report_" + time + ".csv";
		
		response.setHeader(
				"Content-Disposition",
				"attachment; filename=" + reportName + "-" + time + ".csv");
		LOG.info(reportName.trim());
		
		response.setHeader("Content-disposition", "attachment;filename="+reportName);
 
		// write header
		List<String> rows = new ArrayList<String>();

		rows.add(StringUtils.join(tableView.getColumns(), ','));
		rows.add("\n");
 
		// write body
		List<List<String>> lineList = tableView.getRows();
		for (List<String> line : lineList) {
			rows.add(StringUtils.join(line, ','));
			rows.add("\n");
		}
 
		Iterator<String> iter = rows.iterator();
		while (iter.hasNext()) {
			String outputString = (String) iter.next();
			response.getOutputStream().print(outputString);
		}
 
		response.getOutputStream().flush();
		
	}
}
