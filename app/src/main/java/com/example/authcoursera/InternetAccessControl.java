package com.example.authcoursera;

public interface InternetAccessControl {

    //Получаем информацию о доступе к сети
    boolean getAccessInfo();

    //Отправляем запрос на разрешения
    void sendPermissionRequest();
}
