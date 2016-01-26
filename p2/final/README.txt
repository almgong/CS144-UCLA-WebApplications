Part B Design Your Relational Schema:

1. 

User
	Keys: username

Item:
	item_id

Bids:
	keys: bidder(user), time 

ItemCategory:

	item, category

Category:

	id

2. Functional dependencies (completely nontrivial)

ItemId -> name, category, currently, buy_price, first bid, number_of_bids, bids, location, country, started, ends, seller, description

bidder(user), time(of bid) -> amount, item

username -> location, country, rating

3. BCNF: if all FDs X->Y are either trivial or superkeys. Check!

4. 4NF - loads of multivalued dependencies but all X: X->->Y are super keys for their relations (in schema). Check!