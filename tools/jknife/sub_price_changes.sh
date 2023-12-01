#!/usr/bin/env bash

./cli/build/install/jknife/bin/jknife amqpsubscribe --exchangeType DIRECT --exchange price.changes --routingkey "$1"