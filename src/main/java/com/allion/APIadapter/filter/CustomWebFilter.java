package com.allion.APIadapter.filter;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import io.github.bucket4j.Refill;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class CustomWebFilter implements WebFilter {


	private Map<String, Bucket> ratelimitingcache = new HashMap<>();

	private Bucket createSessionRateLimitBucket() {
//        Bandwidth limit = Bandwidth.simple(5, Duration.ofMinutes(1));
		Refill refill = Refill.intervally(20, Duration.ofMinutes(1));
		Bandwidth limit = Bandwidth.classic(20, refill);
		Bucket bucket = Bucket4j.builder()
				.addLimit(limit)
				.build();
//        return Bucket4j.builder().addLimit(limit).build();
		return bucket;
	}

	private Bucket createIpRateLimitBucket() {
//        Bandwidth limit = Bandwidth.simple(5, Duration.ofMinutes(1));
//        return Bucket4j.builder().addLimit(limit).build();
		Refill refill = Refill.intervally(2, Duration.ofMinutes(1));
		Bandwidth limit = Bandwidth.classic(2, refill);
		Bucket bucket = Bucket4j.builder()
				.addLimit(limit)
				.build();
		return bucket;
	}

	@Override
	public Mono<Void> filter(ServerWebExchange serverWebExchange,
			WebFilterChain webFilterChain) {
		System.out.println("Invoked Mono filter Time " + new Date());

		String sourceIP = serverWebExchange.getRequest().getRemoteAddress().getHostName();
		if (ratelimitingcache.containsKey(sourceIP)) {
			System.out.println("Available IP tokens left: " + ratelimitingcache.get(sourceIP).getAvailableTokens());
			System.out.println("Time " + new Date());
			if (!ratelimitingcache.get(sourceIP).tryConsume(1)) {
				serverWebExchange.getResponse().setStatusCode(HttpStatus.BANDWIDTH_LIMIT_EXCEEDED);
				return Mono.empty();
			}
		} else {
			System.out.println("Creating new IP bucket... time " + new Date());
			ratelimitingcache.put(sourceIP, createIpRateLimitBucket());
		}

//        return serverWebExchange.getSession()
//                                // use flatmap to extract the WebSession object from serverWebExchange
//                                .flatMap(webSession -> {
//                                    if (webSession.getAttributes().containsKey("bucket")) {
//                                        // if it does - extract the bucket from the session
//                                        Bucket bucket = (Bucket) webSession.getAttributes().get("bucket");
//                                        // consume a token
//                                        if (bucket.tryConsume(1)) {
//                                            // if allowed - i.e. not over the allocated rate,
//                                            // then pass request on to the next filter in the chain
//                                            System.out.println(
//                                                "Available session tokens left : " + bucket.getAvailableTokens());
//                                            return webFilterChain.filter(serverWebExchange);
//                                        } else {
//                                            // if not allowed then modify response code and immediately return to client
//                                            serverWebExchange.getResponse()
//                                                             .setStatusCode(HttpStatus.BANDWIDTH_LIMIT_EXCEEDED);
//                                            return Mono.empty();
//                                        }
//                                    } else {
//                                        // if bucket does not exist create a new one
//                                        Bucket bucket = createSessionRateLimitBucket();
//                                        System.out.println("Creating new session bucket...Time " + new Date());
//                                        // save bucket to session
//                                        webSession.getAttributes().put("bucket", bucket);
//                                        bucket.tryConsume(1);
//                                        // pass on the request to the next filter in the chain
//                                        return webFilterChain.filter(serverWebExchange);
//                                    }
//                                });
		return webFilterChain.filter(serverWebExchange);
	}
}
