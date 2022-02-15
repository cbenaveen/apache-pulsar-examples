package org.apachepulsar.example;

import org.apache.pulsar.client.admin.PulsarAdmin;
import org.apache.pulsar.client.admin.PulsarAdminException;
import org.apache.pulsar.client.api.PulsarClientException;
import org.apache.pulsar.common.policies.data.Policies;

import java.util.List;
import java.util.Objects;

public class CreateNamespaces {
    private static final String PULSAR_ADMIN_URL = "http://localhost:8080";
    public static final String RESEARCH_AND_DEVELOPMENT = "research-and-development";

    private static PulsarAdmin getPulsarAdmin() throws PulsarClientException {
        return PulsarAdmin.builder().serviceHttpUrl(PULSAR_ADMIN_URL).build();
    }

    private static List<String> listNamespaces(final PulsarAdmin pulsarAdmin,
                                       final String tenantName) throws PulsarAdminException {
        return pulsarAdmin.namespaces().getNamespaces(tenantName);
    }

    private static void createNamespace(final PulsarAdmin pulsarAdmin,
                                        final String tenantName,
                                        final String newNamespace) throws PulsarAdminException {
        pulsarAdmin.namespaces().createNamespace(tenantName + "/" + newNamespace);
    }


    private static void deleteNamespace(final PulsarAdmin pulsarAdmin,
                                        final String tenantName,
                                        final String newNamespace) throws PulsarAdminException {
        pulsarAdmin.namespaces().deleteNamespace(tenantName + "/" + newNamespace);
    }

    public static void main(String[] args) throws PulsarClientException, PulsarAdminException {
        PulsarAdmin pulsarAdmin = null;
        try {
            pulsarAdmin = getPulsarAdmin();
            System.out.println("Connected to Pulsar Running at " + PULSAR_ADMIN_URL
                    + ". PulsarAdmin Object " + pulsarAdmin);

            deleteNamespace(pulsarAdmin, RESEARCH_AND_DEVELOPMENT, "research");
            createNamespace(pulsarAdmin, RESEARCH_AND_DEVELOPMENT, "research");

            final List<String> allNameSpaces = listNamespaces(pulsarAdmin, RESEARCH_AND_DEVELOPMENT);
            for(String namespace: allNameSpaces) {
                System.out.println(namespace);
            }

        } finally {
            if (Objects.nonNull(pulsarAdmin)) {
                pulsarAdmin.close();
            }
        }
    }
}
