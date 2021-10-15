package com.company;


import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import com.company.model.Product;
import com.company.repository.ProductRepository;
import com.company.service.ProductService;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class ServiceUnitTest {

	@Autowired
	private ProductService service;

	@MockBean
	private ProductRepository repo;

	@Test
	void testGetByName() {
		final String name = "Apple";
		final Product product = new Product("Granny Smith", "Green and Delicious", 4.0f); 

		Mockito.when(this.repo.findByName(name)).thenReturn(product);

		assertThat(this.service.getProductByName(name)).isEqualTo(product);

		Mockito.verify(this.repo, Mockito.times(1)).findByName(name);
	}

	@Test
	void testGetById() {
		final long Id = 1;
		final Product product = new Product(1, "Granny Smith", "Green and Delicious", 10.0f);

		Mockito.when(this.repo.findById(Id)).thenReturn(Optional.of(product));

		assertThat(this.service.getProductById(Id)).isEqualTo(product);

		Mockito.verify(this.repo, Mockito.times(1)).findById(Id);
	}

	@Test
	void testGetAllProducts() {
		final List<Product> product = List.of(new Product("Granny Smith", "Green and Delicious", 2.0f),
				new Product("Strawberry", "Sweet and Juicy", 3.0f));

		Mockito.when(this.repo.findAll()).thenReturn(product);

		assertThat(this.service.getAllProduct()).isEqualTo(product);

		Mockito.verify(this.repo, Mockito.times(1)).findAll();
	}

	@Test
	void testUpdate() { 
		final long id = 1;
		Product product = new Product(1, "Granny Smith", "Green and Delicious", 2.1f);
		Optional<Product> optionalProduct = Optional.of(product);

		Product newProduct = new Product(1, "Strawberry", "Sweet and Juicy", 1.2f);

		System.out.println(optionalProduct.get());
		Mockito.when(this.repo.findById(id)).thenReturn(optionalProduct);
		Mockito.when(this.repo.save(newProduct)).thenReturn(newProduct);

		assertThat(this.service.updateProduct(newProduct, product.getId())).isEqualTo(newProduct);

		Mockito.verify(this.repo, Mockito.times(1)).findById(id);
		Mockito.verify(this.repo, Mockito.times(1)).save(newProduct);
	}

	@Test
	void testCreate() {
		Product newProduct = new Product("Strawberry", "Sweet and Juicy", 3.1f);
		Product savedProduct = new Product("Strawberry", "Sweet and Juicy", 3.1f);

		Mockito.when(this.repo.save(newProduct)).thenReturn(savedProduct);

		assertThat(this.service.createProduct(newProduct)).isEqualTo(savedProduct);

		Mockito.verify(this.repo, Mockito.times(1)).save(newProduct);
	}

	@Test
	void testDelete() {
		final long id = 1;

		Mockito.when(this.repo.existsById(id)).thenReturn(false);
		
		assertThat(this.service.deleteProduct(id)).isEqualTo(true);

		Mockito.verify(this.repo, Mockito.times(1)).existsById(id);
	}
}
