package org.heapminds.controller;

import java.util.concurrent.CompletableFuture;

import org.heapminds.bo.LoginBo;
import org.heapminds.bo.OrderBo;
import org.heapminds.bo.PaymentSuccessBO;
import org.heapminds.exception.PaymentException;
import org.heapminds.ro.BaseRo;
import org.heapminds.ro.BaseSro;
import org.heapminds.services.PaymentCaptureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.razorpay.RazorpayException;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j

public class SubscriptionController {

    @Autowired
    PaymentCaptureService paymentCaptureService;

    @GetMapping("/api/handleSubscription/mbbsMembership/{fcm}")
    @CircuitBreaker(name = "inventory", fallbackMethod = "serviceFallback")
    public CompletableFuture<BaseRo> handleSubscription(@PathVariable String fcm) {
        try {
            LoginBo loginBo = new LoginBo();
            loginBo.setUsername("teja.krishna.akp@gmail.com");
            loginBo.setPassword("Password@#password");
            OrderBo result = paymentCaptureService.capturePayment(fcm);
            return CompletableFuture.supplyAsync(() -> new BaseSro<OrderBo>(result));
        } catch (Exception e) {
            throw new PaymentException(e.getMessage());
        }

    }

    int a = 1;

    @PostMapping("/api/handleSubscription/test")
    // @CircuitBreaker(name = "inventory", fallbackMethod = "fallbackMethod")
    // @TimeLimiter(name = "inventory")
    @Retry(name = "inventory", fallbackMethod = "fallbackMethod")
    public CompletableFuture<String> method(@RequestBody String name) throws InterruptedException {
        a++;
        log.info("Retried for times : {}", a);
        return CompletableFuture.supplyAsync(() -> {
            try {
                return paymentCaptureService.test();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return "wed";
        });
    }

    public CompletableFuture<String> fallbackMethod(String orderLineItems,
            RuntimeException runtimeException) {
        log.info("EXCEPTION_MESSAGE {}", runtimeException.getMessage());
        return CompletableFuture.supplyAsync(() -> "Something went wrong, please try after sometime");
    }

    @ResponseBody
    @PostMapping("/api/handleSubscription/verify")
    public void handleVerification(@RequestBody PaymentSuccessBO body) throws RuntimeException, RazorpayException {
        log.info("PAYMENT_SUCCESS_RESPONSE: {}", body.getAccount_id());
        paymentCaptureService.handleVerification(body);

    }

    @GetMapping("/token/{fcmToken}")
    public void getFcm(@PathVariable String fcmToken) {
        System.out.println(fcmToken);
    }

    @GetMapping("/api/log")
    public String log() {
        log.trace("This is a TRACE level message");
        log.debug("This is a DEBUG level message");
        log.info("This is an INFO level message");
        log.warn("This is a WARN level message");
        log.error("This is an ERROR level message");
        return "See the log for details";
    }

}
