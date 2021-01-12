package com.example.museum.entity.Permissions;

import lombok.Data;

@Data
public class MenuItem {
    Integer itemId;
    String itemName;
    String itemIcon;
    String itemUrl;
    Integer parentId;



}
