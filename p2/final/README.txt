Part B Design Your Relational Schema:

1. Keys (Candidate)

Seller:
	seller_id

Bidder
	bidder_id

Item:
	item_id

Bids:
	bidder_id, time 

ItemCategory:

	item, category


2. Functional dependencies (completely nontrivial)

ItemId -> name, currently, buy_price, first bid, number_of_bids, bids, location,
lat, long, country, started, ends, seller, description

UserID/SellerID -> seller_rating, location, lat, long, country

UserID/BidderID -> bidder_rating, location, country

BidderID, Time(of bid) -> amount, item

All express Superkeys.

3. BCNF: if all FDs X->Y in schema are either trivial or superkeys. Check!

4. 4NF - if all X->->Y in schema are either trivial or X is a superkey. Check!
	No violations of 4NF in any of our tables.

