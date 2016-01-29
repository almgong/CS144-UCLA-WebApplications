Part B Design Your Relational Schema:

1. Keys

Seller:
	seller_id

Bidder:
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
	Initially we had the category attribute in item, but this was a MVD 
	that violated 4N, so we removed it by creating a ItemCategory relation.


Other notes:

We did not include the attribute "number of bids" into our Item table because
we felt that it was easily obtainable with a single join query. Therefore, we 
left it out in efforts of reducing redundant information.
