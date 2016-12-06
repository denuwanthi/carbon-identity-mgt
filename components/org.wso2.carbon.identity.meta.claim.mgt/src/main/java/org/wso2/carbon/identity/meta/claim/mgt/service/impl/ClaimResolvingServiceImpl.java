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
package org.wso2.carbon.identity.meta.claim.mgt.service.impl;

import org.wso2.carbon.identity.meta.claim.mgt.exception.ClaimMappingBuilderException;
import org.wso2.carbon.identity.meta.claim.mgt.exception.ClaimResolvingServiceException;
import org.wso2.carbon.identity.meta.claim.mgt.internal.claim.mapping.ClaimMappingBuilder;
import org.wso2.carbon.identity.meta.claim.mgt.service.ClaimResolvingService;

import java.util.Map;

/**
 *
 */
public class ClaimResolvingServiceImpl implements ClaimResolvingService {
    /**
     * Provides claim mappings for applications.
     *
     * @param applicationName : Uniquely identifying name for application.
     * @return Map(application claims : root claim)
     * @throws ClaimResolvingServiceException : Error in getting the claim mapping for application.
     */
    @Override
    public Map<String, String> getApplicationClaimMapping(String applicationName)
            throws ClaimResolvingServiceException {
        try {
            return ClaimMappingBuilder.getInstance().getApplicationClaimMapping(applicationName);
        } catch (ClaimMappingBuilderException e) {
            throw new ClaimResolvingServiceException(
                    "Error while getting the application claim mapping for " + applicationName, e);
        }
    }

    /**
     * Provides claim mappings for IDPs.
     *
     * @param idpName : Uniquely identifying name for IDPs.
     * @return Map(Idp claim : root claim)
     * @throws ClaimResolvingServiceException : Error in getting the claim mapping for IDP.
     */
    @Override
    public Map<String, String> getIdpClaimMapping(String idpName) throws ClaimResolvingServiceException {
        try {
            return ClaimMappingBuilder.getInstance().getIdpClaimMapping(idpName);
        } catch (ClaimMappingBuilderException e) {
            throw new ClaimResolvingServiceException("Error while getting the IDP claim mapping for " + idpName, e);
        }
    }

    /**
     * Provides claim mappings for standards.
     *
     * @param standardName : Uniquely identifying name for standards.
     * @return Map(Standard claim : root claim)
     * @throws ClaimResolvingServiceException : Error in getting the claim mapping for standard.
     */
    @Override
    public Map<String, String> getStandardClaimMapping(String standardName) throws ClaimResolvingServiceException {
        try {
            return ClaimMappingBuilder.getInstance().getStandardClaimMapping(standardName);
        } catch (ClaimMappingBuilderException e) {
            throw new ClaimResolvingServiceException(
                    "Error while getting the standard claim mapping for " + standardName, e);
        }
    }
}
