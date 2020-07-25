package ir.haji.nutro.panel.notification.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by jalil on 1/8/2020.
 */

public class SubscriptionEndpoint {
    private final String endpoint;

    @JsonCreator
    public SubscriptionEndpoint(@JsonProperty("endpoint") String endpoint) {
        this.endpoint = endpoint;
    }

    public String getEndpoint() {
        return this.endpoint;
    }

    @Override
    public String toString() {
        return "SubscriptionEndpoint [endpoint=" + this.endpoint + "]";
    }

}