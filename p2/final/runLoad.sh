#!/bin/bash
#automate P2 Database transformation and set up

#make sure all tables are refreshed
mysql -u cs144 CS144 < drop.sql
mysql -u cs144 CS144 < create.sql

#compile and run Java
ant -f build.xml run-all

#remove duplicates
sort -u sql/Seller.dat > sql/SellerSorted.dat
sort -u sql/Bidder.dat > sql/BidderSorted.dat
sort -u sql/Item.dat > sql/ItemSorted.dat
sort -u sql/Bids.dat > sql/BidsSorted.dat
sort -u sql/Category.dat > sql/CategorySorted.dat

#now load all .dat files into their respective DB locations
mysql -u cs144 CS144 < load.sql

#remove temporary files, all sql dat files 
rm -rf sql
rm -rf bin
