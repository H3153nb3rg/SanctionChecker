package at.jps.slcm.gui.model;

import java.io.Serializable;

public class DisplayMessage implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 6691082945138272084L;

    private String field;
    private String hit;
    private String content;

    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getHit() {
        return hit;
    }

    public void setHit(String hit) {
        this.hit = hit;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
