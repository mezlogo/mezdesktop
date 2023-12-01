#!/usr/bin/env bash

coeff398='{"cfId": "115940998683", "numerator": 149, "denumerator": 50, "prices": {"HongKong": "2.98", "Decimal": "3.98", "American": "+298", "Indonesian": "2.98", "Fractions": "149/50", "Malaysian": "-0.336"}}'
coeff402='{"cfId": "115940998683", "numerator": 151, "denumerator": 50, "prices": {"HongKong": "2.98", "Decimal": "4.02", "American": "+298", "Indonesian": "2.98", "Fractions": "149/50", "Malaysian": "-0.336"}}'
coeff400='{"cfId": "115940998683", "numerator": 3, "denumerator": 1, "prices": {"HongKong": "3.00", "Decimal": "4.00", "American": "+300", "Indonesian": "3.00", "Fractions": "3/1", "Malaysian": "-0.334"}}'

active='ACTIVE'
suspend='SUSPEND'
hidden='HIDDEN'
off='OFF'


./cli/build/install/jknife/bin/jknife amqppublish --exchangeType DIRECT --exchange price.changes --routingkey "$1" --body "$hidden"
