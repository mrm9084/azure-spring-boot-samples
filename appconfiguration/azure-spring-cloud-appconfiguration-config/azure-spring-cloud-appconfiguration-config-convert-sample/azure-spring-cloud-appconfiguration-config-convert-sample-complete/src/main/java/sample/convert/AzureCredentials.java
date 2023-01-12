/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See LICENSE in the project root for
 * license information.
 */
package sample.convert;

import com.azure.core.credential.TokenCredential;
import com.azure.identity.EnvironmentCredentialBuilder;
import com.azure.spring.cloud.config.AppConfigurationCredentialProvider;
import com.azure.spring.cloud.config.KeyVaultCredentialProvider;

public class AzureCredentials implements AppConfigurationCredentialProvider, KeyVaultCredentialProvider {

    @Override
    public TokenCredential getKeyVaultCredential(String uri) {
        return getCredential();
    }

    @Override
    public TokenCredential getAppConfigCredential(String uri) {
        return getCredential();
    }

    private TokenCredential getCredential() {
        System.out.println("Using AzureCredentials");
        return new EnvironmentCredentialBuilder().build();
    }

}
