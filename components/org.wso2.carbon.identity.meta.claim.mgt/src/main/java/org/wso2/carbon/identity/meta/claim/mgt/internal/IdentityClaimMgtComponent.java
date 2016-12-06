/*
 * Copyright (c) 2016, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.wso2.carbon.identity.meta.claim.mgt.internal;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wso2.carbon.identity.meta.claim.mgt.service.ClaimResolvingService;
import org.wso2.carbon.identity.meta.claim.mgt.service.impl.ClaimResolvingServiceImpl;
import org.wso2.carbon.identity.mgt.internal.IdentityMgtDataHolder;
import org.wso2.carbon.identity.mgt.user.impl.JDBCUniqueIdResolverFactory;
import org.wso2.carbon.kernel.startupresolver.RequiredCapabilityListener;

import static org.wso2.carbon.identity.mgt.util.IdentityMgtConstants.UNIQUE_ID_RESOLVER_TYPE;

/**
 * OSGi service component which handle identity claim management.
 *
 * @since 1.0.0
 */
@Component(
        name = "org.wso2.carbon.identity.meta.claim.mgt.internal.IdentityClaimMgtComponent",
        immediate = true,
        property = {
                "componentName=wso2-carbon-identity-meta-claim-mgt"
        })
public class IdentityClaimMgtComponent implements RequiredCapabilityListener {

    private static final Logger log = LoggerFactory.getLogger(IdentityClaimMgtComponent.class);

    private BundleContext bundleContext;

    private ServiceRegistration<ClaimResolvingService> claimResolvingServiceRegistration;

    @Activate
    public void registerClaimResolvingService(BundleContext bundleContext) {

        this.bundleContext = bundleContext;

        // Register Default Unique Id Resolver
        IdentityMgtDataHolder.getInstance()
                .registerUniqueIdResolverFactory(UNIQUE_ID_RESOLVER_TYPE, new JDBCUniqueIdResolverFactory());
    }

    @Deactivate
    public void unregisterClaimResolvingService(BundleContext bundleContext) {

        try {
            if (claimResolvingServiceRegistration != null) {
                bundleContext.ungetService(claimResolvingServiceRegistration.getReference());
            }
        } catch (Exception e) {
            log.error("Error occurred in un getting service", e);
        }

        log.info("Carbon-Claim-Resolving bundle deactivated successfully.");
    }

    @Override
    public void onAllRequiredCapabilitiesAvailable() {

        IdentityClaimMgtDataHolder identityClaimMgtDataHolder = IdentityClaimMgtDataHolder.getInstance();

        // Register the claim resolving service.
        ClaimResolvingServiceImpl claimResolvingService = new ClaimResolvingServiceImpl();
        identityClaimMgtDataHolder.setClaimResolvingService(claimResolvingService);

        claimResolvingServiceRegistration = bundleContext
                .registerService(ClaimResolvingService.class, claimResolvingService, null);
        log.info("Claim resolving service registered successfully.");

        log.info("Carbon-Identity-Claim-Mgt bundle activated successfully.");

    }
}

