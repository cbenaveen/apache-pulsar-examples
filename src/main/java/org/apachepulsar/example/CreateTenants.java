package org.apachepulsar.example;

import org.apache.pulsar.client.admin.PulsarAdmin;
import org.apache.pulsar.client.admin.PulsarAdminException;
import org.apache.pulsar.client.api.PulsarClientException;
import org.apache.pulsar.common.policies.data.TenantInfo;

import java.util.Collections;
import java.util.List;

/**
 * Programmatic way of creating a Tenant.
 */
public class CreateTenants {
    public static final String PULSAR_ADMIN_URL = "http://localhost:8080";

    private static PulsarAdmin getPulsarAdmin() throws PulsarClientException {
        return PulsarAdmin.builder().serviceHttpUrl(PULSAR_ADMIN_URL).build();
    }

    private static void createTenant(final PulsarAdmin pulsarAdmin,
                                     final TenantInfo tenantInfo,
                                     final String tenantName) throws PulsarAdminException {
        pulsarAdmin.tenants().createTenant(tenantName, tenantInfo);
    }

    private static List<String> getTenants(PulsarAdmin pulsarAdmin) throws PulsarAdminException {
        final List<String> tenants = pulsarAdmin.tenants().getTenants();
        return tenants;
    }

    private static boolean isTenantExists(PulsarAdmin pulsarAdmin,
                                               final String tenantName) throws PulsarAdminException {
        final List<String> tenants = pulsarAdmin.tenants().getTenants();
        return tenants.contains(tenantName);
    }

    public static void main(String[] args) throws PulsarClientException, PulsarAdminException {
        final PulsarAdmin pulsarAdmin = getPulsarAdmin();

        final TenantInfo tenantInfo = TenantInfo.builder()
                .allowedClusters(Collections.singleton("standalone"))
                .build();

        List<String> tenants = getTenants(pulsarAdmin);
        for (String tenantName: tenants) {
            System.out.printf("Tenant Name %s%n", tenantName);
        }

        final String tenantName = "research-and-development";
        System.out.println("Attempting to create tenant " + tenantName);

        if(!isTenantExists(pulsarAdmin, tenantName)) {
            createTenant(pulsarAdmin, tenantInfo, tenantName);
        }

        tenants = getTenants(pulsarAdmin);
        for (String name: tenants) {
            System.out.printf("Tenant Name %s%n", name);
        }

        pulsarAdmin.close();
    }
}
