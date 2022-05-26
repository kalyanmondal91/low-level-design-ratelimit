# Rate Limiter - Low level system design
Repository for low level system design of a rate limiter

## Problem Statement
We have to do low level design for a Rate Limiter

Whenever you expose a web service / api endpoint, you need to implement a rate limiter to prevent abuse of the service (DOS attacks).

Implement a Class with a request method. Every request comes in with a unique clientID, deny a request if that client has made more than 5 requests in a time limit of 100s.

Example:
* inputs.add(new Input("A", 1000)); // Hit
* inputs.add(new Input("A", 1002)); // Hit
* inputs.add(new Input("A", 1020)); // Hit
* inputs.add(new Input("A", 1030)); // Hit
* inputs.add(new Input("A", 1050)); // Hit
* inputs.add(new Input("A", 1051)); // Miss
* inputs.add(new Input("A", 1099)); // Miss
* inputs.add(new Input("A", 1100)); // Hit
* inputs.add(new Input("A", 1101)); // Miss

### Strategy
* Token Bucket (quite common and very easy to implement)
* Leaking Bucket
* Fixed window
* Sliding Window
* Sliding window logged

### Expectations
* Code should be functionally correct.
* Code should be modular and readable. Clean and professional level code.
* Code should be extensible and scalable. Means it should be able to accommodate new requirements with minimal changes.
* Code should have good OOPs design.