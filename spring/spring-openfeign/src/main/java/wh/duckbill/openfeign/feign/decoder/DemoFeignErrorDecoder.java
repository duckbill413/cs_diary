package wh.duckbill.openfeign.feign.decoder;

import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.http.HttpStatus;

public class DemoFeignErrorDecoder implements ErrorDecoder {
    private final ErrorDecoder errorDecoder = new Default();

    @Override
    public Exception decode(String methodKey, Response response) {
        HttpStatus httpStatus = HttpStatus.resolve(response.status());

        if (httpStatus == HttpStatus.NOT_FOUND) {
            System.out.println("[DemoFeignErrorDecoder] HttpStatus = " + httpStatus);
            throw new RuntimeException(
                    String.format("[RuntimeException] HttpStatus is %s", httpStatus)
            ); // NOT FOUND 에러에 대하여
        }

        return errorDecoder.decode(methodKey, response); // 정의 하지 않은 에러에 대한 처리
    }
}
