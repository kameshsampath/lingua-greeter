#! /usr/bin/env bash

set -euo pipefail

GOOGLE_APPLICATION_CREDENTIALS="$(cat "${GOOGLE_APPLICATION_CREDENTIALS}")"

GOOGLE_APPLICATION_CREDENTIALS="$(echo -n "${GOOGLE_APPLICATION_CREDENTIALS}" | base64 -w0 -)"

#echo "$GOOGLE_APPLICATION_CREDENTIALS"

# drone secret add --name google_application_credentials --data "'$GOOGLE_APPLICATION_CREDENTIALS'" "${LINGUA_GREETER_GIT_REPO}"