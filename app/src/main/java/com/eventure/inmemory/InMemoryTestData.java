package com.eventure.inmemory;

import com.eventure.model.MyEvent;
import com.eventure.model.Place;
import com.eventure.model.User;

public class InMemoryTestData {

    public static void generateTo(InMemoryDatabase database){

        database.users.clear();
        database.events.clear();

        User sabrina = new User(1,"sabrina","password");
        User jake = new User(0,"jake","password");
        User john = new User(2,"john","123");
        User mike = new User(3,"mike","123");
        User q = new User(4,"q","123");

        MyEvent event = new MyEvent(0,"Lecture on quantum physics",
                "Let`s talk about physics! \n" +
                        "Immerse yourself in the endless world of physics with Professor Albert Einstein and Associate Professor Natalia Yakunina",120,5,5,12,30,
                new Place(50.5d, 30.5d),1);
        MyEvent event1 = new MyEvent(1,"Football",
                "Guys, who want to play football today? Come up.",120,5,6,12,30,
                new Place(50.7d, 30.2d),4);
        MyEvent event3 = new MyEvent(2,"RadioDay!",
                "Let`s celebrate a RadioDay!A concert by Twenty one pilots is waiting for you and of course a super atmosphere",120,5,4,12,30,
                new Place(50.6d, 30.1d),3);
        MyEvent event2 = new MyEvent(3,"Discussion",
                "The topic of discussion will be the death penalty. Everyone is welcome",120,5,9,12,30,
                new Place(50.2d, 30.4d),3);
        MyEvent event4 = new MyEvent(4,"Fishing",
                "There will be a good atmosphere and lots of fresh fish! Everyone is welcome ",120,5,7,12,30,
                new Place(51.1d, 30.5d),4);
        MyEvent event5 = new MyEvent(5,"Project event","You will an unbelievable amount of apps which were created for the project of Theory of algorithms\n",120,5,3,12,20,new Place(57.1d, 32.5d),3);

        MyEvent event6 = new MyEvent(6,"Rock Concert",
                "A concert from Scorpions is waiting for you. Everyone is welcome!",120,5,1,19,00,new Place(55.1d, 28.5d),3);

        MyEvent event7 = new MyEvent(7,"Starladder","A cybersport tournament is waiting for you!",120,4,20,14,40,new Place(58.1d, 25.5d),4);
        MyEvent event8 = new MyEvent(8,"Lecture on higher mathematics","Valentina Fyodorovna is waiting for you. All who won`t come will be expelled!",120,4,25,8,30,new Place(59.2d,28.3d),1);
        MyEvent event9 = new MyEvent(9,"Discussion","Theme will be a justice. Everyone is welcome!",120,4,28,12,20,new Place(53.1d, 25.5d),2);
        MyEvent event10 = new MyEvent(10,"Party in dormitory","Everyone from KPI is welcome!",120,4,12,14,30,new Place(50.2d,25.1d),3);

        database.users.put(sabrina.getUserId(),sabrina);
        database.users.put(jake.getUserId(),jake);
        database.users.put(john.getUserId(),john);
        database.users.put(mike.getUserId(),mike);
        database.users.put(q.getUserId(),q);
        database.events.put(event.getEventId(),event);
        database.events.put(event1.getEventId(),event1);
        database.events.put(event2.getEventId(),event2);
        database.events.put(event3.getEventId(),event3);
        database.events.put(event4.getEventId(),event4);
        database.events.put(event5.getEventId(),event5);
        database.events.put(event6.getEventId(),event6);
        database.events.put(event7.getEventId(),event7);
        database.events.put(event8.getEventId(),event8);
        database.events.put(event9.getEventId(),event9);
        database.events.put(event10.getEventId(),event10);
    }





}
