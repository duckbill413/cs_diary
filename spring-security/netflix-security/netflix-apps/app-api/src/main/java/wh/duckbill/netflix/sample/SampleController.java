package wh.duckbill.netflix.sample;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import wh.duckbill.netflix.sample.response.SampleResponse;

@RestController
@RequiredArgsConstructor
public class SampleController {
    private final SearchSampleUsecase searchSampleUsecase;

    @GetMapping("/api/v1/sample")
    public SampleResponse getSample() {
        return searchSampleUsecase.getSample();
    }
}
