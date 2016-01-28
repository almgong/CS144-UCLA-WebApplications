/* Seller table */
create table Seller (
	seller_id varchar(255),
	rating int,
	primary key(seller_id)
);

create table Bidder (
	bidder_id varchar(255),
	location varchar(1000),
	country varchar(1000),
	rating int,
	primary key(bidder_id)
);

create table Item (
	item_id int,
	name varchar(1000),
	buy_price DECIMAL(8,2),
	currently DECIMAL(8,2), 
	first_bid DECIMAL(8,2),
	started_time timestamp,
	end_time timestamp,
	seller varchar(255),
	description varchar(4000),
	location varchar(1000),
	country varchar(1000),
	latitude varchar(1000),
	longitude varchar(1000),
	foreign key (seller) references Seller(seller_id) on delete cascade,
	primary key(item_id)
);

create table Bids(
	bidder varchar(255),
	item int,
	amount DECIMAL(8,2),
	time timestamp,
	foreign key (bidder) references Bidder(bidder_id) on delete cascade,
	foreign key (item) references Item(item_id) on delete cascade,
	primary key(bidder, time)
);

create table ItemCategory (
	item int,
	category varchar(255),
	foreign key (item) references Item(item_id) on delete cascade,
	primary key(item, category)
);
