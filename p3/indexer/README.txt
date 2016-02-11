We need to index on the union of Item name, category, and description, all of
which are non-atomic (OR semantics apply).

We will index on the concatenation of the above, separated by whitespace,
and will store the id and name attributes for each indexed auction item. We will
create just one index overall.
