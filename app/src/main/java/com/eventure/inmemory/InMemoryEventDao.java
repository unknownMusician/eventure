package com.eventure.inmemory;

import com.eventure.dao.EventDao;
import com.eventure.model.MyEvent;

public class InMemoryEventDao extends InMemoryAbstractDao<MyEvent> implements EventDao {

    public InMemoryEventDao(InMemoryDatabase database){
        super(database.events, MyEvent::getEventId, MyEvent::setEventId, database);
    }


}
