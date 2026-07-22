package dev.moproco.icedlatte.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import dev.moproco.icedlatte.OrderService;
import dev.moproco.icedlatte.domain.Order;
import dev.moproco.icedlatte.repository.OrderRepository;
import dev.moproco.icedlatte.domain.OrderStatusHistory;
import dev.moproco.icedlatte.repository.OrderStatusHistoryRepository;
import dev.moproco.icedlatte.domain.OrderAddress;
import dev.moproco.icedlatte.repository.OrderAddressRepository;
import dev.moproco.icedlatte.domain.OrderItem;
import dev.moproco.icedlatte.repository.OrderItemRepository;
import dev.moproco.icedlatte.domain.ShoppingCart;
import dev.moproco.icedlatte.repository.ShoppingCartRepository;
import dev.moproco.icedlatte.domain.ShoppingCartItem;
import dev.moproco.icedlatte.repository.ShoppingCartItemRepository;
import dev.moproco.icedlatte.dto.CheckoutOrderRequest;
import dev.moproco.icedlatte.dto.OrderSnapshot;
import dev.moproco.icedlatte.dto.OrderStatusHistorySnapshot;
import dev.moproco.icedlatte.domain.OrderStatus;


@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderStatusHistoryRepository orderStatusHistoryRepository;
    private final OrderAddressRepository orderAddressRepository;
    private final OrderItemRepository orderItemRepository;
    private final ShoppingCartRepository shoppingCartRepository;
    private final ShoppingCartItemRepository shoppingCartItemRepository;

    public OrderServiceImpl(OrderRepository orderRepository, OrderStatusHistoryRepository orderStatusHistoryRepository, OrderAddressRepository orderAddressRepository, OrderItemRepository orderItemRepository, ShoppingCartRepository shoppingCartRepository, ShoppingCartItemRepository shoppingCartItemRepository) {
        this.orderRepository = orderRepository;
        this.orderStatusHistoryRepository = orderStatusHistoryRepository;
        this.orderAddressRepository = orderAddressRepository;
        this.orderItemRepository = orderItemRepository;
        this.shoppingCartRepository = shoppingCartRepository;
        this.shoppingCartItemRepository = shoppingCartItemRepository;
    }

    /**
     * @prompt Retrieves all orders for the user by userId. Returns a list of OrderSnapshot with id, userId, status, recipientName, recipientSurname, recipientPhone, itemsQuantity, itemsTotalPrice. Sorted by createdAt descending.
     * @generated NOT
     */
    @Override
    @Transactional
    public List<OrderSnapshot> getOrders(Long userId) {
        // generated start
List<Order> orders = orderRepository.findByUserId(userId);
    return orders.stream()
            .sorted((o1, o2) -> o2.getCreatedAt().compareTo(o1.getCreatedAt()))
            .map(order -> new OrderSnapshot(
                    order.getUserId(),
                    order.getStatus(),
                    order.getRecipientName(),
                    order.getRecipientSurname(),
                    order.getRecipientPhone(),
                    order.getItemsQuantity(),
                    order.getItemsTotalPrice()))
            .collect(java.util.stream.Collectors.toList());
// generated end
    }

    /**
     * @prompt Creates a new order from the user's shopping cart by userId. Retrieves the ShoppingCart and its ShoppingCartItems. Creates Order, OrderItem for each ShoppingCartItem, and OrderAddress from request.address. Sets status to CREATED, computes itemsQuantity and itemsTotalPrice from cart items. Closes the ShoppingCart (sets closedAt). Returns an OrderSnapshot.
     * @generated NOT
     */
    @Override
    @Transactional
    public OrderSnapshot createOrder(Long userId, CheckoutOrderRequest request) {
        // generated start
ShoppingCart cart = shoppingCartRepository.findByUserId(userId).stream()
        .filter(c -> c.getClosedAt() == null)
        .findFirst()
        .orElseThrow(() -> new org.springframework.web.server.ResponseStatusException(
                org.springframework.http.HttpStatus.NOT_FOUND, "Active shopping cart not found for user"));

    List<ShoppingCartItem> cartItems = cart.getItems();

    Order order = new Order();
    order.setUserId(userId);
    order.setStatus(OrderStatus.CREATED);
    order.setRecipientName(request.recipientName());
    order.setRecipientSurname(request.recipientSurname());
    order.setRecipientPhone(request.recipientPhone());

    int itemsQuantity = cartItems.stream().mapToInt(ShoppingCartItem::getProductQuantity).sum();
    double itemsTotalPrice = 0.0;
    order.setItemsQuantity(itemsQuantity);
    order.setItemsTotalPrice(itemsTotalPrice);

    order = orderRepository.save(order);

    OrderAddress address = new OrderAddress();
    address.setCountry(request.address().country());
    address.setCity(request.address().city());
    address.setLine(request.address().line());
    address.setPostcode(request.address().postcode());
    address.setOrder(order);
    address = orderAddressRepository.save(address);
    order.setAddress(address);

    for (ShoppingCartItem cartItem : cartItems) {
        OrderItem orderItem = new OrderItem();
        orderItem.setOrderId(order.getId());
        orderItem.setProductId(cartItem.getProductId());
        orderItem.setProductsQuantity(cartItem.getProductQuantity());
        orderItem.setOrder(order);
        orderItemRepository.save(orderItem);
    }

    cart.setClosedAt(java.time.LocalDateTime.now());
    shoppingCartRepository.save(cart);

    return new OrderSnapshot(order.getUserId(), order.getStatus(), order.getRecipientName(),
            order.getRecipientSurname(), order.getRecipientPhone(), order.getItemsQuantity(),
            order.getItemsTotalPrice());
// generated end
    }

    /**
     * @prompt Retrieves a single order by its ID. The 'orderId' parameter is the order ID. Looks up the Order by orderId. Returns an OrderSnapshot with id, userId, status, recipientName, recipientSurname, recipientPhone, itemsQuantity, itemsTotalPrice. Throws NotFoundException if not found.
     * @generated NOT
     */
    @Override
    @Transactional
    public OrderSnapshot getOrderById(Long orderId) {
        // generated start
Order order = orderRepository.findById(orderId)
        .orElseThrow(() -> new org.springframework.web.server.ResponseStatusException(
                org.springframework.http.HttpStatus.NOT_FOUND, "Order not found"));
    return new OrderSnapshot(order.getUserId(), order.getStatus(), order.getRecipientName(),
            order.getRecipientSurname(), order.getRecipientPhone(), order.getItemsQuantity(),
            order.getItemsTotalPrice());
// generated end
    }

    /**
     * @prompt Cancels an order by orderId. Only allowed if status is PENDING_PAYMENT or PAID. Sets the Order's status to CANCELLED. Creates and saves an OrderStatusHistory entry with oldStatus=previous status, newStatus=CANCELLED, changedBy='system'. Does not return anything (void).
     * @generated NOT
     */
    @Override
    @Transactional
    public void cancelOrder(Long orderId) {
        // generated start
Order order = orderRepository.findById(orderId)
        .orElseThrow(() -> new org.springframework.web.server.ResponseStatusException(
                org.springframework.http.HttpStatus.NOT_FOUND, "Order not found"));
    OrderStatus previousStatus = order.getStatus();
    if (previousStatus != OrderStatus.PENDING_PAYMENT && previousStatus != OrderStatus.PAID) {
        throw new IllegalArgumentException("Order can only be cancelled if status is PENDING_PAYMENT or PAID");
    }
    order.setStatus(OrderStatus.CANCELLED);
    orderRepository.save(order);
    OrderStatusHistory history = new OrderStatusHistory();
    history.setOrderId(orderId);
    history.setOldStatus(previousStatus);
    history.setNewStatus(OrderStatus.CANCELLED);
    history.setChangedBy("system");
    history.setChangedAt(java.time.LocalDateTime.now());
    orderStatusHistoryRepository.save(history);
// generated end
    }

    /**
     * @prompt Requests a refund for an order by orderId. Only allowed if status is DELIVERED. Sets the Order's status to REFUND_REQUESTED. Creates and saves an OrderStatusHistory entry with oldStatus=DELIVERED, newStatus=REFUND_REQUESTED. Does not return anything (void).
     * @generated NOT
     */
    @Override
    @Transactional
    public void requestRefund(Long orderId) {
        // generated start
Order order = orderRepository.findById(orderId)
            .orElseThrow(() -> new org.springframework.web.server.ResponseStatusException(
                    org.springframework.http.HttpStatus.NOT_FOUND, "Order not found"));
    if (order.getStatus() != OrderStatus.DELIVERED) {
        throw new IllegalArgumentException("Refund can only be requested if status is DELIVERED");
    }
    order.setStatus(OrderStatus.REFUND_REQUESTED);
    orderRepository.save(order);
    OrderStatusHistory history = new OrderStatusHistory();
    history.setOrderId(orderId);
    history.setOldStatus(OrderStatus.DELIVERED);
    history.setNewStatus(OrderStatus.REFUND_REQUESTED);
    history.setChangedAt(java.time.LocalDateTime.now());
    orderStatusHistoryRepository.save(history);
// generated end
    }

    /**
     * @prompt Creates a new shopping cart from a previous order's items by orderId. Creates new ShoppingCartItems for each OrderItem in the order. Returns the new cart ID as a String.
     * @generated NOT
     */
    @Override
    @Transactional
    public Long reorder(Long orderId) {
        // generated start
Order order = orderRepository.findById(orderId)
            .orElseThrow(() -> new org.springframework.web.server.ResponseStatusException(
                    org.springframework.http.HttpStatus.NOT_FOUND, "Order not found"));

    ShoppingCart cart = new ShoppingCart();
    cart.setUserId(order.getUserId());
    cart.setCreatedAt(java.time.LocalDateTime.now());
    cart = shoppingCartRepository.save(cart);

    List<OrderItem> orderItems = orderItemRepository.findByOrderId(orderId);
    for (OrderItem orderItem : orderItems) {
        ShoppingCartItem cartItem = new ShoppingCartItem();
        cartItem.setProductId(orderItem.getProductId());
        cartItem.setProductQuantity(orderItem.getProductsQuantity());
        cartItem.setCart(cart);
        shoppingCartItemRepository.save(cartItem);
    }

    return cart.getId();
// generated end
    }

    /**
     * @prompt Retrieves the status change history for an order by orderId. Returns a list of OrderStatusHistorySnapshot with id, oldStatus, newStatus, changedBy, reason, changedAt. Sorted by changedAt ascending.
     * @generated NOT
     */
    @Override
    @Transactional
    public List<OrderStatusHistorySnapshot> getOrderHistory(Long orderId) {
        // generated start
List<OrderStatusHistory> historyList = orderStatusHistoryRepository.findByOrderId(orderId);
    return historyList.stream()
            .sorted((h1, h2) -> h1.getChangedAt().compareTo(h2.getChangedAt()))
            .map(h -> new OrderStatusHistorySnapshot(
                    h.getOldStatus(),
                    h.getNewStatus(),
                    h.getChangedBy(),
                    h.getReason(),
                    h.getChangedAt()))
            .collect(java.util.stream.Collectors.toList());
// generated end
    }

}
