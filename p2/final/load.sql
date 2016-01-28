load data local infile 'sql/SellerSorted.dat' into table Seller fields terminated by '\t';
load data local infile 'sql/BidderSorted.dat' into table Bidder fields terminated by '\t';
load data local infile 'sql/ItemSorted.dat' into table Item fields terminated by '\t';
load data local infile 'sql/BidsSorted.dat' into table Bids fields terminated by '\t';
load data local infile 'sql/CategorySorted.dat' into table ItemCategory fields terminated by '\t';