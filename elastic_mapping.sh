curl -X PUT "localhost:9200/films?pretty" -H 'Content-Type: application/json' -d '{"mappings":{"properties":{"id":{"type":"keyword"},"link":{"type":"text"},"title":{"type":"text"},"score":{"type":"double"},
"genres":{"type":"text"},"poster":{"type":"text"},"sinopsis":{"type":"text"},"actors":{"type":"text"},"year":{"type":"integer"}}}}'
