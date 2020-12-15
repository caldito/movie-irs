#!/usr/bin/env bash

# create mapping
curl -X PUT "localhost:9200/films?pretty" -H 'Content-Type: application/json' -d '{"mappings":{"properties":{"id":{"type":"keyword"},"link":{"type":"text"},"title":{"type":"text"},"score":{"type":"double"}, "genres":{"type":"text"},"poster":{"type":"text"},"sinopsis":{"type":"text"},"actors":{"type":"text","fielddata": true},"year":{"type":"integer"}}}}'

# index films
curl -XPOST -H "Content-Type: application/json" "localhost:9200/films/_bulk?pretty" --data-binary "@films.json"
