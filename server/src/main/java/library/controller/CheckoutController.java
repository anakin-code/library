package library.controller;

import jakarta.validation.Valid;
import library.dto.request.CheckinRequest;
import library.dto.request.CheckoutRequest;
import library.dto.response.AdminCheckoutResponse;
import library.dto.response.CheckoutResponse;
import library.service.CheckoutService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173")
public class CheckoutController {
    private final CheckoutService checkoutService;

    public CheckoutController(CheckoutService checkoutService) {
        this.checkoutService = checkoutService;
    }

    @PostMapping("/checkout")
    @ResponseStatus(HttpStatus.CREATED)
    public CheckoutResponse checkout(@Valid @RequestBody CheckoutRequest request) {
        return checkoutService.checkout(request);
    }

    @PatchMapping("/checkin")
    public CheckoutResponse checkin(@Valid @RequestBody CheckinRequest request) {
        return checkoutService.checkin(request);
    }

    @GetMapping("/users/{hrid}/checkouts")
    public List<CheckoutResponse> getActiveCheckouts(@PathVariable String hrid) {
        return checkoutService.getActiveCheckouts(hrid);
    }

    @GetMapping("/users/{hrid}/checkout-histories")
    public List<CheckoutResponse> getHistory(@PathVariable String hrid) {
        return checkoutService.getHistory(hrid);
    }

    @GetMapping("/admin/checkouts")
    public List<AdminCheckoutResponse> getAdminActiveCheckouts() {
        return checkoutService.getAdminActiveCheckouts();
    }
}
