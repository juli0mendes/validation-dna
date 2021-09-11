package com.juli0mendes.validationdna.adapter.exceptions;

public class ErrorDetailDto {

    private String item;
    private String description;

    public ErrorDetailDto() {
    }

    public ErrorDetailDto(String item, String description) {
        this.item = item;
        this.description = description;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "ErrorDetailDTO{" +
                "item='" + item + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
