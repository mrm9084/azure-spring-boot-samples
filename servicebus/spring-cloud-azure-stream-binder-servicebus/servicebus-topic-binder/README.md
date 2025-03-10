# Spring Cloud Azure Stream Binder for Service Bus topic Sample shared library for Java 


This code sample demonstrates how to use the Spring Cloud Stream binder for Azure Service Bus topic. The sample app has two operating modes.
One way is to expose a Restful API to receive string message, another way is to automatically provide string messages.
These messages are published to a service bus topic. The sample will also consume messages from the same service bus topic.


## What You Will Build
You will build an application using Spring Cloud Stream to send and receive messages for Azure Service Bus Topic.

## What You Need

- [An Azure subscription](https://azure.microsoft.com/free/)
- [Terraform](https://www.terraform.io/)
- [Azure CLI](https://docs.microsoft.com/cli/azure/install-azure-cli)
- [JDK8](https://www.oracle.com/java/technologies/downloads/) or later
- Maven
- You can also import the code straight into your IDE:
    - [IntelliJ IDEA](https://www.jetbrains.com/idea/download)

## Provision Azure Resources Required to Run This Sample
This sample will create Azure resources using Terraform. If you choose to run it without using Terraform to provision resources, please pay attention to:
> [!IMPORTANT]  
> If you choose to use a security principal to authenticate and authorize with Azure Active Directory for accessing an Azure resource
> please refer to [Authorize access with Azure AD](https://microsoft.github.io/spring-cloud-azure/current/reference/html/index.html#authorize-access-with-azure-active-directory) to make sure the security principal has been granted the sufficient permission to access the Azure resource.

### Authenticate Using the Azure CLI
Terraform must authenticate to Azure to create infrastructure.

In your terminal, use the Azure CLI tool to setup your account permissions locally.

```shell
az login
```

Your browser window will open and you will be prompted to enter your Azure login credentials. After successful authentication, your terminal will display your subscription information. You do not need to save this output as it is saved in your system for Terraform to use.

```shell
You have logged in. Now let us find all the subscriptions to which you have access...

[
  {
    "cloudName": "AzureCloud",
    "homeTenantId": "home-Tenant-Id",
    "id": "subscription-id",
    "isDefault": true,
    "managedByTenants": [],
    "name": "Subscription-Name",
    "state": "Enabled",
    "tenantId": "0envbwi39-TenantId",
    "user": {
      "name": "your-username@domain.com",
      "type": "user"
    }
  }
]
```

If you have more than one subscription, specify the subscription-id you want to use with command below: 
```shell
az account set --subscription <your-subscription-id>
```

### Provision the Resources

After login Azure CLI with your account, now you can use the terraform script to create Azure Resources.

#### Run with Bash

```shell
# In the root directory of the sample
# Initialize your Terraform configuration
terraform -chdir=./terraform init

# Apply your Terraform Configuration
terraform -chdir=./terraform apply -auto-approve

```

#### Run with Powershell

```shell
# In the root directory of the sample
# Initialize your Terraform configuration
terraform -chdir=terraform init

# Apply your Terraform Configuration
terraform -chdir=terraform apply -auto-approve

```

It may take a few minutes to run the script. After successful running, you will see prompt information like below:

```shell
azurecaf_name.resource_group: Creating...
azurecaf_name.azurecaf_name_servicebus: Creating...
azurecaf_name.resource_group: Creation complete ...
azurecaf_name.azurecaf_name_servicebus: Creation complete ...
azurerm_resource_group.main: Creating...
azurerm_resource_group.main: Creation complete ...
azurerm_servicebus_namespace.servicebus_namespace: Creating...
...
azurerm_servicebus_namespace.servicebus_namespace: Creation complete ...
azurerm_role_assignment.role_servicebus_data_owner: Creating...
azurerm_servicebus_topic.servicebus_topic: Creating...
azurerm_servicebus_topic.servicebus_topic: Creation complete ...
azurerm_servicebus_subscription.servicebus_subscription: Creating...
...
azurerm_servicebus_subscription.servicebus_subscription: Creation complete...
...
azurerm_role_assignment.role_servicebus_data_owner: Creation complete ...

Apply complete! Resources: 7 added, 0 changed, 0 destroyed.

Outputs:

...
```

You can go to [Azure portal](https://ms.portal.azure.com/) in your web browser to check the resources you created.

### Export Output to Your Local Environment
Running the command below to export environment values:

#### Run with Bash

```shell
source ./terraform/setup_env.sh
```

#### Run with Powershell

```shell
terraform\setup_env.ps1
```

If you want to run the sample in debug mode, you can save the output value.

```shell
AZURE_SERVICEBUS_NAMESPACE=...
AZURE_SERVICEBUS_TOPIC_NAME=...
AZURE_SERVICEBUS_TOPIC_SUBSCRIPTION_NAME=...
AZURE_SERVICEBUS_RESOURCE_GROUP=...
AZURE_SERVICEBUS_SUBSCRIPTION_ID=...
```

## Run Locally

### Run the sample with Maven

In your terminal, run `mvn clean spring-boot:run`.

```shell
mvn clean spring-boot:run
```

### Run the sample in IDEs

You can debug your sample by adding the saved output values to the tool's environment variables or the sample's `application.yaml` file.

* If your tool is `IDEA`, please refer to [Debug your first Java application](https://www.jetbrains.com/help/idea/debugging-your-first-java-application.html) and [add environment variables](https://www.jetbrains.com/help/objc/add-environment-variables-and-program-arguments.html#add-environment-variables).

* If your tool is `ECLIPSE`, please refer to [Debugging the Eclipse IDE for Java Developers](https://www.eclipse.org/community/eclipse_newsletter/2017/june/article1.php) and [Eclipse Environment Variable Setup](https://examples.javacodegeeks.com/desktop-java/ide/eclipse/eclipse-environment-variable-setup-example/).

## Verify This Sample


1.  Verify in your app’s logs that similar messages were posted:

```shell
...
New message received: 'Hello world, 2'
...
Message 'Hello world, 2' successfully checkpointed
...
New message received: 'Hello world, 3'
...
Message 'Hello world, 3' successfully checkpointed
...
```

## Clean Up Resources
After running the sample, if you don't want to run the sample, remember to destroy the Azure resources you created to avoid unnecessary billing.

The terraform destroy command terminates resources managed by your Terraform project.   
To destroy the resources you created.

#### Run with Bash

```shell
terraform -chdir=./terraform destroy -auto-approve
```

#### Run with Powershell

```shell
terraform -chdir=terraform destroy -auto-approve
```

## (Optional) Use Azure Resource Manager to Retrieve Connection String

If you don't want to configure the connection string in your application, it's also possible to use Azure Resource Manager to retrieve the connection string. Just make sure the principal has sufficient permission to read resource metadata.

1. Uncomment the Azure Resource Manager dependency in the *pom.xml* file.

```xml
<dependency>
  <groupId>com.azure.spring</groupId>
  <artifactId>spring-cloud-azure-resourcemanager</artifactId>
</dependency>
```

2. Run locally with the command `mvn clean spring-boot:run -Dspring-boot.run.profiles=rm` to activate the [application-rm.yaml][application-rm.yaml] profile file.

3. [Verify This Sample](#verify-this-sample).


## Enhancement

### Sync Support

To enable message sending in a synchronized way with Spring Cloud Stream 3.x, spring-cloud-azure-stream-binder-servicebus supports the sync producer mode to get responses for sent messages.
Make sure set `spring.cloud.stream.servicebus.bindings.<binding-name>.producer.sync = true` before use it.

### Configuration Options

[Service Bus Producer Properties](https://aka.ms/spring/docs/4.0.0#producer-properties-2)

[Service Bus Consumer Properties](https://aka.ms/spring/docs/4.0.0#consumer-properties)

### Resource Provision

Service Bus binder supports provisioning of queue, topic and subscription, users could use [properties](https://aka.ms/spring/docs/4.0.0#resource-provision-2) to enable provisioning.

### Partition Key Support

Service Bus binder supports partitioning by allowing setting partition key and session id in the message header. [Here](https://aka.ms/spring/docs/4.0.0#partition-key-support) shows how to set partition key for messages.

### Session Support

Service Bus binder supports message sessions. [Here](https://aka.ms/spring/docs/4.0.0#session-support) shows how to set session id of a message.

### Error Channel

Service Bus binder supports consumer error channel, producer error channel and global default error channel, click [here](https://aka.ms/spring/docs/4.0.0#error-channels-2)  to see more information.

### Set Service Bus message headers

Users can get all the supported ServiceBus message headers [here](https://aka.ms/spring/docs/4.0.0#scs-sb-headers) to configure.

[application-rm.yaml]: ./src/main/resources/application-rm.yaml
