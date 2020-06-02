package com.client;

import com.config.NbpApiConfig;
import com.domain.externalDto.ResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import java.net.URI;

@Component
public class NbpApiClient {

    @Autowired
    private NbpApiConfig nbpApiConfig;

    @Autowired
    private RestTemplate restTemplate;

    public double getCurrencyFactor(String code) {
        code = code.toLowerCase();
        if (code.equals("pln")) {
            return 1;
        } else {
            URI url = UriComponentsBuilder.fromHttpUrl(nbpApiConfig.getNbpApiEndpoint() + code)
                    .build()
                    .encode()
                    .toUri();
            ResponseDto response = restTemplate.getForObject(url, ResponseDto.class);
            return response.getRates()[0].getMid();
        }
    }
}
