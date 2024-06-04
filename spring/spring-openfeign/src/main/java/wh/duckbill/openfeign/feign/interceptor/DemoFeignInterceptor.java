package wh.duckbill.openfeign.feign.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpMethod;

import java.nio.charset.StandardCharsets;

@RequiredArgsConstructor(staticName = "of")
public class DemoFeignInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate requestTemplate) {
        // GET 요청일 경우
        if (requestTemplate.method().equals(HttpMethod.GET.name())) {
            System.out.println("[GET] [DemoFeignInterceptor] queries: " + requestTemplate.queries());
            return;
        }

        // POST 요청일 경우
        String encodedRequestBody = StringUtils.toEncodedString(requestTemplate.body(), StandardCharsets.UTF_8);
        System.out.println("[POST] [DemoFeignInterceptor] requestBody: " + encodedRequestBody);

        // 추가적으로 필요한 로직을 수행
        String covertRequestBody = encodedRequestBody;
        requestTemplate.body(covertRequestBody);
    }
}
