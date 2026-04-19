package org.example.session10_btth.model;
// thiết bị phòng lab


public class ResourceItem {
    private Long id;
    private String name;
    private String imageUrl;
    private int availableQuantity;
    private ResourceType type;

    public ResourceItem() {}

    public ResourceItem(Long id, String name, String imageUrl, int availableQuantity, ResourceType type) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.availableQuantity = availableQuantity;
        this.type = type;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public int getAvailableQuantity() { return availableQuantity; }
    public void setAvailableQuantity(int availableQuantity) { this.availableQuantity = availableQuantity; }

    public ResourceType getType() { return type; }
    public void setType(ResourceType type) { this.type = type; }
}