We need to index on the union of Item name, category, and description, where
OR semantics apply.

We will index on the concatenation of the above, separated by whitespace,
and will store only the id and name attributes for each indexed auction item. 
This is sufficient for our needs, as we only need to return the item id and name
for matching documents.

We will create just one index overall named p3_index.
