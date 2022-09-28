#! /usr/bin/env bash

set -euo pipefail

KUBE_CONFIG_CONTENT="$(k3d kubeconfig get "$K3D_CLUSTER_NAME")"

KUBE_CONFIG_CONTENT=$(echo "${KUBE_CONFIG_CONTENT}" | sed -E 's|(https://)(host.docker.internal:)([0-9]*)|\1kubernetes.default.svc.cluster.local:443|')

KUBE_CONFIG_CONTENT="$(echo -n "${KUBE_CONFIG_CONTENT}" | base64 -w0 -)"

#echo "$KUBE_CONFIG_CONTENT"

drone secret add --name kubeconfig_content --data "'$KUBE_CONFIG_CONTENT'" "${LINGUA_GREETER_GIT_REPO}"