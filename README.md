# Lingua Greeter

A [Quarkus](https://quarkus.io) based Java/Kafka Streaming example. The application is another variant of Hello World that greets user in various configured languages.

The application uses [Google Cloud Translation API](https://cloud.google.com/translate/docs/quickstarts).

## Prerequisites

- [Docker for Mac/Windows/Linux](https://www.docker.com/products/docker-desktop)
- [httpie](https://httpie.org/)
- [kustomize](https://kustomize.io/)
- [Drone CLI](https://docs.drone.io/cli/install/)
- [k3d](https://k3d.io)
- [yq](https://mikefarah.gitbook.io/yq/)
- [direnv](https://direnv.net)
  
## Drone Configuration

Copy `DRONE_TOKEN` from Drone Account settings page, then create/update the file called `.envrc.local` and add the variables to override,

```shell
export DRONE_TOKEN="drone token from the drone account settings page"
```

Reload the environment

```shell
direnv allow .
```

Ensure the token works,

```shell
drone info
```

Now activate the `lingua-greeter` in Drone,

```shell
drone repo enable "${LINGUA_GREETER_GIT_REPO}"
```

### Add Secrets to Drone Repository

The application build uses few secrets namely,

- `maven_mirror_url` : The maven mirror to used by Apache Maven builder to download the artifacts.
- `destination_image`: The container image name. Default to `${REGISTRY_NAME}/example/lingua-greeter`.
- `image_registry`: The Image registry to use. Derived from environment variable `${REGISTRY_NAME}`.
- `image_registry_user`: The Image registry username to authenticate. Defaults `admin`.
- `image_registry_password`: The Image registry user password to be used while authentication authenticate. Defaults to `admin123`.

Run the following script to add the secrets to the Drone repo `${LINGUA_GREETER_GIT_REPO}`,

```shell
./scripts/add-secrets.sh
```

### Updating Secrets

```shell
./scripts/update-secrets.sh
```

### Remove Secrets

```shell
./scripts/rm-secrets.sh
```

## Testing with Kubernetes

Refer to the [Lingua Greeter GitOps][https://github.com/kameshsampath/lingua-greeter-gitops] for cluster setup to test the application with local kubernetes clusters like [k3s](https://k3s.io) using GitOps.

### Update .drone.yml to resolve hostnames

Get Gitea `gitea-http` service `Cluster IP`,

```shell
export GITEA_HTTP_CLUSTER_IP=$(kubectl get -n default svc gitea-http -ojsonpath='{.spec.clusterIP}')
```

Update the `.drone.yml`,

```shell
yq -i '(.. | select(tag == "!!str")) |= envsubst' .drone.yml 
```

```shell
git commit  -m "Add host aliases" .drone.yml
git push origin main
```

### Trigger CI

Any pushes to repository will trigger a drone build. Here is a example to trigger a build without commit:

```shell
git commit --allow-empty -m "Test Trigger" -m "Test Trigger"
git push origin main
```

## Testing Locally

### Environment Setup

```shell
docker-compose up
```

### Run the application

```shell
mvn clean package
java -jar target/quarkus-app/quarkus-run.jar
```

Access the application using <http://localhost:8080/>
