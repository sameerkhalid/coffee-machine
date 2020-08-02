package com.example.demo.exception;

public abstract class ResourceNotFoundException extends RuntimeException{
    private String resourceType;
    private String resourceName;
    public ResourceNotFoundException(String resourceType, String resourceName) {
        super(resourceType + " '" + resourceName + "' not found.");
        this.resourceName = resourceName;
        this.resourceType = resourceType;
    }

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }
}

