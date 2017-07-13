package sesoc.global.test.dao;

import java.util.List;
import java.util.Map;

import sesoc.global.test.vo.Customer;


public interface CustomerDAO {
	// CRUD
	public int insert(Customer customer);
	public Customer selectOne(Map<String, String> customer);
	public int update(Customer customer);
	public int delete(String custid);
	public List<Customer> select();
	
}
