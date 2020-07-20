package br.com.codenation.service;

import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import br.com.codenation.model.OrderItem;
import br.com.codenation.model.Product;

public class OrderServiceTest {

	private OrderService orderService = new OrderServiceImpl();

	@Test
	public void testCalculateOrderValue() {
		List<OrderItem> items = new ArrayList<>();
		items.add(new OrderItem(1l, 3l));
		items.add(new OrderItem(2l, 2l));
		assertNotNull(this.orderService.calculateOrderValue(items));
	}
	
	@Test
	public void testCalculateOrderValueWithDiscount() {
	    List<OrderItem> items = new ArrayList<>();
	    items.add(new OrderItem(7l, 2l));
	    Double expected = this.orderService.calculateOrderValue(items);
	    Assert.assertEquals(57.6, expected, 0.0);
	}
	
	@Test
	public void testCalculateOrderValueWithoutDiscount() {
	    List<OrderItem> items = new ArrayList<>();
	    items.add(new OrderItem(1l, 2l));
	    Double expected = this.orderService.calculateOrderValue(items);
	    Assert.assertEquals(500.0, expected, 0.0);
	}
	
	@Test
	public void testCalculateOrderValueWithAnWithoutDiscount() {
	    List<OrderItem> items = new ArrayList<>();
	    items.add(new OrderItem(1l, 2l));
	    items.add(new OrderItem(7l, 2l));
	    Double expected = this.orderService.calculateOrderValue(items);
	    Assert.assertEquals(557.6, expected, 0.0);
	}

	@Test
	public void testFindProductsById() {
		assertNotNull(this.orderService.findProductsById(Arrays.asList(1l, 2l, 3l, 4l, 5l)).size());
	}

	@Test
	public void testCalculateMultipleOrders() {
		List<OrderItem> items = new ArrayList<>();
		items.add(new OrderItem(1l, 3l));
		items.add(new OrderItem(2l, 2l));
		List<OrderItem> items2 = new ArrayList<>();
		items.add(new OrderItem(1l, 3l));
		items.add(new OrderItem(2l, 2l));
		List<OrderItem> items3 = new ArrayList<>();
		items.add(new OrderItem(1l, 3l));
		items.add(new OrderItem(2l, 2l));
		assertNotNull(this.orderService.calculateMultipleOrders(Arrays.asList(items, items2, items3)));
	}

	@Test
	public void testGroupProducts() {
		Map<Boolean, List<Product>> groupedProducts = this.orderService.groupProductsBySale(Arrays.asList(1l, 2l, 12l));
		assertNotNull(groupedProducts.get(true));
		assertNotNull(groupedProducts.get(false));
	}

}
