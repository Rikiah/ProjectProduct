package com.company.service;

import java.util.List;
import java.util.function.IntPredicate;

import com.company.model.Product;

public interface ProductService {
	Product createProduct(Product product);
	
	Product updateProduct(Product product, long l);
	
	List<Product> getAllProduct();
	
	Product getProductById(long productId);
	
	boolean deleteProduct(long id);

	Product getProductByName(String name);

}