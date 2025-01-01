package com.example.demo.controller;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import com.example.demo.dto.ProductResponse;
import com.example.demo.entity.Product;
import com.example.demo.entity.SpacificProduct;
import com.example.demo.service.ProductService;

public class ProductControllerTest {
    @Mock
    private ProductService productService;

    @Mock
    private ProductResponse productResponse; // Mock object ของ ProductResponse

    @InjectMocks
    private ProductController productController;

    private List<Product> products;
    private Long productTotal;

    @BeforeEach
    public void setUp() {
        // สร้างข้อมูล Mock
        MockitoAnnotations.openMocks(this); // เปิด mock object
        Product product1 = new Product();
        SpacificProduct spacificProduct1 = new SpacificProduct();
        product1.setId(1L);
        product1.setName("Product 1");
        product1.setPrice(9.99);
        spacificProduct1.setColor("Red");
        spacificProduct1.setWeight(10.0);
        spacificProduct1.setDimensions("10x10x10");
        product1.setSpacificProduct(spacificProduct1);

        Product product2 = new Product();
        SpacificProduct spacificProduct2 = new SpacificProduct();
        product2.setId(2L);
        product2.setName("Product 2");
        product2.setPrice(19.99);
        spacificProduct2.setColor("Blue");
        spacificProduct2.setWeight(20.0);
        spacificProduct2.setDimensions("20x20x20");
        product2.setSpacificProduct(spacificProduct2);

        products = Arrays.asList(product1, product2);
        productTotal = (long) products.size();
    }

    @Test
    public void testGetAllProducts() {
        // กำหนดพฤติกรรมของ Mock
        when(productService.getAllProducts()).thenReturn(products);
        when(productResponse.getTotalProducts()).thenReturn(productTotal);

        // เรียกใช้เมธอดที่ต้องการทดสอบ
        ProductResponse responseEntity = productController.getAllProducts();

        // ตรวจสอบจำนวนสินค้าที่ส่งคืน
        assertNotNull(responseEntity);
        assertEquals(productResponse, responseEntity);

        // ตรวจสอบผลลัพธ์
        verify(productService).getAllProducts();
        verify(productResponse).setProducts(products);
        verify(productResponse).setTotalProducts(2L);

        verify(productResponse).setProducts(products);
        verify(productResponse).setTotalProducts(products.size());
        System.out.println("responseEntity: " + responseEntity.getProducts());

    }

}
