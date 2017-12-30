esourcej [![Build Status](https://circleci.com/gh/esourcej/esourcej.svg)](https://circleci.com/gh/esourcej/esourcej)
========

Event-sourcing framework for Java.

### Overview

```java

// Account.java
class Account {
    
    class CreateCommand { public String name; public BigInteger balance; }
    class PayBill { public String name; public BigInteger balance; }
    
    class CreatedEvent { public String name; public BigInteger balance; } 
    class BillPaidEvent { public String billId; public BigInteger amount; } 
    class BillPayFailedEvent { 
        public enum REASON { NO_ENOUGH_MONEY };
        
        public REASON reason; public String billId; public BigInteger amount;
    }
    
    class State { public String name; public BigInteger balance; }
    
    @CommandHandler
    static List<Object> handle(CreateCommand command)
    {
        CreatedEvent event = new CreatedEvent();
        
        event.name = command.name;
        event.balance = command.balance;
        
        return Arrays.asList(event);
    }
    
    @EventHandler
    static State handle(CreatedEvend event)
    {
        new State;
        
        state.name = event.name;
        state.balance = event.balance;
        
        return state;
    }
}


```