package com.company.service;

import java.util.List;
import java.util.Optional;
import java.util.function.IntPredicate;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.exceptions.ResourceNotFoundException;
import com.company.model.Product;
import com.company.repository.ProductRepository;


@Service
@Transactional

public class ProductServiceImpl implements ProductService{

	
	@Autowired
	private ProductRepository productRepository;
	private Long id;
	
	@Override
	public Product createProduct(Product product) {
		return productRepository.save(product);
	}

	@Override
	public Product updateProduct(Product product, long id) {
		
	Optional<Product> optionalProduct = this.productRepository.findById(id); // 
	Product toUpdate = optionalProduct.get(); // not mocked
	toUpdate.setId(product.getId());
	toUpdate.setName(product.getName());
	toUpdate.setDescription(product.getDescription());
	
	return this.productRepository.save(toUpdate); // mocked
	}
		
	

	@Override
	public List<Product> getAllProduct() {
		return this.productRepository.findAll();
	}

	@Override
	public Product getProductById(long productId) {
		return this.productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("product not found"));
	}


	
	
	@Override
	public boolean deleteProduct(long id) {
		
		this.productRepository.deleteById(id);
		
		boolean exists = this.productRepository.existsById(id);
		
		return !exists;
	}

	@Override
	public Product getProductByName(String name) {
		return this.productRepository.findByName(name);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	
	



	}


