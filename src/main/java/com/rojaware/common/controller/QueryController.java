package com.rojaware.common.controller;

import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.rojaware.common.service.JdbcTemplateApp;
import com.rojaware.query.dao.QueryDAO;
import com.rojaware.query.model.Query;

@Controller
@RequestMapping("/query")
public class QueryController {
	final static Logger LOG = Logger.getLogger(QueryController.class);
	   
	@Autowired
    private QueryDAO queryDao;
     

	@RequestMapping(value = "/{name}", method = RequestMethod.GET)
	public ModelAndView getQuery(@PathVariable String name, ModelMap model) {
		return new ModelAndView("query", "command", new Query(name));
	}

	@RequestMapping(value = "/addQuery", method = RequestMethod.POST)
	public String addQuery(@ModelAttribute("SpringWeb") Query query, ModelMap model) {
		// save into table
		
		// display to page
		model.addAttribute("name", query.getName());
		model.addAttribute("sql", query.getSql());
		model.addAttribute("id", query.getId());
//		model.addAttribute("map", query.getMapJson());

		return "result"; // call result.jsp
	}

	public void updateQuery(Query query) {
		// TODO Auto-generated method stub

	}

	public Query getQuery(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	public void deleteQuery(int id) {
		// TODO Auto-generated method stub

	}

	public List<Query> getQuerys() {
		// TODO Auto-generated method stub
		return null;
	}
	@RequestMapping(value="/", method = RequestMethod.GET)
    public ModelAndView home() throws IOException{
        List<Query> queryList = queryDao.list();
        LOG.info(""+ queryList.toString());
        ModelAndView model = new ModelAndView("home");
        model.addObject("queryList", queryList);
        return model;
    }
}