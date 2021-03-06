package br.com.codenation.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import br.com.codenation.model.OrderItem;
import br.com.codenation.model.Product;
import br.com.codenation.repository.ProductRepository;
import br.com.codenation.repository.ProductRepositoryImpl;

public class OrderServiceImpl implements OrderService {

    private static final double TWENTY_PERCENT_DISCOUNT = 0.8;
    private ProductRepository productRepository = new ProductRepositoryImpl();

    /**
     * Calculate the sum of all OrderItems
     */
    @Override
    public Double calculateOrderValue(List<OrderItem> items) {
        return items.stream()
            .mapToDouble(this::getTotalByItem)
            .sum();
    }
    
    private Double getTotalByItem(OrderItem orderItem){
        Product product = getProduct(orderItem.getProductId());
        Double price = product.getValue();
        Double quantity = orderItem.getQuantity().doubleValue();
        if (hasDiscount(product)) {
            return calculateTotalWithDiscount(price, quantity);
        }
        return calculateTotal(price, quantity);
    }
    
    private  boolean hasDiscount(Product product) {
        return product.getIsSale();
    }
    
    private  Double calculateTotal(Double price, Double quantity) {
        return price * quantity;
    }
    
    private  Double calculateTotalWithDiscount(Double price, Double quantity) {
        return (price * quantity) * TWENTY_PERCENT_DISCOUNT;
    }
    
    private Product getProduct(Long id) {
        Product p = new Product(0l, "", 0.0, false);
        return productRepository.findById(id).orElse(p);
    }

    /**
     * Map from idProduct List to Product Set
     */
    @Override
    public Set<Product> findProductsById(List<Long> ids) {
        return ids.stream()
            .map( id -> productRepository.findById(id))
            .filter(Optional::isPresent)
            .map(Optional::get).collect(Collectors.toSet());
    }

    /**
     * Calculate the sum of all Orders(List<OrderIten>)
     */
    @Override
    public Double calculateMultipleOrders(List<List<OrderItem>> orders) {
        return orders.stream()
            .mapToDouble(this::calculateOrderValue)
            .sum();
    }


    /**
     * Group products using isSale attribute as the map key
     */
    @Override
    public Map<Boolean, List<Product>> groupProductsBySale(List<Long> productIds) {
        return productIds.stream()
            .map(this::getProduct)
            .collect(Collectors.groupingBy(Product::getIsSale));
    }

}