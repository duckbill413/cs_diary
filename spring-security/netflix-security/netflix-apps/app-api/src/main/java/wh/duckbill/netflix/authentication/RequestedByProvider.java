package wh.duckbill.netflix.authentication;

import java.util.Optional;

public interface RequestedByProvider {
    Optional<String> getRequestedBy();
}
