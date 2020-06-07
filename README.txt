Jonathan Ratsamy
Project Assignment 2
CS636 / CS436

The submitted version of the music store system is capable of browsing the 
catalog, listening to some music samples, register a user when adding to a 
cart, view the user's cart, and update, add, remove and checkout the user's 
cart.

Server port is set to 9003.

Files Edited:
cs636.music.presentation.web: CatalogController.java

CatalogController.java: 
Based off murachMusicStore's CatalogController & OrderController
Added methods for display the catalog and cart and methods to add, remove 
and update the cart along with some constants to use. 

Files Added:
WEB-INF/jsp/*.jsp: add.jsp, cart.jsp, catalog.jsp

catalog.jsp:
Layout is based off index.html from ch07downloadS just added a form with 
options to select and an add button that calls to CatalogController. 

add.jsp:
This displays a message to say you added the product to the cart along 
with the product code. This was primarily used for testing.

cart.jsp:
Heavily based on cart.jsp from murachMusicStore with edits to for layout
and to use cartItemData objects.