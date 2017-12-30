esourcej [![Build Status](https://circleci.com/gh/esourcej/esourcej.svg)](https://circleci.com/gh/esourcej/esourcej)
========

Event-sourcing framework for Java.

### Main features

- No third party code in domain/app package - no interfaces implementation / extending abstract classes needed.
  Even annotations class could be totally custom and user-defined. Of course ready to use classes / annotations are 
  provided, ready to use.
- Fully functional command/event handlers - `static` methods are preferred, no side effects in main flow.
- Easy to extend - everything is encapsulated using well-defined interfaces which default implementations are provided. 
  It's easy to write own implementation in order to use custom infrastructure / tooling / AOP.
- Testing friendly - deticated package includes testing tools like matchers, builders, in-memory implementations 
  which make functional tests fast, portable, easy to write and maintain
- Easy configuration - builder which produces ready to use `CommandGateway` connected to `EventStore`, `MessageBroker` etc

### Overview

```java

// Account.java
class Account {
    
    class Command {}
    
    class CreateCommand extends Command { public String name; public BigInteger balance; }
    class PayBillCommand extends Command { public String name; public BigInteger balance; }
    
    class Event {}
    
    class CreatedEvent extends Event { public String name; public BigInteger balance; } 
    class BillPaidEvent extends Event { public String billId; public BigInteger amount; } 
    class BillPayFailedEvent extends Event { 
        public enum REASON { NO_ENOUGH_MONEY };
        
        public REASON reason; public String billId; public BigInteger amount;
    }
    
    class State { public String name; public BigInteger balance; }
    
    @CommandHandler
    static List<Event> handle(CreateCommand command)
    {
        Event event = new CreatedEvent();
        
        event.name = command.name;
        event.balance = command.balance;
        
        return Arrays.asList(event);
    }
    
    @EventHandler
    static State handle(CreatedEvend event)
    {
        State state = new State();
        
        state.name = event.name;
        state.balance = event.balance;
        
        return state;
    }
}
```

### Plays with others

Framework works very well with:

- [Spring - HTTP / MVC Framework](https://spring.io)
- [EventStore - store for events with fencing](https://eventstore.org)
- [RabbitMQ - messaging and queuing](https://www.rabbitmq.com)
- [Immutables - great immutebles for java with simple API](https://immutables.github.io)
- Much much more - interfaces are easy to implement (`EventStore`, `StateProvider` etc)

### Inspiration

- [A Functional Foundation for CQRS/ES](http://verraes.net/2014/05/functional-foundation-for-cqrs-event-sourcing/)
- [AxonFramework - great Event sourcing framework](http://www.axonframework.org)
- [EventStore](https://eventstore.org)

Thanks for your contributions!
