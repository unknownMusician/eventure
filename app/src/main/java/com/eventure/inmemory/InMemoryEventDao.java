package com.eventure.inmemory;

import com.eventure.dao.EventDao;
import com.eventure.model.Event;

public class InMemoryEventDao extends InMemoryAbstractDao<Event> implements EventDao {

    public InMemoryEventDao(InMemoryDatabase database){
        super(database.events, Event::getEventId, Event::setEventId, database);
    }


}
