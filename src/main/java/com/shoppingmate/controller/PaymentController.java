package com.shoppingmate.controller;

import com.razorpay.Payment;
import com.razorpay.PaymentLink;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.shoppingmate.exception.OrderException;
import com.shoppingmate.model.Order;
import com.shoppingmate.repository.OrderRepository;
import com.shoppingmate.response.ApiResponse;
import com.shoppingmate.response.PaymentLinkResponse;
import com.shoppingmate.service.OrderService;
import com.shoppingmate.service.UserService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class PaymentController {
    @Value("${razorpay.api.key}")
    String apiKey;

    @Value("${razorpay.api.secret}")
    String apiSecret;

    @Autowired
    OrderService orderService;
    @Autowired
    UserService userService;
    @Autowired
    OrderRepository orderRepository;

    public PaymentController( OrderService orderService, UserService userService, OrderRepository orderRepository) {
        this.orderService = orderService;
        this.userService = userService;
        this.orderRepository = orderRepository;
    }

    @PostMapping("/payments/{orderId}")
    public ResponseEntity<PaymentLinkResponse> createPaymentLink(@PathVariable Long orderId,
                                                                 @RequestHeader("Authorization") String jwt)
            throws OrderException, RazorpayException {

        Order order = orderService.findOrderById(orderId);
        try {
            RazorpayClient razorpay = new RazorpayClient(apiKey, apiSecret);

            JSONObject paymentLinkRequest = new JSONObject();
            paymentLinkRequest.put("amount", order.getTotalDiscountedPrice() * 100);
            paymentLinkRequest.put("currency", "INR");

            JSONObject customer = new JSONObject();
            customer.put("name", order.getUser().getFirstName());
            customer.put("email", order.getUser().getEmail());
            paymentLinkRequest.put("customer", customer);

            JSONObject notify = new JSONObject();
            notify.put("sms", true);
            notify.put("email", true);
            paymentLinkRequest.put("notify", notify);

            /*    After, payment we will redirect here    */

            paymentLinkRequest.put("callback_url", "http://localhost:3000/payment/" + orderId);
            paymentLinkRequest.put("callback_method", "get");

            PaymentLink payment = razorpay.paymentLink.create(paymentLinkRequest);
            String paymentLinkId = payment.get("id");
            String paymentLinkUrl = payment.get("short_url");

            PaymentLinkResponse res = new PaymentLinkResponse();
            res.setPayment_link_id(paymentLinkId);
            res.setPayment_link_url(paymentLinkUrl);

            return new ResponseEntity<PaymentLinkResponse>(res, HttpStatus.CREATED);

        } catch (Exception e) {
            throw new RazorpayException(e.getMessage());
        }

    }
    @GetMapping("/payments")
    public ResponseEntity<ApiResponse> redirect(@RequestParam(name = "payment_id") String paymentId,
                                                @RequestParam(name = "order_id") Long orderId) throws OrderException, RazorpayException {

        Order order = orderService.findOrderById(orderId);
        RazorpayClient razorpay = new RazorpayClient(apiKey, apiSecret);
        try {
            Payment payment = razorpay.payments.fetch(paymentId);

            if (payment.get("status").equals("captured")) {
                order.getPaymentDetails().setPaymentId(paymentId);
                order.getPaymentDetails().setStatus("COMPLETED");
                order.setOrderStatus("PLACED");
                orderRepository.save(order);
            }
            ApiResponse res = new ApiResponse();
            res.setMessage("Your Order Get PLACED");
            res.setSuccess(true);

            return new ResponseEntity<ApiResponse>(res, HttpStatus.ACCEPTED);
        } catch (Exception e) {
            throw new RazorpayException(e.getMessage());
        }

    }

}
