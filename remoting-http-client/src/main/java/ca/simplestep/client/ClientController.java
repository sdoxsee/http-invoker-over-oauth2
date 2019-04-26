package ca.simplestep.client;

import ca.simplestep.api.Booking;
import ca.simplestep.api.BookingException;
import ca.simplestep.api.CabBookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static java.lang.System.out;

@RestController
public class ClientController {

    @Autowired
    private CabBookingService cabBookingService;

    @GetMapping("/")
    public Mono<String> index() throws BookingException {
        Booking x = cabBookingService.bookRide("13 Seagate Blvd, Key Largo, FL 33037");
        out.println(x);
        return Mono.justOrEmpty(x.toString());
    }

    @Autowired
    private WebClient webClient;

    @GetMapping("/hello")
    public Mono<String> hello(
            @RegisteredOAuth2AuthorizedClient("client-id") OAuth2AuthorizedClient authorizedClient
    ) throws BookingException {
        out.println("Access Token: " + authorizedClient.getAccessToken().getTokenValue());
        return this.webClient
                .get()
                .uri("http://localhost:8080/hello")
                .exchange()
                .flatMap(response -> response.bodyToMono(String.class));
    }
}
