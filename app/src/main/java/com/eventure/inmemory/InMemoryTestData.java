package com.eventure.inmemory;

import com.eventure.model.Event;
import com.eventure.model.User;

public class InMemoryTestData {

    public static void generateTo(InMemoryDatabase database){

        database.users.clear();
        database.events.clear();

        User sabrina = new User(1,"sabrina","password");
        User jake = new User(0,"jake","password");
        User john = new User(2,"john","123");
        User mike = new User(3,"mike","123");

        Event event = new Event(0,"KPI",
                "Nothing there",2020,6,1,12,30,
                "KPI","project");

        database.users.put(sabrina.getUserId(),sabrina);
        database.users.put(jake.getUserId(),jake);
        database.users.put(john.getUserId(),john);
        database.users.put(mike.getUserId(),mike);
        database.events.put(event.getEventId(),event);
    }





}
