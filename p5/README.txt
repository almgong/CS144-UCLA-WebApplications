1. (4)-(5) and (5)-(6). We used ssl encription for the last stage as well since
we also show the credit card number in the confirmation page.

2. To ensure that a user purchases only at the buy price, we stored in a session
object the buy price of the item whenever a user accesses the item page. Thus,
the value of buy price is never modified after initial receipt of the response
from the oak server, ensuring that the buy price will be correct after checkout.