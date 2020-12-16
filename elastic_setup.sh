#!/usr/bin/env bash

# create mapping
curl -X PUT "localhost:9200/films?pretty" -H 'Content-Type: application/json' -d '{"mappings":{"properties":{"id":{"type":"integer"},"link":{"type":"keyword"},"title":{"type":"keyword"},"score":{"type":"double"}, "genres":{"type":"text"},"poster":{"type":"keyword"},"summary":{"type":"text"},"actors":{"type":"keyword"},"year":{"type":"integer"}}}}'

# index films
curl -XPOST -H "Content-Type: application/json" "localhost:9200/films/_bulk?pretty" --data-binary "@films_scraped.json"
