package com.company.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.company.model.Product;
import com.company.service.ProductService;

@RestController
public class ProductController {
	
	@Autowired
	private ProductService productService;
	
	@GetMapping("/getAllProduct")
	public ResponseEntity<List<Product>> getAllProduct(){
		return ResponseEntity.ok().body(productService.getAllProduct());
		
	}
	
	@GetMapping("/getProduct/{id}")
	public ResponseEntity<Product> getProductById(@PathVariable long id){
		return ResponseEntity.ok().body(productService.getProductById(id));
		
	}

	@PostMapping("/createProduct")
	public ResponseEntity<Product> createProduct(@RequestBody Product product){
		
		
		Product responseBody = this.productService.createProduct(product);
		ResponseEntity<Product> response = new ResponseEntity<Product>(responseBody, HttpStatus.CREATED);
		return response;
		
		
	}
	
	@PutMapping("/updateProduct/{id}")
	public ResponseEntity<Product> updateProduct(@PathVariable long id, @RequestBody Product product){
		Product responseBody = this.productService.updateProduct(product, id); 
		return new ResponseEntity<Product>(responseBody, HttpStatus.ACCEPTED);
		
    }
	
	
	@DeleteMapping("/removeProduct/{id}")
	public ResponseEntity<?> deleteProduct(@PathVariable long id) {
		boolean deleted = this.productService.deleteProduct(id);
		if (deleted) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT); 
		} else {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		
	
		
		}
	}

}