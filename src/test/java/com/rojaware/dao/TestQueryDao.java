package com.rojaware.dao;
 
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.google.gson.Gson;
import com.rojaware.query.dao.QueryDao;
import com.rojaware.query.exception.QueryException;
import com.rojaware.query.model.Query;
 
@ContextConfiguration(locations = "classpath:spring.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class TestQueryDao 
{
     
    @Autowired
    private QueryDao dao;
    
    @Test
    public void testAddQuery()
    {
         
        Query query = new Query();
        query.setName("Lokesh");
        String sql = "SELECT * FROM ACCOUNT";
        query.setSql(sql);
        String json = "{id:1}";
        query.setMap(json);
       
         
        dao.insert(query);
        List<Query> querys = dao.list("company");
         
        int size = querys.size();
//		Assert.assertEquals(7, size);
         
        Assert.assertEquals(query.getSql(), querys.get(size-1).getSql());
    }
    
    @Test
    public void execute() throws QueryException{
    	Query query = new Query();
    	query.setId(1001);
    	Map<String, Object> map = new HashMap<String, Object>();
    	map.put("PRODUCT", "SAVING");
    	query.setMap(map);
    	List<List<String>> list=null;
		try {
			list = dao.execute(query);
		} catch (SQLException e) {
			String msg = e.getMessage() + " :: " + e.getErrorCode();
			throw new QueryException(e.getSQLState() + " :: " + msg, e);
		}
    	Gson gson = new Gson();
    	String s = gson.toJson(list);
    	System.out.println(s);
    }
//	public String runToJson(Query query);
//
//	public Query findById(int id);
//
//	public List<Query> list();
//
//	public int findTotalQuery();
//	void insert(Query query);
//	void save(Query query);

	
}