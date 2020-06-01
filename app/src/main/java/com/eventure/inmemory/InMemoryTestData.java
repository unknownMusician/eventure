package com.eventure.inmemory;

import com.eventure.model.MyEvent;
import com.eventure.model.User;

public class InMemoryTestData {

    public static void generateTo(InMemoryDatabase database){

        database.users.clear();
        database.events.clear();

        User sabrina = new User(1,"sabrina","password");
        User jake = new User(0,"jake","password");
        User john = new User(2,"john","123");
        User mike = new User(3,"mike","123");

        MyEvent event = new MyEvent(0,"KPI",
                "Nothing there",120,0,12,12,30,
                "KPI",1);
        MyEvent event1 = new MyEvent(1,"KPI",
                "Nothing there",120,0,12,12,30,
                "KPI",2);
        MyEvent event3 = new MyEvent(2,"KPI",
                "Nothing there",120,0,12,12,30,
                "KPI",4);
        MyEvent event2 = new MyEvent(3,"KPI",
                "Nothing there",120,0,12,12,30,
                "KPI",3);
        MyEvent borispol = new MyEvent(4,"Borispol HYPE",
                "TU-TU-TUTUTUTUTUTU",120,5,2,12,30,
                "KPI",3);

        database.users.put(sabrina.getUserId(),sabrina);
        database.users.put(jake.getUserId(),jake);
        database.users.put(john.getUserId(),john);
        database.users.put(mike.getUserId(),mike);
        database.events.put(event.getEventId(),event);
        database.events.put(event1.getEventId(),event1);
        database.events.put(event2.getEventId(),event2);
        database.events.put(event3.getEventId(),event3);
        database.events.put(borispol.getEventId(),borispol);
    }





}
