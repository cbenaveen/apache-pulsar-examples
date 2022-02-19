package org.apachepulsar.example.functions;

import org.apache.pulsar.functions.api.Context;
import org.apache.pulsar.functions.api.Function;
import org.slf4j.Logger;

import java.nio.ByteBuffer;
import java.util.Objects;

/**
 * Each input message will be of format: "MI:1", where MI stands for the team name, the integer after
 * : stands for the vote count.
 *
 * Output format will also be similar, instead of returning vote in the message, we will
 * return aggregated vote
 *
 * INPUT:
 *  MI:1
 *  MI:3
 *
 * Output:
 *  MI:4
 */
public class IPLFavoriteTeam implements Function<String, String> {
    @Override
    public String process(String input, Context context) throws Exception {
        if (Objects.isNull(input) || input.length() <= 0) {
            throw new IllegalArgumentException("Invalid input");
        }

        if (!input.contains(":")) {
            throw new IllegalArgumentException("Message is not in the expected format");
        }

        Logger logger = context.getLogger();

        final String[] splitMessage = input.split(":");
        final String teamName = splitMessage[0];
        final int vote = Integer.parseInt(splitMessage[1]);
        logger.info("Team Name {}, vote {}" + teamName, vote);

        ByteBuffer teamState = context.getState(teamName);
        if (Objects.isNull(teamState)) {
            teamState = ByteBuffer.allocate(56);
        }

        int anInt = teamState.getInt(0);
        logger.info("Current vote count for Team Name {} is {}",teamName, anInt);
        if (anInt <= 0) {
            teamState.putInt(0, vote);
        } else {
            teamState.putInt(0, (anInt + vote));
        }
        context.putState(teamName, teamState);
        logger.info("Aggregated vote count for Team Name {} is {}", teamName,
                teamState.getInt(0));

        return teamName + ": " + teamState.getInt(0);
    }
}
