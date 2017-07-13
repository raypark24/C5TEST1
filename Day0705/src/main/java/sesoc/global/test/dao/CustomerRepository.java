package sesoc.global.test.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import sesoc.global.test.vo.Customer;

@Repository
public class CustomerRepository {
	@Autowired
	SqlSession sqlSession;
	public int insertCustomer(Customer customer){ // insert
		int result = 0;
		CustomerDAO daoC = sqlSession.getMapper(CustomerDAO.class);
		result = daoC.insert(customer);
		
		return result;
	}
	public int updateCustomer(Customer customer){ // update
		int result = 0;
		
		return result;
	}
	public int deleteCustomer(String custid){ // delete
		int result = 0;
		
		return result;
	}
	public Customer findCustomer(String custid,String password){ // selectOne
		Customer resultC = null;
		CustomerDAO daoC = sqlSession.getMapper(CustomerDAO.class);
		Map<String,String> map = new HashMap<>();
		map.put("custid", custid);
		map.put("password", password);
		
		resultC = daoC.selectOne(map);
		System.out.println(resultC);
		return resultC;
	}
	public List<Customer> findAll(){ // selectAll
		ArrayList<Customer> resultC = null;
		
		return resultC;
	}
	
}
