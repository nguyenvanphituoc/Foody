package com.example.nguyenvanphituoc.foody.Interface;

import java.util.ArrayList;

/**
 * Created by PhiTuocPC on 4/26/2017.
 * nguyen van phi tước
 */

public class GenericList <T> extends ArrayList<T>
{
    private Class<T> genericType;

    public GenericList(Class<T> c)
    {
        this.genericType = c;
    }

    public Class<T> getGenericType()
    {
        return genericType;
    }
}
