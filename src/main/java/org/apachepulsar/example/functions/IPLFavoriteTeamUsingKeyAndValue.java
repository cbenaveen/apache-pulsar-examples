package org.apachepulsar.example.functions;

import org.apache.pulsar.functions.api.Context;
import org.apache.pulsar.functions.api.Function;

/**
 * Each message will have:
 * a key, to indicate the team name
 * a value, indicate the vote value.
 *
 * For eg:
 * bin/pulsar-client produce ipl/2022/votes -k "MI" -m "9"
 * bin/pulsar-client produce ipl/2022/votes -k "CSK" -m "3"
 * bin/pulsar-client produce ipl/2022/votes -k "RCB" -m "6"
 * bin/pulsar-client produce ipl/2022/votes -k "RR" -m "10"
 * bin/pulsar-client produce ipl/2022/votes -k "DC" -m "5"
 */
public class IPLFavoriteTeamUsingKeyAndValue implements Function<String, String> {
    @Override
    public String process(String input, Context context) throws Exception {
        return null;
    }
}
