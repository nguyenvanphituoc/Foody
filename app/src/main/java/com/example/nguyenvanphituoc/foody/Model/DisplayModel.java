package com.example.nguyenvanphituoc.foody.Model;

import com.example.nguyenvanphituoc.foody.Activity.UIUtils;

import org.ksoap2.serialization.PropertyInfo;

import java.io.Serializable;
import java.lang.reflect.Type;

/**
 * Created by PhiTuocPC on 4/4/2017.
 */

public class DisplayModel implements Serializable {
    private int id;
    private String name;
    private double rating;
    private byte[] image;
    private int person;
    private String city_name;
    private String district_name;
    private String ward_name;
    private String service_name;
    private String category_name;
    public WardModel wardModel;
    public CategoriesModel categoriesModel;
    public ServiceModel serviceModel;
    public PersonModel personModel;

    public DisplayModel() {
    }

    public String getCity_name() {
        return city_name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getRating() {
        return rating;
    }

    public byte[] getImage() {
        return image;
    }

    public String getDistrict_name() {
        return district_name;
    }

    public String getWard_name() {
        return ward_name;
    }

    public String getService_name() {
        return service_name;
    }

    public String getCategory_name() {
        return category_name;
    }


    public void setProperty(int index, Object value) {
        switch (index) {
            case 0:
                id = Integer.parseInt(value.toString());
                break;
            case 1:
                rating = Double.parseDouble(value.toString());
                break;
            case 2:
                name = value.toString();
                break;
            case 3:
                image = (byte[]) value;
                break;
            case 4:
                category_name = value.toString();
                break;
            case 5:
                service_name = value.toString();
                break;
            case 6:
                city_name = value.toString();
                break;
            case 7:
                district_name = value.toString();
                break;
            case 8:
                ward_name = value.toString();
                break;
            case 9:
                person = Integer.parseInt(value.toString());
                break;
            default:
                break;
        }
    }

    public Object getPropertyInfo(String index) {
        String tmp = index.trim().toLowerCase();
        PropertyInfo pi = new PropertyInfo();

        switch (tmp) {
            case "rate":
                pi.setName("places_rate");
                pi.setValue(rating);
                pi.setType(Double.TYPE);
                return pi;
            case "name":
                pi.setName("places_name");
                pi.setValue(name);
                pi.setType(name.getClass());
                return pi;
            case "img":
                pi.setName("places_img");
                pi.setValue("");
                pi.setType("".getClass());
                return pi;
            case "person":
                pi.setName("per_id");
                pi.setValue(person);
                pi.setType(Integer.TYPE);
                return pi;
            case "category":
                pi.setName("category_id");
                pi.setValue(categoriesModel.getId());
                pi.setType(Integer.TYPE);
                return pi;
            case "service":
                pi.setName("service_id");
                pi.setValue(serviceModel.getId());
                pi.setType(Integer.TYPE);
                return pi;
            case "city":
                pi.setName("city_id");
                pi.setValue(wardModel.getId());
                pi.setType(Integer.TYPE);
                return pi;
            case "district":
                pi.setName("district_name");
                pi.setValue(wardModel.getDistrict());
                pi.setType("".getClass());
                return pi;
            case "street":
                pi.setName("street_name");
                pi.setValue(wardModel.getStreet());
                pi.setType("".getClass());
                return pi;
            default:
                return null;
        }
    }

    public void setProperty(String index, Object value) {
        String tmp = index.trim().toLowerCase();
        switch (tmp) {
            case "id":
                id = Integer.parseInt(value.toString());
                break;
            case "rate":
                rating = Double.parseDouble(value.toString());
                break;
            case "name":
                name = value.toString();
                break;
            case "img":
                image =  (byte[]) value;
                break;
            case "category":
                category_name = value.toString();
                break;
            case "service":
                service_name = value.toString();
                break;
            case "city":
                city_name = value.toString();
                break;
            case "district":
                district_name = value.toString();
                break;
            case "street":
                ward_name = value.toString();
            case "person":
                person =  Integer.parseInt(value.toString());
                break;
            default:
                break;
        }
    }

}
