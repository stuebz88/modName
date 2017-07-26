package com.example.examplemod.common.caps.empire;

import java.util.UUID;

import com.example.examplemod.common.core.empire.Empire;

public interface IEmpire 
{
    public void setUUID(UUID empireUUID);
    
    public UUID getUUID();
    
    public void abandonEmpire();
}
