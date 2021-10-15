package com.company;

	import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
	import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
	import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
	import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
	import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
	import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

	import java.util.List;

	import org.junit.jupiter.api.Test;
	import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
	import org.springframework.boot.test.context.SpringBootTest;
	import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
	import org.springframework.http.MediaType;
	import org.springframework.test.context.ActiveProfiles;
	import org.springframework.test.context.jdbc.Sql;
	import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
	import org.springframework.test.web.servlet.MockMvc;
	import org.springframework.test.web.servlet.MvcResult;
	import org.springframework.test.web.servlet.RequestBuilder;
	import org.springframework.test.web.servlet.ResultMatcher;

	import com.fasterxml.jackson.databind.ObjectMapper;
	import com.company.model.*;

	@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
	@AutoConfigureMockMvc
	@Sql(scripts = { "classpath:product-schema.sql",
			"classpath:product-data.sql" }, executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
	@ActiveProfiles("test")
	public class ProductIntegrationTest {

		@Autowired 
		private MockMvc mvc; 

		@Autowired
		private ObjectMapper mapper;

		@Test
		void testCreate() throws Exception {
			final Product testProduct = new Product("Granny Smith", "Green and Delicious", 10.0f);
			String testProductAsJSON = this.mapper.writeValueAsString(testProduct);

			final Product savedProduct = new Product(1, "Granny Smith", "Green and Delicious", 10.0f);
			String savedProductAsJSON = this.mapper.writeValueAsString(savedProduct);

			
			RequestBuilder request = post("/createProduct").contentType(MediaType.APPLICATION_JSON)
					.content(testProductAsJSON);

			ResultMatcher checkStatus = status().isCreated();
			ResultMatcher checkContent = content().json(savedProductAsJSON);

			this.mvc.perform(request).andExpect(checkStatus).andExpect(checkContent);

			
			MvcResult result = this.mvc.perform(request).andExpect(checkStatus).andReturn();

			String responseJSON = result.getResponse().getContentAsString();

			@SuppressWarnings("unused")
			Product created = this.mapper.readValue(responseJSON, Product.class);


		}

		@Test
		void testGetAll() throws Exception {
			String savedProductAsJSON = this.mapper
					.writeValueAsString(List.of(new Product(2,"Granny Smith", "Green and Delicious", 10.0f)));

			RequestBuilder request = get("/getAllProduct");

			ResultMatcher checkStatus = status().isOk();
			ResultMatcher checkContent = content().json(savedProductAsJSON);

			this.mvc.perform(request).andExpect(checkStatus).andExpect(checkContent);
		}

		@Test
		void testGetById() throws Exception {
			final Product savedProduct = new Product(1, "Strawberry", "Sweet and Juicy", 450.0f);
			String savedProductAsJSON = this.mapper.writeValueAsString(savedProduct);

			RequestBuilder request = get("/getProduct/" + savedProduct.getId());

			ResultMatcher checkStatus = status().isOk();
			ResultMatcher checkContent = content().json(savedProductAsJSON);

			this.mvc.perform(request).andExpect(checkStatus).andExpect(checkContent);
		}

		@Test
		void testUpdate() throws Exception {
			final Product testProduct = new Product(1, "Kiwi", "Green and Golden", 1.0f);
			final String testProductAsJSON = this.mapper.writeValueAsString(testProduct);

			RequestBuilder request = put("/updateProduct/1").contentType(MediaType.APPLICATION_JSON)
					.content(testProductAsJSON);

			ResultMatcher checkStatus = status().isAccepted();
			ResultMatcher checkContent = content().json(testProductAsJSON);

			this.mvc.perform(request).andExpect(checkStatus).andExpect(checkContent);
		}

		@Test
		void testDelete() throws Exception {
			this.mvc.perform(delete("/removeProduct/1")).andExpect(status().isNoContent());
		}

		void testCreateAbrdiged() throws Exception {
			final String testProductAsJSON = this.mapper
					.writeValueAsString(new Product("Strawberry", "Sweet and Juicy", 5.0f));
			final String savedProductAsJSON = this.mapper.writeValueAsString(new Product("Strawberry", "Sweet and Juicy", 5.0f));

			this.mvc.perform(post("/createProduct").contentType(MediaType.APPLICATION_JSON).content(testProductAsJSON))
					.andExpect(status().isCreated()).andExpect(content().json(savedProductAsJSON));

		}
	}
	
	
	
	

