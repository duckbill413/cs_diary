package wh.duckbill.netflix.interceptor;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.WebRequestInterceptor;
import wh.duckbill.netflix.authentication.AuthenticationHolder;
import wh.duckbill.netflix.authentication.RequestedBy;

@Component
@RequiredArgsConstructor
public class RequestedByInterceptor implements WebRequestInterceptor {
    public static final String REQUESTED_BY_HEADER = "requested-by";
    private final AuthenticationHolder authenticationHolder;
    @Override
    public void preHandle(WebRequest request) throws Exception {
        String requestedBy = request.getHeader(REQUESTED_BY_HEADER);
        RequestedBy requested = new RequestedBy(requestedBy);
        authenticationHolder.setAuthentication(requested);
    }

    @Override
    public void postHandle(WebRequest request, ModelMap model) throws Exception {

    }

    @Override
    public void afterCompletion(WebRequest request, Exception ex) throws Exception {

    }
}
