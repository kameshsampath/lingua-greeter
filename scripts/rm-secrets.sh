#! /usr/bin/env bash

set -euxo pipefail

drone secret rm --name maven_mirror_url "${LINGUA_GREETER_GIT_REPO}"

drone secret rm --name destination_image "${LINGUA_GREETER_GIT_REPO}"

drone secret rm --name image_registry "${LINGUA_GREETER_GIT_REPO}"

drone secret rm --name image_registry_user "${LINGUA_GREETER_GIT_REPO}"

drone secret update --name image_registry_password "${LINGUA_GREETER_GIT_REPO}"

drone secret rm --name kubeconfig_content user-01/lingua-greeter