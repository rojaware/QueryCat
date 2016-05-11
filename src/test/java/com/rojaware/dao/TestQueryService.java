package com.rojaware.dao;
 
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.rojaware.query.exception.QueryException;
import com.rojaware.query.model.Query;
import com.rojaware.query.model.TableView;
import com.rojaware.query.service.QueryService;
import com.rojaware.query.service.impl.QueryServiceImpl;
 
@ContextConfiguration(locations = "classpath:spring.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class TestQueryService 
{
	final static Logger LOG = Logger.getLogger(QueryServiceImpl.class);
    @Autowired
    private QueryService service;
    
//    @Test
//    public void testAddQuery()
//    {
//         
//        Query query = new Query();
//        query.setName("Lokesh");
//        String sql = "SELECT * FROM ACCOUNT";
//        query.setSql(sql);
//        String json = "{id:1}";
//        query.setMap(json);
//       
//         
//        dao.insert(query);
//        List<Query> querys = dao.list();
//         
//        int size = querys.size();
////		Assert.assertEquals(7, size);
//         
//        Assert.assertEquals(query.getSql(), querys.get(size-1).getSql());
//    }
    
    @Test
    public void execute(){
    	Query query = new Query();
    	query.setId(1001);
    	Map<String, Object> map = new HashMap<String, Object>();
    	map.put("PRODUCT", "SAVING");
    	query.setMap(map);
    	TableView view=null;
		try {
			view = service.execute(query);
		} catch (QueryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	LOG.debug(":: "+ view);
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