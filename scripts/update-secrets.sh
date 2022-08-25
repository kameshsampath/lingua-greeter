#! /usr/bin/env bash

set -euxo pipefail

drone secret update --name maven_mirror_url --data 'http://nexus.infra:8081/repository/maven-public/' "${LINGUA_GREETER_GIT_REPO}"

drone secret update --name destination_image --data "${REGISTRY_NAME}/example/lingua-greeter" "${LINGUA_GREETER_GIT_REPO}"

drone secret update --name image_registry --data "${REGISTRY_NAME}" "${LINGUA_GREETER_GIT_REPO}"

drone secret update --name image_registry_user --data "${IMAGE_REGISTRY_USER}" "${LINGUA_GREETER_GIT_REPO}"

drone secret update --name image_registry_password --data "${IMAGE_REGISTRY_PASSWORD}" "${LINGUA_GREETER_GIT_REPO}"