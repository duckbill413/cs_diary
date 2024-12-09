package wh.duckbill.netflix.sample;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import wh.duckbill.netflix.sample.response.SampleResponse;

@Service
@RequiredArgsConstructor
public class SampleService implements SearchSampleUsecase {
    private final SamplePort samplePort;
    private final SamplePersistencePort samplePersistencePort;

    @Override
    public SampleResponse getSample() {
        SamplePortResponse sample = samplePort.getSample();
        String sampleName = samplePersistencePort.getSampleName("1");
        return new SampleResponse(sample.getName());
    }
}
