package wh.duckbill.netflix.audit;

import java.util.Optional;

public interface RequestedByProvider {
    Optional<String> getRequestedBy();
}
