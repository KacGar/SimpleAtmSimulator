
# Simple ATM simulator

Small application which simulates what we usually see using ATM's. Application was made purely for learning purposes, mostly for learning how to use SWING components and it's not a proper ATM software and shouldn't be used as such.

What application does is :
  - initiliaze operation (by faking that user entered card)
  - "reads" credit card with a loading screen (which fakes work done in background)
  - lets user enter password which is "1234" (its hardcoded for convenience)
  - lets user choose either to deposit or withdraw
  - asks if recipe of operation should be shown
  - farewell panel after which app comes back to starting point

Balance value is a static field, which from starting balance value "600", we can perform multiple operations of depositing or withdrawal (as long as balance allow it). After restaring application we're going back to starting point of value "600".

Since i'm just started learning JAVA and programming in general, this was project was just a meaning of learning how SWING components,layouts etc. work so i'm sorry for my unproffesional workarounds for faking certain actions (like ie. "reading from card").


## Libraries

One external library used in project is custom Look and Feel API which is free to use, created by FormDev (https://www.formdev.com/flatlaf/) which is not required. Upon starting, app checks if library is there, if not default LaF will be used.