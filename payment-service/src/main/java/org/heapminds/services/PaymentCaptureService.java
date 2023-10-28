package org.heapminds.services;

import java.util.HashMap;

import org.heapminds.bo.LoginBo;
import org.heapminds.bo.OrderBo;
import org.heapminds.bo.PaymentSuccessBO;
import org.heapminds.events.producer.PaymentSuccessProducer;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PaymentCaptureService {

    @Value("${payment.key}")
    private String paymentKey;

    @Value("${payment.secret}")
    private String paymentSecret;

    private final String baseUrl = "http://localhost:7999/api/v1/auth/login";

    @Autowired
    private PaymentSuccessProducer paymentSuccessProducer;

    @Autowired
    WebClient.Builder webClientBuilder;

    Logger log = LoggerFactory.getLogger(PaymentCaptureService.class);

    public OrderBo capturePayment(String fcmToken) throws RazorpayException {
        // Object data =
        int payment_capture = 1;
        int amount = 189;
        String currency = "INR";
        RazorpayClient razorpay = new RazorpayClient(paymentKey, paymentSecret);
        JSONObject options = new JSONObject();
        options.put("amount", amount * 100);
        options.put("currency", currency);
        options.put("receipt", Double.toString(Math.random()));
        options.put("payment_capture", payment_capture);
        Order order = razorpay.orders.create(options);
        HashMap<String, String> parameters = new HashMap<String, String>();
        parameters.put("fcmToken", fcmToken);
        paymentSuccessProducer.sendMessageToFcmTopic(parameters);

        return orderBoMapper(order);

    }

    private OrderBo orderBoMapper(Order order) {
        OrderBo orderBo = new OrderBo();
        orderBo.setId(order.get("id"));
        orderBo.setAmount(299999);
        orderBo.setCurrency(order.get("currency"));
        orderBo.setReceipt(order.get("receipt"));
        orderBo.setStatus(order.get("status"));
        orderBo.setCreated_at(order.get("created_at"));
        return orderBo;
    }

    public void handleVerification(PaymentSuccessBO body) throws RazorpayException {
        paymentSuccessProducer.sendMessageToNotificationTopic(body);
    }

    public String test() throws InterruptedException {
        LoginBo body = new LoginBo();
        body.setPassword("Password@#password");
        body.setUsername("teja.krishna.akp@gmail.com");
        String token = webClientBuilder.baseUrl(baseUrl).build().post().bodyValue(body).retrieve()
                .bodyToMono(String.class).block();
        return token;
    }

}
