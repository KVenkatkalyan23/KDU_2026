package org.example;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class Exercise2 {

    private static void safeLookupWithDefaultValue(String id){
        Object object =  Optional.ofNullable((Object)Inventory.findItem(id))
                .orElseGet(ItemPlaceholder::new);
        if(object  instanceof  Inventory){
            System.out.println(((Inventory) object).getPalletItemIds());
        }else{
            System.out.println(((ItemPlaceholder) object).getInfo());
        }
    }

    private static void flatteningInventoryIDs(){
        Inventory inventory = Inventory.findItem("A100");
        Set<String> idSet = inventory.getPalletItemIds()
                .stream()
                .flatMap(p -> p.stream())
                .collect(Collectors.toSet());
        System.out.println(idSet);
    }

    public static void run(){
        safeLookupWithDefaultValue("A200");
        flatteningInventoryIDs();
    }
}
