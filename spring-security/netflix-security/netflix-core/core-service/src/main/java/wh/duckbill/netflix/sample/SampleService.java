package wh.duckbill.netflix.sample;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import wh.duckbill.netflix.sample.response.SampleResponse;

@Service
@RequiredArgsConstructor
public class SampleService implements SearchSampleUsecase {
    private final SamplePort samplePort;

    @Override
    public SampleResponse getSample() {
        SamplePortResponse sample = samplePort.getSample();
        return new SampleResponse(sample.getName());
    }
}
