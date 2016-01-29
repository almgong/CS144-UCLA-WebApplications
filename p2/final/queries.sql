/* 1 */
SELECT count(*) FROM (SELECT seller_id, bidder_id FROM Seller LEFT OUTER JOIN Bidder ON bidder_id = seller_id  UNION  SELECT seller_id, bidder_id FROM Seller RIGHT OUTER JOIN Bidder ON bidder_id = seller_id) as Users;

/* 2 */
select count(*) from Item where binary location='New York';

/* 3 */
select count(*) from (select count(category) as numCategories from ItemCategory GROUP BY item) as counts where counts.numCategories = 4;

/* 4 */
select item_id from (select item_id,currently from Item, Bids where item_id = item AND end_time > '2001-12-20 00:00:01') as currentItems where currently=(SELECT max(currently) from (select currently from Item, Bids where item_id = item AND end_time > '2001-12-20 00:00:01') as currentPrices);

/* 5 */
select count(*) from Seller where rating>1000;

/* 6 */
select count(*) from Seller,Bidder where seller_id=bidder_id;

/* 7 */
select count(*) from (select category from ItemCategory, (select item from Bids where amount > 100 group by item) as HighBids where ItemCategory.item = HighBids.Item group by Category) as categories;

