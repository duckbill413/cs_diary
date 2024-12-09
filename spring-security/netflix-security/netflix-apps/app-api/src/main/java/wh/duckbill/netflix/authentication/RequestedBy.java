package wh.duckbill.netflix.authentication;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RequestedBy implements Authentication{
    private final String requestedBy;
}
