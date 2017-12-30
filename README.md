esourcej [![Build Status](https://circleci.com/gh/esourcej/esourcej.svg)](https://circleci.com/gh/esourcej/esourcej)
========

Event-sourcing framework for Java.

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