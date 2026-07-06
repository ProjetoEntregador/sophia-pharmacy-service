package com.sophia.sophia_pharmacy_service.audit;

import com.sophia.sophia_pharmacy_service.dtos.audit.AuditEventDto;
import com.sophia.sophia_pharmacy_service.entities.enums.AuditOperation;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AuditContext {

    private final ThreadLocal<List<AuditEventDto>> events =
            ThreadLocal.withInitial(ArrayList::new);

    public void add(AuditEventDto event){
        events.get().add(event);
    }

    public List<AuditEventDto> getEvents(){
        return events.get();
    }

    public void clear(){
        events.remove();
    }

    public void insert(String entity, Object data){
        add(
                AuditEventDto.builder()
                        .entity(entity)
                        .operation(AuditOperation.INSERT)
                        .newData(data)
                        .build()
        );

    }

    public void delete(String entity, Object data){
        add(
                AuditEventDto.builder()
                        .entity(entity)
                        .operation(AuditOperation.DELETE)
                        .oldData(data)
                        .build()
        );

    }

    public void update(String entity, Object oldData, Object newData){
        add(
                AuditEventDto.builder()
                        .entity(entity)
                        .operation(AuditOperation.INSERT)
                        .oldData(oldData)
                        .newData(newData)
                        .build()
        );

    }
}

