package com.rojaware.query.controller;


import java.util.List;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.gson.Gson;
import com.rojaware.query.exception.QueryException;
import com.rojaware.query.model.Query;
import com.rojaware.query.model.TableView;
import com.rojaware.query.service.QueryService;
 
 
 
 
@Controller ("appController")
@RequestMapping("/")
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

		TableView tableView=null;
		try {
			tableView = queryService.execute(query);
		} catch (QueryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Gson gson = new Gson();
		LOG.debug(gson.toJson(tableView));
		model.addAttribute("tableView", tableView);
        return "resultlist";
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
         * Below mentioned peace of code [if block] is to demonstrate that you can fill custom errors outside the 
 
validation
         * framework as well while still using internationalized messages.
         * 
         */
        if(!queryService.isQueryUnique( query.getId(), query.getName())){
//            FieldError ssoError =new FieldError("query","ssoId",messageSource.getMessage("non.unique.ssoId",
//            		new String[]{query.getName()}, Locale.getDefault()));
//            result.addError(ssoError);
            return "registration";
        }
        queryService.saveQuery(query);
 
        model.addAttribute("success", "Query :: \n" + query.getName() + "\n"+ query.getSql() + "\n registered successfully");
        //return "success";
        return "registrationsuccess";
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
     
    
 
}
