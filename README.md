## Synopsis

The BlackJack RMI project shows an example of a fully distributed black jack (aka twenty-one) game. You can have how many clients as you want but just one server. The clients represent the players while the server can be seen as the dealer. The rules are the same as the twenty-one so the player must beat the dealer in one of these ways: 
- Get 21 points on the player's first two cards (called a "blackjack" or "natural"), without a dealer blackjack;
- Reach a final score higher than the dealer without exceeding 21; or
- Let the dealer draw additional cards until his or her hand exceeds 21. 

## Motivation

This project represents an example on how to use RMI technologies to build a fully distributed system. 

## Installation

We assume that both client and server have a copy of *class* files.
- Start the rmiregistry (on Windows: start rmiregistry; on Linux: rmiregistry &)
- Compile the server running the *rmic* utility
- Start the server
- Start the client 

## License

This work is licensed under Apache 2.0 license. Copyright {2016} {Aurelio Forese}
