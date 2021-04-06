/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thuylinh.cart;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import thuylinh.carDetail.CarDTO;

/**
 *
 * @author Thuy Linh
 */
public class CartObject implements Serializable{
    private Map<String, CarDTO> cars;
    public Map<String, CarDTO> getItems(){
        return cars;
    }
     public void addItemsToCart(CarDTO item){
        if(this.cars==null){
            this.cars = new HashMap<>();
        }
          item.setQuantity(1);
        if(this.cars.containsKey(item.getCarID())){
           int  quantity = this.cars.get(item.getCarID()).getQuantity() +1 ;
                  item.setQuantity(quantity);
        }
        this.cars.put(item.getCarID(), item);
        
    }
    public int total (){
        int total = 0;
        if(this.cars!=null){
        for (CarDTO dto : this.cars.values()) {
            total = (int) (total + dto.getTotal()*dto.getQuantity());
        }
        }
        return total;
    }
    public void removeItems(String carID){
        if(this.cars==null){
            return;
        }
        if(this.cars.containsKey(carID)){
            this.cars.remove(carID);
            if(this.cars.isEmpty()){
                this.cars = null;
            }
        }
    }
    public void updateCart (String carID, int quantity,float total){
        if(this.cars==null){
            return;
        }
        if(this.cars.containsKey(carID)){
            this.cars.get(carID).setQuantity(quantity);
            this.cars.get(carID).setTotal(total);
        }
    }
}
