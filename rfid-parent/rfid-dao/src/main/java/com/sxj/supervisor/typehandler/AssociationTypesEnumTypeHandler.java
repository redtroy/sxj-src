package com.sxj.supervisor.typehandler;

import org.apache.ibatis.type.EnumOrdinalTypeHandler;

import com.sxj.supervisor.enu.rfid.ref.AssociationTypesEnum;

public class AssociationTypesEnumTypeHandler extends
        EnumOrdinalTypeHandler<AssociationTypesEnum>
{
    
    public AssociationTypesEnumTypeHandler(Class<AssociationTypesEnum> type)
    {
        super(type);
    }
    
}
